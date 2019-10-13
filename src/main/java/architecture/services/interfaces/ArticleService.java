package architecture.services.interfaces;

import architecture.domain.CountryCodes;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.viewModels.ArticleLocalViewModel;

import java.util.List;

public interface ArticleService {

    void createArticle(ArticleServiceModel article);

    ArticleServiceModel findById(Long id);

    void updateArticle(ArticleServiceModel article);

    List<ArticleLocalViewModel> findArticlesByCategory(Long id, CountryCodes wantedCode);
}
