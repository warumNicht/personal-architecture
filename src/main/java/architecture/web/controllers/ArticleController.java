package architecture.web.controllers;

import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.domain.models.serviceModels.LocalisedArticleContentServiceModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
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
}