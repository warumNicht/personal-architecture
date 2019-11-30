package architecture.domain.models.viewModels;

import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;
import java.util.HashMap;
import java.util.Map;

public class ArticleViewModel extends BaseModel {
    private ImageViewModel mainImage;
    private Map<CountryCodes, LocalisedArticleContentViewModel> localContent = new HashMap<>();

    public ImageViewModel getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageViewModel mainImage) {
        this.mainImage = mainImage;
    }

    public Map<CountryCodes, LocalisedArticleContentViewModel> getLocalContent() {
        return localContent;
    }

    public void setLocalContent(Map<CountryCodes, LocalisedArticleContentViewModel> localContent) {
        this.localContent = localContent;
    }
}
