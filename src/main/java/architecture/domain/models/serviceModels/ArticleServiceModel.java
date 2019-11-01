package architecture.domain.models.serviceModels;

import architecture.domain.CountryCodes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArticleServiceModel extends BaseServiceModel {
    private Date date;
    private CategoryServiceModel category;
    private Map<CountryCodes, LocalisedArticleContentServiceModel> localContent = new HashMap<>();

    public ArticleServiceModel() {
    }

    public ArticleServiceModel(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public Map<CountryCodes, LocalisedArticleContentServiceModel> getLocalContent() {
        return localContent;
    }

    public void setLocalContent(Map<CountryCodes, LocalisedArticleContentServiceModel> localContent) {
        this.localContent = localContent;
    }
}
