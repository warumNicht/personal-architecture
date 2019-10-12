package architecture.web.controllers;

import architecture.domain.entities.Category;
import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.domain.models.viewModels.ArticleLocalViewModel;
import architecture.domain.CountryCodes;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.repositories.CategoryRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import architecture.domain.entities.Article;
import architecture.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class HomeController extends BaseController{

    private ArticleRepository articleRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public HomeController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView, HttpServletRequest req) {
        CountryCodes wantedCode = super.getCurrentCookieLocale();
        Object[] all = this.articleRepository.getAllNestedSelect(CountryCodes.BG, wantedCode);

        List<ArticleLocalViewModel> localisedArticles=new ArrayList<>();
        for (Object article : all) {
            Object[] articleObjects = (Object[]) article;
            ArticleLocalViewModel articleLocalViewModel = new ArticleLocalViewModel();
            articleLocalViewModel.setId((Long)articleObjects[0]);
            articleLocalViewModel.setDate((Date)articleObjects[1]);
            articleLocalViewModel.setLocalisedContent((LocalisedArticleContent)articleObjects[2]);
            localisedArticles.add(articleLocalViewModel);
        }
        modelAndView.addObject("localizedArticles",localisedArticles);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request) {
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }

    @GetMapping("/create")
    public ModelAndView createArticle(@ModelAttribute(name = "articleBinding") ArticleBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("articleBinding", model);
        modelAndView.setViewName("create-article");
        return modelAndView;
    }

    @PostMapping("/create")
    private ModelAndView createArticlePost(@Valid @ModelAttribute(name = "articleBinding") ArticleBindingModel model,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleBinding", model);
            modelAndView.setViewName("create-article");
            return modelAndView;
        }

        Article article = new Article(new Date());
        Category category = this.categoryRepository.findById(model.getCategoryId()).orElse(null);
        article.setCategory(category);
        LocalisedArticleContent articleContent = new LocalisedArticleContent(model.getTitle(), model.getContent());

        article.getLocalContent().put(model.getCountry(), articleContent);
        article = this.articleRepository.saveAndFlush(article);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
