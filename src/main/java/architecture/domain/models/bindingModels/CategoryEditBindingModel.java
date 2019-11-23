package architecture.domain.models.bindingModels;

import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;

import java.util.LinkedHashMap;

public class CategoryEditBindingModel extends BaseModel {
    private LinkedHashMap<CountryCodes, String> localNames;

    public CategoryEditBindingModel() {
        this.localNames = new LinkedHashMap<>();
        for (CountryCodes countryCode : CountryCodes.values()) {
            this.localNames.put(countryCode, "");
        }
    }

    public LinkedHashMap<CountryCodes, String> getLocalNames() {
        return localNames;
    }

    public void setLocalNames(LinkedHashMap<CountryCodes, String> localNames) {
        this.localNames = localNames;
    }
}
