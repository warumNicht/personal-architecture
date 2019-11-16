package architecture.web.controllers;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fetch")
public class FetchController extends BaseController {
    private CategoryService categoryService;

    @Autowired
    public FetchController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories/all", produces = "application/json")
    public Object getCategories(HttpServletRequest req) {
        CountryCodes wanted = super.getCurrentCookieLocale();
        return this.categoryService.getAllCategoriesByLocale(ApplicationConstants.DEFAULT_COUNTRY_CODE, wanted);
    }
}
