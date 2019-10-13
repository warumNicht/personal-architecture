package architecture.services.interfaces;

import architecture.domain.models.serviceModels.ArticleServiceModel;

public interface ArticleService {

    void createArticle(ArticleServiceModel article);

    ArticleServiceModel findById(Long id);

    void updateArticle(ArticleServiceModel article);
}
