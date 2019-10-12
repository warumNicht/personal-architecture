package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import architecture.domain.models.bindingModels.ArticleBindingModel;
import architecture.domain.entities.Article;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.bindingModels.CategoryCreateBindingModel;
import architecture.domain.models.bindingModels.CategoryEditBindingModel;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.repositories.ArticleRepo;
import architecture.repositories.CategoryRepository;
import architecture.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin" )
public class AdminController {
    private ArticleRepo articleRepo;
    private CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(ArticleRepo articleRepo, CategoryRepository categoryRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.articleRepo = articleRepo;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
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
        CategoryServiceModel category = new CategoryServiceModel();
        category.getLocalCategoryNames().put(model.getCountry(),model.getName());
        this.categoryService.addCategory(category);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/category/edit/{categoryId}")
    public ModelAndView editCategory(ModelAndView modelAndView, @PathVariable(name = "categoryId") Long categoryId){
        CategoryServiceModel category = this.categoryService.findById(categoryId);
        CategoryEditBindingModel model = new CategoryEditBindingModel();
        model.setId(categoryId);

        for (Map.Entry<CountryCodes, String> local: category.getLocalCategoryNames().entrySet()) {
            model.getLocalNames().put(local.getKey(), local.getValue());
        }
        modelAndView.addObject("categoryEditModel", model);
        modelAndView.setViewName("edit-category");
        return modelAndView;
    }

    @PutMapping("/category/edit/{categoryId}")
    public ModelAndView editCategoryPut(ModelAndView modelAndView,@ModelAttribute(name = "categoryEditModel") CategoryEditBindingModel model,
                                        @PathVariable(name = "categoryId") Long categoryId){
        CategoryServiceModel categoryToEdit = this.modelMapper.map(model, CategoryServiceModel.class);
        Map<CountryCodes, String> filteredValues = categoryToEdit.getLocalCategoryNames().entrySet()
                .stream()
                .filter(entry -> !"".equals(entry.getValue()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        categoryToEdit.setLocalCategoryNames(filteredValues);

        this.categoryService.editCategory(categoryToEdit);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
