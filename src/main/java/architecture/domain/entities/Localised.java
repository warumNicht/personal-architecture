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
    private Map<String,String> strings = new HashMap<String, String>();

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
