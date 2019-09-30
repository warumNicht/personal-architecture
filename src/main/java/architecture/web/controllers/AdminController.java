package architecture.web.controllers;

import architecture.domain.entities.Category;
import architecture.domain.models.ArticleBindingModel;
import architecture.domain.entities.Article;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.CategoryCreateBindingModel;
import architecture.repositories.ArticleRepo;
import architecture.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin" )
public class AdminController {
    private ArticleRepo articleRepo;
    private CategoryRepository categoryRepository;

    @Autowired
    public AdminController(ArticleRepo articleRepo, CategoryRepository categoryRepository) {
        this.articleRepo = articleRepo;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/listAll")
    public ModelAndView listAll(ModelAndView modelAndView){
        List<Article> allArticles = this.articleRepo.findAll();
        modelAndView.addObject("allArticles",allArticles);
        modelAndView.setViewName("listAll");

        return modelAndView;
    }

    @GetMapping("/articles/addLang/{id}")
    public ModelAndView addLanguageToArticle(ModelAndView modelAndView, @PathVariable(name = "id") Long articleId,
                                             @ModelAttribute(name = "articleBinding") ArticleBindingModel model){
        modelAndView.setViewName("article-add-lang");
        return modelAndView;
    }

    @PostMapping("/articles/addLang")
    public ModelAndView addLanguageToArticlePost(ModelAndView modelAndView,
                                                 @ModelAttribute(name = "articleBinding") ArticleBindingModel model, @RequestParam(name = "articleId") String articleId ){
        Long id = Long.parseLong(articleId);
        Article articleToUpdate = this.articleRepo.findById(id).orElse(null);
        LocalisedArticleContent localisedArticleContent = new LocalisedArticleContent(model.getTitle(), model.getContent());
        articleToUpdate.getLocalContent().put(model.getCountry(),localisedArticleContent);
        this.articleRepo.save(articleToUpdate);

        modelAndView.setViewName("redirect:/admin/listAll");
        return modelAndView;
    }

    @GetMapping("/category/create")
    public ModelAndView createCategory(ModelAndView modelAndView, @ModelAttribute(name = "categoryCreateModel") CategoryCreateBindingModel model){
        modelAndView.addObject("categoryCreateModel", model);
        modelAndView.setViewName("createCategory");
        return modelAndView;
    }

    @PostMapping("/category/create")
    public ModelAndView createCategoryPost (ModelAndView modelAndView, @ModelAttribute(name = "categoryCreateModel") CategoryCreateBindingModel model){
        Category category=new Category();
        category.getLocalCategoryNames().put(model.getCountry(),model.getName());
        this.categoryRepository.saveAndFlush(category);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
