package architecture.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    @Column(name = "date")
    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "title_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "inter_id"))
    @MapKey(name = "locale")
    private Map<String, TraducedText> title = new HashMap<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "content_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "inter_id"))
    @MapKey(name = "locale")
    private Map<String, TraducedText> content = new HashMap<>();



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, TraducedText> getTitle() {
        return title;
    }

    public void setTitle(Map<String, TraducedText> title) {
        this.title = title;
    }

    public Map<String, TraducedText> getContent() {
        return content;
    }

    public void setContent(Map<String, TraducedText> text) {
        this.content = text;
    }
}
