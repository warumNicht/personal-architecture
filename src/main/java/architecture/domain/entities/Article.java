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
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "localised_content", joinColumns = @JoinColumn(name = "article_id"))
    @MapKeyColumn(name = "country_code")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CountryCodes, LocalisedArticleContent> localContent  = new HashMap<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<CountryCodes, LocalisedArticleContent> getLocalContent() {
        return localContent;
    }

    public void setLocalContent(Map<CountryCodes, LocalisedArticleContent> localContent) {
        this.localContent = localContent;
    }
}
