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
import architecture.domain.models.viewModels.articles.ArticleAddImageViewModel;
import architecture.domain.models.viewModels.articles.ArticleEditViewModel;
import architecture.domain.models.viewModels.articles.ArticleViewModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
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
    public String createArticle(@ModelAttribute(name = "articleBinding") ArticleBindingModel articleBindingModel, Model model) {
        model.addAttribute("articleBinding", articleBindingModel);
        return "create-article";
    }

    @PostMapping("/create")
    private String createArticlePost(@Valid @ModelAttribute(name = "articleBinding") ArticleBindingModel bindingModel,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleBinding", bindingModel);
            return "create-article";
        }
        ArticleServiceModel article = new ArticleServiceModel(new Date());
        CategoryServiceModel category = this.categoryService.findById(bindingModel.getCategoryId());
        article.setCategory(category);
        LocalisedArticleContentServiceModel content = new LocalisedArticleContentServiceModel(bindingModel.getTitle(), bindingModel.getContent());
        article.getLocalContent().put(bindingModel.getCountry(), content);
        if (!"".equals(bindingModel.getMainImage().getUrl())) {
            ImageServiceModel mainImage = this.modelMapper.map(bindingModel.getMainImage(), ImageServiceModel.class);
            mainImage.getLocalImageNames().put(bindingModel.getCountry(), bindingModel.getMainImage().getName());
            mainImage.setArticle(article);
            article.setMainImage(mainImage);
        }
        this.articleService.createArticle(article);
        return "redirect:/" + super.getLocale() + "/";
    }

    @GetMapping("/addLang/{id}")
    public String addLanguageToArticle(Model modelView, @PathVariable(name = "id") Long articleId,
                                       @ModelAttribute(name = "articleBinding") ArticleBindingModel model) {
        ArticleServiceModel article = this.articleService.findById(articleId);
        if (article.getMainImage() == null) {
            model.setMainImage(null);
        }
        modelView.addAttribute("articleBinding", model);
        return "article-add-lang";
    }

    @PostMapping("/addLang")
    public String addLanguageToArticlePost(Model modelView,
                                           @ModelAttribute(name = "articleBinding") ArticleBindingModel model, @RequestParam(name = "articleId") Long articleId) {
        ArticleServiceModel article = this.articleService.findById(articleId);
        LocalisedArticleContentServiceModel localisedArticleContent = new LocalisedArticleContentServiceModel(model.getTitle(), model.getContent());
        article.getLocalContent().put(model.getCountry(), localisedArticleContent);
        if (article.getMainImage() != null) {
            article.getMainImage().getLocalImageNames().put(model.getCountry(), model.getMainImage().getName());
        }
        this.articleService.updateArticle(article);
        return "redirect:/" + super.getLocale() + "/admin/listAll";
    }

    @GetMapping(value = "/edit/{id}/{lang}")
    public String editArticleLang(Model modelView, @PathVariable(name = "id") Long id, @PathVariable(name = "lang") String lang,
                                  @ModelAttribute(name = "articleEditLang") ArticleEditLangBindingModel model) {
        ArticleServiceModel articleServiceModel = this.articleService.findById(id);
        LocalisedArticleContentServiceModel localisedArticleContentServiceModel = articleServiceModel.getLocalContent().get(CountryCodes.valueOf(lang));
        ArticleEditLangBindingModel bindingModel = this.modelMapper.map(localisedArticleContentServiceModel, ArticleEditLangBindingModel.class);
        bindingModel.setId(id);
        if (articleServiceModel.getMainImage() != null) {
            bindingModel.setMainImageName(articleServiceModel.getMainImage().getLocalImageNames().get(CountryCodes.valueOf(lang)));
        }
        model = bindingModel;
        modelView.addAttribute("articleEditLang", model);
        return "article-edit-lang";
    }

    @GetMapping(value = "/edit/{id}")
    public String editArticle(Model model, @PathVariable(name = "id") Long id) {
        ArticleServiceModel article = this.articleService.findById(id);
        ArticleEditViewModel viewModel = this.modelMapper.map(article, ArticleEditViewModel.class);
        model.addAttribute("articleEdit", viewModel);
        return "article-edit";
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.PATCH}, value = "/edit", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String editArticleLangPut(@RequestBody ArticleEditLangBindingModel model) {

        ArticleServiceModel articleServiceModel = this.articleService.findById(model.getId());
        LocalisedArticleContentServiceModel content = this.modelMapper.map(model, LocalisedArticleContentServiceModel.class);
        articleServiceModel.getLocalContent().put(CountryCodes.valueOf(model.getLang()), content);
        if (articleServiceModel.getMainImage() != null) {
            articleServiceModel.getMainImage().getLocalImageNames().put(CountryCodes.valueOf(model.getLang()), model.getMainImageName());
        }
        this.articleService.updateArticle(articleServiceModel);
        return "\"/" + super.getLocale() + "/admin/listAll\"";
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.PATCH}, value = "/change-category/{articleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String changeCategory ( @RequestBody Long categoryId, @PathVariable(name = "articleId") Long articleId){
        ArticleServiceModel article = this.articleService.findById(articleId);
        CategoryServiceModel newCategory = this.categoryService.findById(categoryId);
        article.setCategory(newCategory);
        this.articleService.updateArticle(article);
        return "\"/" + super.getLocale() + "/admin/articles/edit/" + articleId + "\"";
    }

    @GetMapping(value = "/add-image/{id}")
    public String articleAddImage(@PathVariable(name = "id") Long id, Model model) {
        ArticleServiceModel article = this.articleService.findById(id);
        LocalisedArticleContentServiceModel content = article.getLocalContent().get(super.getCurrentCookieLocale());
        if (content == null) {
            content = article.getLocalContent().get(ApplicationConstants.DEFAULT_COUNTRY_CODE);
        }
        if (content == null) {
            content = article.getLocalContent().entrySet().iterator().next().getValue();
        }
        ArticleAddImageViewModel addImageViewModel;
        if (article.getMainImage() != null) {
            addImageViewModel = new ArticleAddImageViewModel(id, content.getTitle(), article.getMainImage().getUrl());
        } else {
            addImageViewModel = new ArticleAddImageViewModel(id, content.getTitle());
        }
        model.addAttribute("article", addImageViewModel);
        return "article-add-image";
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.PUT}, value = "/add-image/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String articleAddImagePut(@RequestBody ArticleAddImageBindingModel image,
                                     @PathVariable(name = "id") Long id,
                                     @RequestParam(value = "main", required = false) boolean isMain) {
        ArticleServiceModel article = this.articleService.findById(image.getId());
        ImageServiceModel imageServiceModel = new ImageServiceModel(image.getImage().getUrl());
        imageServiceModel.getLocalImageNames().put(image.getLang(), image.getImage().getName());
        imageServiceModel.setArticle(article);
        if (isMain) {
            article.setMainImage(imageServiceModel);
            this.articleService.updateArticle(article);
        } else {
            this.imageService.saveImage(imageServiceModel);
        }
        return "\"/" + super.getLocale() + "/admin/listAll\"";
    }

}
