package architecture.domain.models.viewModels;

import architecture.domain.models.BaseModel;

public class LocalisedCategoryViewModel extends BaseModel {
    private String name;

    public LocalisedCategoryViewModel(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
