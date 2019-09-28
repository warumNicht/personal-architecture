package architecture.web.controllers;

import architecture.domain.ArticleBindingModel;
import architecture.domain.ArticleLocalViewModel;
import architecture.domain.CountryCodes;
import architecture.domain.entities.LocalisedArticleContent;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import architecture.domain.entities.Article;
import architecture.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class HomeController {

    private ArticleRepo articleRepo;

    @Autowired
    public HomeController(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView, HttpServletRequest req) {
        Cookie actualCookie = WebUtils.getCookie(req, "lang");
        CountryCodes wanded;
        if(actualCookie!=null){
            wanded=CountryCodes.valueOf(actualCookie.getValue().toUpperCase());
        }else {
            wanded=CountryCodes.BG;
        }
        Object[] all = this.articleRepo.getAllNestedSelect(CountryCodes.BG, wanded);

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
        Cookie[] cookies = request.getCookies();
        Article article = this.articleRepo.findById(1L).orElse(null);
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }

    @GetMapping("/create")
    public ModelAndView createArticle(@ModelAttribute(name = "articleBinding") ArticleBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("articleBinding", model);
        modelAndView.setViewName("create");
        return modelAndView;
    }

    @PostMapping("/create")
    private ModelAndView createArticlePost(@Valid @ModelAttribute(name = "articleBinding") ArticleBindingModel model,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleBinding", model);
            modelAndView.setViewName("create");
            return modelAndView;
        }

        Article article = new Article(new Date());
        LocalisedArticleContent articleContent = new LocalisedArticleContent(model.getTitle(), model.getContent());

        article.getLocalContent().put(model.getCountry(), articleContent);
        article = this.articleRepo.saveAndFlush(article);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
