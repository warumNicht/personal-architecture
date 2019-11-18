package architecture.domain.models.serviceModels;

public class ImageServiceModel extends BaseServiceModel{
    private String name;
    private String url;
    private ArticleServiceModel article;

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

    public ArticleServiceModel getArticle() {
        return article;
    }

    public void setArticle(ArticleServiceModel article) {
        this.article = article;
    }
}
