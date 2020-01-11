package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.EnumValidator;
import architecture.constants.AppConstants;
import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.LinkedHashMap;

public class ImageEditBindingModel extends BaseModel {
    @NotNull
    @Size
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?(?!w{0,2}\\.)[^\"'\\s]{3,}\\.(png|jpg|jpeg|gif|png|svg|webp)$|^$", message = "Not a image url")
    private String url;

    @NotNull
    private LinkedHashMap<@EnumValidator(enumClass = CountryCodes.class, message = "wert") CountryCodes,
            @NotEmpty @Length(min = 5) @BeginUppercase(allowEmpty = true) String> localImageNames;

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
