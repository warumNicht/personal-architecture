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
