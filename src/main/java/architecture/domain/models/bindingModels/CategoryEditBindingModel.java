package architecture.domain.models.bindingModels;

import architecture.domain.CountryCodes;

import java.util.LinkedHashMap;

public class CategoryEditBindingModel {
    private Long id;
    private LinkedHashMap<CountryCodes, String> localNames;

    public CategoryEditBindingModel() {
        this.localNames = new LinkedHashMap<>();
        for (CountryCodes countryCode : CountryCodes.values()) {
            this.localNames.put(countryCode, "");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinkedHashMap<CountryCodes, String> getLocalNames() {
        return localNames;
    }

    public void setLocalNames(LinkedHashMap<CountryCodes, String> localNames) {
        this.localNames = localNames;
    }
}
