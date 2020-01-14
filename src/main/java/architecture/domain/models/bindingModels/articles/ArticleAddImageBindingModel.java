package architecture.domain.models.bindingModels.articles;

import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;
import architecture.domain.models.bindingModels.ImageBindingModel;

public class ArticleAddImageBindingModel extends BaseModel {
    private CountryCodes lang;
    private ImageBindingModel image;

    public ArticleAddImageBindingModel() {
    }

    public CountryCodes getLang() {
        return lang;
    }

    public void setLang(CountryCodes lang) {
        this.lang = lang;
    }

    public ImageBindingModel getImage() {
        return image;
    }

    public void setImage(ImageBindingModel image) {
        this.image = image;
    }
}
