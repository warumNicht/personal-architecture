package architecture.services;

import architecture.domain.CountryCodes;
import architecture.domain.models.serviceModels.LocalisedCategoryServiceModel;
import architecture.repositories.CategoryRepository;
import architecture.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<LocalisedCategoryServiceModel> getAllCategoriesByLocale(CountryCodes defaultCode, CountryCodes currentCode) {
        Object[] allCategoriesByLocale = this.categoryRepository.getAllCategoriesByLocale(defaultCode, currentCode);
        List<LocalisedCategoryServiceModel> categories = new ArrayList<>();
        for (Object currentObjectArray : allCategoriesByLocale) {
            Object[] objectArray = (Object[]) currentObjectArray;
            Long categoryId = (Long) objectArray[0];
            String categoryName = (String) objectArray[1];
            LocalisedCategoryServiceModel categoryServiceModel = new LocalisedCategoryServiceModel(categoryId, categoryName);
            categories.add(categoryServiceModel);
        }
        return categories;
    }
}
