package architecture.domain.models.bindingModels.articles;

public class ArticleEditLangBindingModel extends ArticleBindingModel {
    private Long id;
    private String mainImageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainImageName() {
        return mainImageName;
    }

    public void setMainImageName(String mainImageName) {
        this.mainImageName = mainImageName;
    }
}
