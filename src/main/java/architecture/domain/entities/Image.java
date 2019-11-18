package architecture.domain.entities;

import architecture.domain.CountryCodes;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(name = "image_url")
    private String url;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "image_names", joinColumns = @JoinColumn(name = "image_id"))
    @MapKeyColumn(name = "country_code")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CountryCodes, String> localImageNames = new HashMap<>();

    @OneToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;


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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
