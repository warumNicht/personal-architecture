package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/all", produces = "application/json")
    public Object getCategories(HttpServletRequest req) {
        CountryCodes wanted = super.getCurrentCookieLocale();
        return this.categoryService.getAllCategoriesByLocale(CountryCodes.BG, wanted);
    }
}
