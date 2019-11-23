package architecture.domain.models.viewModels;

import java.util.Date;

public class ArticleLocalViewModel {
    private Long id;
    private Date date;
    private ImageLocaleViewModel mainImage;
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

    public ImageLocaleViewModel getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageLocaleViewModel mainImage) {
        this.mainImage = mainImage;
    }

    public LocalisedArticleContentViewModel getLocalisedContent() {
        return localisedContent;
    }

    public void setLocalisedContent(LocalisedArticleContentViewModel localisedContent) {
        this.localisedContent = localisedContent;
    }
}
