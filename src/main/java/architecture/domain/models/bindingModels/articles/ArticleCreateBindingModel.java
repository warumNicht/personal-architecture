package architecture.domain.models.bindingModels.articles;

import architecture.domain.models.bindingModels.ImageBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ArticleCreateBindingModel extends ArticleBindingModel{
    @NotNull
    @Valid
    private ImageBindingModel mainImage;

    public ArticleCreateBindingModel() {
        this.mainImage = new ImageBindingModel();
    }

    public ImageBindingModel getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageBindingModel mainImage) {
        this.mainImage = mainImage;
    }
}
