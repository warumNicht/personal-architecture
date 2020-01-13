package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.EnumValidator;
import architecture.constants.AppConstants;
import architecture.domain.CountryCodes;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleCreateBindingModel {
    @NotNull
    @Min(value = 1)
    private Long categoryId;

    @NotNull
    @EnumValidator(enumClass = CountryCodes.class, message = "{country.nonexistent}")
    private CountryCodes country;

    @NotNull
    @NotEmpty(message = "{value.empty}")
    @Size(min = AppConstants.NAME_MIN_LENGTH, max = AppConstants.NAME_MAX_LENGTH, message = "{text.length.between}")
    @BeginUppercase(allowEmpty = true)
    private String title;

    @NotNull
    @NotEmpty(message = "{value.empty}")
    @Size(min = AppConstants.DESCRIPTION_MIN_LENGTH, message = "{text.length.min}")
    @BeginUppercase(allowEmpty = true)
    private String content;

    @NotNull
    @Valid
    private ImageBindingModel mainImage;

    public ArticleCreateBindingModel() {
        this.mainImage = new ImageBindingModel();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CountryCodes getCountry() {
        return country;
    }

    public void setCountry(CountryCodes country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ImageBindingModel getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageBindingModel mainImage) {
        this.mainImage = mainImage;
    }
}
