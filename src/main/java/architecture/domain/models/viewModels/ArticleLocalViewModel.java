package architecture.domain.models.viewModels;

import architecture.domain.entities.LocalisedArticleContent;

import java.util.Date;

public class ArticleLocalViewModel {
    private Long id;
    private Date date;
    private LocalisedArticleContent localisedContent;

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

    public LocalisedArticleContent getLocalisedContent() {
        return localisedContent;
    }

    public void setLocalisedContent(LocalisedArticleContent localisedContent) {
        this.localisedContent = localisedContent;
    }
}
