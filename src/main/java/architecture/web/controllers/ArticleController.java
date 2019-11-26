package architecture.web.controllers;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.models.bindingModels.ArticleAddImageBindingModel;
import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.domain.models.bindingModels.ArticleEditLangBindingModel;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.domain.models.serviceModels.ImageServiceModel;
import architecture.domain.models.serviceModels.LocalisedArticleContentServiceModel;
import architecture.domain.models.viewModels.ArticleAddImageViewModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleController(ArticleService articleService, CategoryService categoryService, ImageService imageService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.imageService = imageService;
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
        if(!"".equals(model.getMainImage().getUrl())){
            ImageServiceModel mainImage = this.modelMapper.map(model.getMainImage(), ImageServiceModel.class);
            mainImage.getLocalImageNames().put(model.getCountry(),model.getMainImage().getName());
            mainImage.setArticle(article);
            article.setMainImage(mainImage);
        }
        this.articleService.createArticle(article);

        modelAndView.setViewName("redirect:/" + super.getLocale() + "/");
        return modelAndView;
    }

    @GetMapping("/addLang/{id}")
    public ModelAndView addLanguageToArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long articleId,
                                             @ModelAttribute(name = "articleBinding") ArticleBindingModel model) {
        ArticleServiceModel article = this.articleService.findById(articleId);
        if(article.getMainImage()==null){
            model.setMainImage(null);
        }
        modelAndView.addObject("articleBinding", model);
        modelAndView.setViewName("article-add-lang");
        return modelAndView;
    }

    @PostMapping("/addLang")
    public ModelAndView addLanguageToArticlePost(ModelAndView modelAndView,
                                                 @ModelAttribute(name = "articleBinding") ArticleBindingModel model, @RequestParam(name = "articleId") Long articleId) {
        ArticleServiceModel article = this.articleService.findById(articleId);
        LocalisedArticleContentServiceModel localisedArticleContent = new LocalisedArticleContentServiceModel(model.getTitle(), model.getContent());
        article.getLocalContent().put(model.getCountry(), localisedArticleContent);
        if(article.getMainImage()!=null){
            article.getMainImage().getLocalImageNames().put(model.getCountry(), model.getMainImage().getName());
        }
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
        if(articleServiceModel.getMainImage()!=null){
            bindingModel.setMainImageName(articleServiceModel.getMainImage().getLocalImageNames().get(CountryCodes.valueOf(lang)));
        }
        model = bindingModel;
        modelAndView.addObject("articleEditLang", model);
        modelAndView.setViewName("article-edit-lang");
        return modelAndView;
    }

    @RequestMapping(method = {RequestMethod.PATCH}, value = "/edit", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String editArticlePut(@RequestBody ArticleEditLangBindingModel model) {

        ArticleServiceModel articleServiceModel = this.articleService.findById(model.getId());
        LocalisedArticleContentServiceModel content = this.modelMapper.map(model, LocalisedArticleContentServiceModel.class);
        articleServiceModel.getLocalContent().put(CountryCodes.valueOf(model.getLang()), content);
        articleServiceModel.getMainImage().getLocalImageNames().put(CountryCodes.valueOf(model.getLang()),model.getMainImageName());
        this.articleService.updateArticle(articleServiceModel);
        return "\"/" + super.getLocale() + "/admin/listAll\"";
    }

    @GetMapping(value = "/add-image/{id}")
    public ModelAndView articleAddImage(ModelAndView modelAndView, @PathVariable(name = "id") Long id){
        ArticleServiceModel article = this.articleService.findById(id);
        LocalisedArticleContentServiceModel content = article.getLocalContent().get(super.getCurrentCookieLocale());
        if(content==null){
            content=article.getLocalContent().get(ApplicationConstants.DEFAULT_COUNTRY_CODE);
        }
        ArticleAddImageViewModel model;
        if(article.getMainImage()!=null){
            model= new ArticleAddImageViewModel(id, content.getTitle(), article.getMainImage().getUrl());
        }else {
            model= new ArticleAddImageViewModel(id, content.getTitle());
        }
        modelAndView.addObject("article",model);
        modelAndView.setViewName("article-add-image");
        return modelAndView;
    }

    @RequestMapping(method = {RequestMethod.PUT}, value = "/add-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String articleAddImagepost(@RequestBody ArticleAddImageBindingModel image){
        ArticleServiceModel article = this.articleService.findById(image.getId());
        ImageServiceModel imageServiceModel = new ImageServiceModel(image.getImage().getUrl());
        imageServiceModel.getLocalImageNames().put(image.getLang(), image.getImage().getName());
        imageServiceModel.setArticle(article);
        this.imageService.saveImage(imageServiceModel);
        System.out.println(image.getLang());
        return "\"/" + super.getLocale() + "/admin/listAll\"";
    }
}
