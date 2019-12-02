package architecture.web.controllers;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.CategoryService;
import architecture.services.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fetch")
public class FetchController extends BaseController {
    private final CategoryService categoryService;
    private final ImageService  imageService;

    @Autowired
    public FetchController(CategoryService categoryService, ImageService imageService) {
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/categories/all", produces = "application/json")
    public Object getCategories() {
        CountryCodes wanted = super.getCurrentCookieLocale();
        return this.categoryService.getAllCategoriesByLocale(ApplicationConstants.DEFAULT_COUNTRY_CODE, wanted);
    }

    @RequestMapping(value = "/images/{articleId}", produces = "application/json")
    public Object getArticleImages(@PathVariable(name = "articleId") Long articleId){
        Object res=this.imageService.getImagesByArticle(articleId);
        return res;
    }
}
