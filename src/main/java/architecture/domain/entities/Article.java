package architecture.domain.entities;

import architecture.domain.CountryCodes;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Image mainImage;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "localised_content", joinColumns = @JoinColumn(name = "article_id"))
    @MapKeyColumn(name = "country_code")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CountryCodes, LocalisedArticleContent> localContent = new HashMap<>();

    public Article() {
    }

    public Article(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<CountryCodes, LocalisedArticleContent> getLocalContent() {
        return localContent;
    }

    public void setLocalContent(Map<CountryCodes, LocalisedArticleContent> localContent) {
        this.localContent = localContent;
    }
}
