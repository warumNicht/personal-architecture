package architecture.domain.models.bindingModels.articles;

import architecture.annotations.BeginUppercase;
import architecture.constants.AppConstants;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleEditLangBindingModel extends ArticleBindingModel {
    @NotNull
    @Min(value = 1)
    private Long id;

    @NotNull
    @NotEmpty(message = "{text.empty}")
    @Size(min = AppConstants.NAME_MIN_LENGTH, max = AppConstants.NAME_MAX_LENGTH, message = "{text.length.between}")
    @BeginUppercase
    private String mainImageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainImageName() {
        return mainImageName;
    }

    public void setMainImageName(String mainImageName) {
        this.mainImageName = mainImageName;
    }
}
