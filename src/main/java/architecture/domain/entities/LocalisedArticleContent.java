package architecture.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LocalisedArticleContent{
    @Column(name = "article_title")
    private String title;
    @Column(name = "article_content")
    private String content;

    public LocalisedArticleContent() {
    }

    public LocalisedArticleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
