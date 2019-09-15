package architecture.domain.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class Category implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NAME_ID")
    private Localised nameStrings = new Localised();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DESCRIPTION_ID")
    private Localised descriptionStrings = new Localised();

    private static final long serialVersionUID = 1L;

    public Category() {
        super();
    }

    public Category(String locale, String name, String description) {
        this.nameStrings.addString(locale, name);
        this.descriptionStrings.addString(locale, description);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
