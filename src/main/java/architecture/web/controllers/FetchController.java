package architecture.web.controllers;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.models.viewModels.ImageViewModel;
import architecture.domain.models.viewModels.LocalisedCategoryViewModel;
import architecture.services.interfaces.CategoryService;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fetch")
public class FetchController extends BaseController {
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public FetchController(CategoryService categoryService, ImageService imageService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/categories/all", produces = "application/json")
    public Object getCategories() {
        CountryCodes wanted = super.getCurrentCookieLocale();
        List<LocalisedCategoryViewModel> allCategoriesByLocale = this.categoryService.getAllCategoriesByLocale(ApplicationConstants.DEFAULT_COUNTRY_CODE, wanted);
        return allCategoriesByLocale;
    }

    @RequestMapping(value = "/images/{articleId}", produces = "application/json")
    public Object getArticleImages(@PathVariable(name = "articleId") Long articleId) {
        return this.imageService.getImagesByArticle(articleId)
                .stream()
                .map(img -> this.modelMapper.map(img, ImageViewModel.class))
                .collect(Collectors.toList());
    }
}
