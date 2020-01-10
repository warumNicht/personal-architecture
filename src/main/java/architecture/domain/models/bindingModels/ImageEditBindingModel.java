package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.EnumValidator;
import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;

import javax.validation.constraints.*;
import java.util.LinkedHashMap;

public class ImageEditBindingModel extends BaseModel {
    @NotNull
    @NotEmpty
    @Size(min = 10)
    private String url;

    @NotNull
    private LinkedHashMap<@EnumValidator(enumClass = CountryCodes.class, message = "wert") CountryCodes,
            @NotEmpty @BeginUppercase String> localImageNames;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkedHashMap<CountryCodes, String> getLocalImageNames() {
        return localImageNames;
    }

    public void setLocalImageNames(LinkedHashMap<CountryCodes, String> localImageNames) {
        this.localImageNames = localImageNames;
    }
}
