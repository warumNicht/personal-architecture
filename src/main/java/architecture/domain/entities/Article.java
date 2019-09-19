package architecture.domain.entities;

import architecture.domain.CountryCodes;

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
    @MapKeyColumn(name = "country_code")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CountryCodes, LocalisedArticleContent> localContent  = new HashMap<>();
}
