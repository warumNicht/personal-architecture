package architecture.web.controllers;

import architecture.domain.CountryCodes;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/admin/articles")
public class ArticleController extends BaseController {
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
        article.getLocalContent().put(model.getCountry(), content);
        this.articleService.createArticle(article);

        modelAndView.setViewName("redirect:/" + super.getLocale() + "/");
        return modelAndView;
    }

    @GetMapping("/addLang/{id}")
    public ModelAndView addLanguageToArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long articleId,
                                             @ModelAttribute(name = "articleBinding") ArticleBindingModel model) {
        modelAndView.setViewName("article-add-lang");
        return modelAndView;
    }

    @PostMapping("/addLang")
    public ModelAndView addLanguageToArticlePost(ModelAndView modelAndView,
                                                 @ModelAttribute(name = "articleBinding") ArticleBindingModel model, @RequestParam(name = "articleId") Long articleId) {
        ArticleServiceModel article = this.articleService.findById(articleId);
        LocalisedArticleContentServiceModel localisedArticleContent = new LocalisedArticleContentServiceModel(model.getTitle(), model.getContent());
        article.getLocalContent().put(model.getCountry(), localisedArticleContent);
        this.articleService.updateArticle(article);

        modelAndView.setViewName("redirect:/" + super.getLocale() + "/admin/listAll");
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}/{lang}")
    public ModelAndView editArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long id, @PathVariable(name = "lang") String lang,
                                    @ModelAttribute(name = "articleEditLang") ArticleEditLangBindingModel model) {
        ArticleServiceModel articleServiceModel = this.articleService.findById(id);
        LocalisedArticleContentServiceModel localisedArticleContentServiceModel = articleServiceModel.getLocalContent().get(CountryCodes.valueOf(lang));
        ArticleEditLangBindingModel bindingModel = this.modelMapper.map(localisedArticleContentServiceModel, ArticleEditLangBindingModel.class);
        bindingModel.setId(id);
        System.out.println(id);
        System.out.println(lang);
        model = bindingModel;
        modelAndView.addObject("articleEditLang", model);
        modelAndView.setViewName("article-edit-lang");
        return modelAndView;
    }

//    @PutMapping(value = "/edit")
    @RequestMapping(method = {RequestMethod.PATCH},value = "/edit")
    public ModelAndView editArticlePatch(ModelAndView modelAndView, @ModelAttribute(name = "articleEditLang") ArticleEditLangBindingModel model,
                                       @RequestParam(name = "articleLang") String lang) {
        ArticleServiceModel articleServiceModel = this.articleService.findById(model.getId());
        LocalisedArticleContentServiceModel content = this.modelMapper.map(model, LocalisedArticleContentServiceModel.class);
        articleServiceModel.getLocalContent().put(CountryCodes.valueOf(lang), content);
        this.articleService.updateArticle(articleServiceModel);
        modelAndView.setViewName("redirect:/" + super.getLocale() + "/admin/listAll");
        return modelAndView;
    }

    @PutMapping(value = "/edit")
    @ResponseStatus(code = HttpStatus.MOVED_PERMANENTLY)
    public ModelAndView editArticlePut(ModelAndView modelAndView, @RequestBody ArticleEditLangBindingModel model) {
        ArticleServiceModel articleServiceModel = this.articleService.findById(model.getId());
        LocalisedArticleContentServiceModel content = this.modelMapper.map(model, LocalisedArticleContentServiceModel.class);
        articleServiceModel.getLocalContent().put(CountryCodes.valueOf("EN"), content);
        this.articleService.updateArticle(articleServiceModel);
        modelAndView.setViewName("redirect:/" + super.getLocale() + "/admin/listAll");
        return modelAndView;
    }
}
