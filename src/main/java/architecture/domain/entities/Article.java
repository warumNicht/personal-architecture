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

    @ElementCollection
    @CollectionTable(name = "i18n", foreignKey = @ForeignKey(name = "none"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "text")
    public Map<String, String> text = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "i18n2", foreignKey = @ForeignKey(name = "none"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "content")
    public Map<String, String> content = new HashMap<>();



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }
}
