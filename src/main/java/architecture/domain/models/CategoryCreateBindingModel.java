package architecture.domain.models;

import architecture.domain.CountryCodes;

public class CategoryCreateBindingModel {
    private CountryCodes country;
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
