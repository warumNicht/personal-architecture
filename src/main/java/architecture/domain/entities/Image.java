package architecture.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(name = "image_name")
    private String name;

    @Column(name = "image_url")
    private String url;

    @OneToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
