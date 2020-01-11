package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.EnumValidator;
import architecture.domain.CountryCodes;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryCreateBindingModel {
    @NotNull(message = "Cannot be null")
    @EnumValidator(enumClass = CountryCodes.class, message = "wert")
    private CountryCodes country;

    @Size(min=8, max=36, message = "{category.name.length}")
    @BeginUppercase(message = "begin-uppercase")
    @Pattern(regexp = "^(?=.*\\S).+$|^$",flags = Pattern.Flag.DOTALL,
            message = "{whitespaces}")
    private String name;

    public CountryCodes getCountry() {
        return country;
    }

    public void setCountry(String country) {
        try {
            this.country = CountryCodes.valueOf(country);
        }catch (Exception e){
            this.country=null;
        }
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
