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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NAME_ID")
    private Localised nameStrings = new Localised();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DESCRIPTION_ID")
    private Localised descriptionStrings = new Localised();

    private static final long serialVersionUID = 1L;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName(String locale) {
        return this.nameStrings.getString(locale);
    }

    public void setName(String locale, String name) {
        this.nameStrings.addString(locale, name);
    }

    public String getDescription(String locale) {
        return this.descriptionStrings.getString(locale);
    }

    public void setDescription(String locale, String description) {
        this.descriptionStrings.addString(locale, description);
    }

    public Localised getNameStrings() {
        return nameStrings;
    }

    public void setNameStrings(Localised nameStrings) {
        this.nameStrings = nameStrings;
    }

    public Localised getDescriptionStrings() {
        return descriptionStrings;
    }

    public void setDescriptionStrings(Localised descriptionStrings) {
        this.descriptionStrings = descriptionStrings;
    }
}
