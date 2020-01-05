package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.domain.models.bindingModels.CategoryCreateBindingModel;
import architecture.domain.models.bindingModels.CategoryEditBindingModel;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.services.interfaces.ArticleService;
import architecture.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(ArticleService articleService, CategoryService categoryService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/listAll")
    public String listAll(Model model) {
        List<ArticleServiceModel> allArticles = this.articleService.findAll();
        model.addAttribute("allArticles", allArticles);
        return "listAll";
    }

    @GetMapping("/category/create")
    public String createCategory(Model modelView, @ModelAttribute(name = "categoryCreateModel") CategoryCreateBindingModel model) {
        modelView.addAttribute("categoryCreateModel", model);
        return "categories/category-create";
    }

    @PostMapping("/category/create")
    public String createCategoryPost(@Valid @ModelAttribute(name = "categoryCreateModel") CategoryCreateBindingModel bindingModel,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "categories/category-create";
        }
        CategoryServiceModel category = new CategoryServiceModel();
        category.getLocalCategoryNames().put(bindingModel.getCountry(), bindingModel.getName());
        this.categoryService.addCategory(category);
        return "redirect:/" + super.getLocale() + "/admin/category/list";
    }

    @GetMapping("/category/edit/{categoryId}")
    public String editCategory(Model model, @PathVariable(name = "categoryId") Long categoryId) {
        CategoryServiceModel category = this.categoryService.findById(categoryId);
        CategoryEditBindingModel bindingModel = new CategoryEditBindingModel();
        bindingModel.setId(categoryId);

        for (Map.Entry<CountryCodes, String> local : category.getLocalCategoryNames().entrySet()) {
            bindingModel.getLocalNames().put(local.getKey(), local.getValue());
        }
        model.addAttribute("categoryEditModel", bindingModel);
        return "categories/category-edit";
    }

    @PutMapping("/category/edit/{categoryId}")
    public String editCategoryPut(@ModelAttribute(name = "categoryEditModel") CategoryEditBindingModel model,
                                  @PathVariable(name = "categoryId") Long categoryId) {
        CategoryServiceModel categoryToEdit = this.modelMapper.map(model, CategoryServiceModel.class);
        Map<CountryCodes, String> filteredValues = categoryToEdit.getLocalCategoryNames().entrySet()
                .stream()
                .filter(entry -> !"".equals(entry.getValue()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        categoryToEdit.setLocalCategoryNames(filteredValues);

        this.categoryService.editCategory(categoryToEdit);
        return "redirect:/" + super.getLocale() + "/admin/category/list";
    }

    @GetMapping(value = "/category/list")
    public String listCategories() {
        return "categories/categories-list";
    }
}
