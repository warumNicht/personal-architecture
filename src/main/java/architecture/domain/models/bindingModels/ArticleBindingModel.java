package architecture.domain.models.bindingModels;

import architecture.domain.CountryCodes;

public class ArticleBindingModel {
    private Long categoryId;
    private CountryCodes country;
    private String title;
    private String content;
    private ImageBindingModel mainImage;

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
