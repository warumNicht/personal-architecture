package architecture.domain.models.viewModels;

import java.util.Date;

public class ArticleLocalViewModel {
    private Long id;
    private Date date;
    private String mainImage;
    private String name;
    private LocalisedArticleContentViewModel localisedContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalisedArticleContentViewModel getLocalisedContent() {
        return localisedContent;
    }

    public void setLocalisedContent(LocalisedArticleContentViewModel localisedContent) {
        this.localisedContent = localisedContent;
    }
}
