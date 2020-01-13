package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ImageBindingModel {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?(?!w{0,2}\\.)[^\"'\\s]{3,}\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG|svg|SVG|webp|WEBP)$|^$", message = "Not a image url")
    private String url;
    @NotNull
    @NotEmpty
    @BeginUppercase(allowEmpty = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
