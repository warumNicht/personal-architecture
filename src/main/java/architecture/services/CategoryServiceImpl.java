package architecture.services;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import architecture.domain.models.serviceModels.CategoryServiceModel;
import architecture.domain.models.viewModels.LocalisedCategoryViewModel;
import architecture.repositories.CategoryRepository;
import architecture.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<LocalisedCategoryViewModel> getAllCategoriesByLocale(CountryCodes defaultCode, CountryCodes currentCode) {
        Object[] allCategoriesByLocale = this.categoryRepository.getAllCategoriesByLocale(defaultCode, currentCode);
        List<LocalisedCategoryViewModel> categories = new ArrayList<>();
        for (Object currentObjectArray : allCategoriesByLocale) {
            Object[] objectArray = (Object[]) currentObjectArray;
            Long categoryId = (Long) objectArray[0];
            String categoryName = (String) objectArray[1];
            LocalisedCategoryViewModel categoryServiceModel = new LocalisedCategoryViewModel(categoryId, categoryName);
            categories.add(categoryServiceModel);
        }
        return categories;
    }

    @Override
    public void addCategory(CategoryServiceModel categoryServiceModel){
        Category category = this.mapper.map(categoryServiceModel, Category.class);
        this.categoryRepository.saveAndFlush(category);
    }
}
