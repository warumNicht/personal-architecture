package architecture.domain.models.serviceModels;

import architecture.domain.CountryCodes;

import java.util.HashMap;
import java.util.Map;

public class CategoryServiceModel extends BaseServiceModel {
    private Map<CountryCodes, String> localCategoryNames  = new HashMap<>();

    public Map<CountryCodes, String> getLocalCategoryNames() {
        return localCategoryNames;
    }

    public void setLocalCategoryNames(Map<CountryCodes, String> localCategoryNames) {
        this.localCategoryNames = localCategoryNames;
    }
}
