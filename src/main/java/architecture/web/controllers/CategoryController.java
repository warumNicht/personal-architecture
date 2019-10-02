package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = "/all", produces = "application/json")
    public Object getCategories(HttpServletRequest req) {
        CountryCodes wanted = super.getCurrentCookieLocale();
        return this.categoryRepository.getAllCategoriesByLocale(CountryCodes.BG, wanted);
    }
}
