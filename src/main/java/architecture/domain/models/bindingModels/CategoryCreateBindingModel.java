package architecture.domain.models.bindingModels;

import architecture.domain.CountryCodes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryCreateBindingModel {
    @NotNull
    private CountryCodes country;
    @Size(min=8, max=30, message = "category.name.length")
    private String name;

    public CountryCodes getCountry() {
        return country;
    }

    public void setCountry(CountryCodes country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
