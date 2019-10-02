package architecture.services.interfaces;

import architecture.domain.CountryCodes;
import architecture.domain.models.serviceModels.LocalisedCategoryServiceModel;

import java.util.List;

public interface CategoryService {
    List<LocalisedCategoryServiceModel> getAllCategoriesByLocale(CountryCodes defaultCode, CountryCodes currentCode);
}
