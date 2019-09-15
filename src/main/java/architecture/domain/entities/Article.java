package architecture.domain.entities;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    @Column(name = "date")
    private Date date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inter_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "inter_id"))
    @MapKey(name = "locale")
    private Map<String, TraducedText> title = new HashMap<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inter_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "inter_id"))
    @MapKey(name = "locale")
    private Map<String, TraducedText> text = new HashMap<>();



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

    public Map<String, TraducedText> getText() {
        return text;
    }

    public void setText(Map<String, TraducedText> text) {
        this.text = text;
    }
}
