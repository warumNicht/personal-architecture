package architecture.domain.models.bindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public abstract class ImageBaseBindingModel {
    @NotNull
    @NotEmpty(message = "{text.empty}")
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?(?!w{0,2}\\.)[^\"'\\s]{3,}\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG|svg|SVG|webp|WEBP)$|^$", message = "{url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
