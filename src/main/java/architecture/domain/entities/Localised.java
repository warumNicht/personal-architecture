package architecture.domain.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Localised {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;


    @ElementCollection
    @CollectionTable(name = "map_table", foreignKey = @ForeignKey(name = "none"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "country")
    @Column(name = "content")
    private Map<String,String> strings = new HashMap<>();

    //private String locale;
    //private String text;

    public Localised() {}

    public Localised(Map<String, String> map) {
        this.strings = map;
    }

    public void addString(String locale, String text) {
        strings.put(locale, text);
    }

    public String getString(String locale) {
        String returnValue = strings.get(locale);
        return (returnValue != null ? returnValue : null);
    }

}
