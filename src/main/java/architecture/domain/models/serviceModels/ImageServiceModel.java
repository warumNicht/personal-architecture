package architecture.domain.models.serviceModels;

import architecture.domain.CountryCodes;
import java.util.HashMap;
import java.util.Map;

public class ImageServiceModel extends BaseServiceModel{
    private String url;
    private Map<CountryCodes, String> localImageNames = new HashMap<>();
    private ArticleServiceModel article;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<CountryCodes, String> getLocalImageNames() {
        return localImageNames;
    }

    public void setLocalImageNames(Map<CountryCodes, String> localImageNames) {
        this.localImageNames = localImageNames;
    }

    public ArticleServiceModel getArticle() {
        return article;
    }

    public void setArticle(ArticleServiceModel article) {
        this.article = article;
    }
}
