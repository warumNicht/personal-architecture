package architecture.domain.models.bindingModels.articles;

import architecture.domain.models.bindingModels.ImageBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ArticleAddLangBindingModel extends ArticleBindingModel{
    @NotNull
    @Min(value = 1)
    private Long id;

    @NotNull
    @Valid
    private ImageBindingModel mainImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImageBindingModel getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageBindingModel mainImage) {
        this.mainImage = mainImage;
    }
}
