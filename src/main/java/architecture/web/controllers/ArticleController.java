package architecture.web.controllers;

import architecture.domain.entities.Article;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.domain.models.bindingModels.ArticleEditLangBindingModel;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.domain.models.serviceModels.LocalisedArticleContentServiceModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleController(ArticleService articleService, CategoryService categoryService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
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
        ArticleServiceModel article = new ArticleServiceModel(new Date());
        CategoryServiceModel category = this.categoryService.findById(model.getCategoryId());
        article.setCategory(category);
        LocalisedArticleContentServiceModel content = new LocalisedArticleContentServiceModel(model.getTitle(), model.getContent());
        article.getLocalContent().put(model.getCountry(),content);
        this.articleService.createArticle(article);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/addLang/{id}")
    public ModelAndView addLanguageToArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long articleId,
                                             @ModelAttribute(name = "articleBinding") ArticleBindingModel model){
        modelAndView.setViewName("article-add-lang");
        return modelAndView;
    }

    @PostMapping("/addLang")
    public ModelAndView addLanguageToArticlePost(ModelAndView modelAndView,
                                                 @ModelAttribute(name = "articleBinding") ArticleBindingModel model, @RequestParam(name = "articleId") Long articleId ){
        ArticleServiceModel article = this.articleService.findById(articleId);
        LocalisedArticleContentServiceModel localisedArticleContent = new LocalisedArticleContentServiceModel(model.getTitle(), model.getContent());
        article.getLocalContent().put(model.getCountry(),localisedArticleContent);
        this.articleService.updateArticle(article);

        modelAndView.setViewName("redirect:/admin/listAll");
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}/{lang}")
    public ModelAndView editArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long id, @PathVariable(name = "lang") String lang,
                                    @ModelAttribute(name = "articleEditLang")ArticleEditLangBindingModel model){
        System.out.println(id);
        System.out.println(lang);
        modelAndView.setViewName("article-edit-lang");
        return modelAndView;
    }
}
