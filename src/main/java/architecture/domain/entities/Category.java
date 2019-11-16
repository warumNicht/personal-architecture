package architecture.domain.entities;

import architecture.domain.CountryCodes;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "category_names", joinColumns = @JoinColumn(name = "category_id"))
    @MapKeyColumn(name = "country_code")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CountryCodes, String> localCategoryNames = new HashMap<>();

    public Map<CountryCodes, String> getLocalCategoryNames() {
        return localCategoryNames;
    }

    public void setLocalCategoryNames(Map<CountryCodes, String> localCategoryNames) {
        this.localCategoryNames = localCategoryNames;
    }
}
