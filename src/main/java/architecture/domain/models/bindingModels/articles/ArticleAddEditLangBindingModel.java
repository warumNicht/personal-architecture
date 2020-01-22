package architecture.domain.models.bindingModels.articles;

import architecture.annotations.BeginUppercase;
import architecture.annotations.LengthOrEmpty;
import architecture.constants.AppConstants;
import architecture.domain.models.bindingModels.ImageBindingModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleAddEditLangBindingModel extends ArticleBindingModel {
    @NotNull
    @Min(value = 1)
    private Long id;

    @Length(min = AppConstants.NAME_MIN_LENGTH, max = AppConstants.NAME_MAX_LENGTH, message = "{text.length.between}")
    @BeginUppercase(allowNull = true)
    private String mainImage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
