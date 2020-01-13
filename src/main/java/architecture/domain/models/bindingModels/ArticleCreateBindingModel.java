package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.annotations.EnumValidator;
import architecture.domain.CountryCodes;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleCreateBindingModel {
    @NotNull(message = "{value.null}")
    private Long categoryId;

    @NotNull(message = "{value.null}")
    @EnumValidator(enumClass = CountryCodes.class, message = "{value.inexistent.country}")
    private CountryCodes country;

    @NotNull(message = "{value.null}")
    @NotEmpty(message = "{value.empty}")
    @Size(min = 4)
    @BeginUppercase(allowEmpty = true)
    private String title;

    @NotNull(message = "{value.null}")
    @NotEmpty(message = "{value.empty}")
    @Size(min = 4)
    @BeginUppercase(allowEmpty = true)
    private String content;

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
