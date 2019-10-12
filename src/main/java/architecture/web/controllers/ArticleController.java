package architecture.web.controllers;

import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    @Autowired
    public ArticleController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
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

//        Article article = new Article(new Date());
//        Category category = this.categoryRepository.findById(model.getCategoryId()).orElse(null);
//        article.setCategory(category);
//        LocalisedArticleContent articleContent = new LocalisedArticleContent(model.getTitle(), model.getContent());

//        article.getLocalContent().put(model.getCountry(), articleContent);
//        article = this.articleRepository.saveAndFlush(article);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
