package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.ContainsNotEmpty;
import architecture.annotations.EnumValidator;
import architecture.annotations.LengthOrEmpty;
import architecture.constants.AppConstants;
import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.LinkedHashMap;

public class ImageEditBindingModel extends BaseModel {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?(?!w{0,2}\\.)[^\"'\\s]{3,}\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG|svg|SVG|webp|WEBP)$|^$", message = "Not a image url")
    private String url;

    @NotNull
    @Size(min = AppConstants.COUNTRY_SIZE +1, max = AppConstants.COUNTRY_SIZE+2, message = "Must contain '${validatedValue}'  {min} names")
    @ContainsNotEmpty
    private LinkedHashMap<@EnumValidator(enumClass = CountryCodes.class, message = "wert") CountryCodes,
            @LengthOrEmpty(min = 3, max = 256) @BeginUppercase(allowEmpty = true) String> localImageNames;

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
