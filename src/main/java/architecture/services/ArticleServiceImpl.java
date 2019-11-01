package architecture.services;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.domain.models.viewModels.ArticleLocalViewModel;
import architecture.domain.models.viewModels.LocalisedArticleContentViewModel;
import architecture.repositories.ArticleRepository;
import architecture.services.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createArticle(ArticleServiceModel article) {
        Article articleToCreate = this.modelMapper.map(article, Article.class);
        this.articleRepository.saveAndFlush(articleToCreate);
    }

    @Override
    public ArticleServiceModel findById(Long id) {
        Article article = this.articleRepository.findById(id).orElse(null);
        ArticleServiceModel articleServiceModel = this.modelMapper.map(article, ArticleServiceModel.class);
        return articleServiceModel;
    }

    @Override
    public void updateArticle(ArticleServiceModel article) {
        Article articleToUpdate = this.modelMapper.map(article, Article.class);
        this.articleRepository.saveAndFlush(articleToUpdate);
    }

    @Override
    public List<ArticleLocalViewModel> findArticlesByCategory(Long id, CountryCodes wantedCode) {
        Object[] all = this.articleRepository.getAllByCategory(CountryCodes.BG, wantedCode, id);

        List<ArticleLocalViewModel> localisedArticles = new ArrayList<>();
        for (Object article : all) {
            Object[] articleObjects = (Object[]) article;
            ArticleLocalViewModel articleLocalViewModel = new ArticleLocalViewModel();
            articleLocalViewModel.setId((Long) articleObjects[0]);
            articleLocalViewModel.setDate((Date) articleObjects[1]);
            LocalisedArticleContent localisedArticleContent = (LocalisedArticleContent) articleObjects[2];
            articleLocalViewModel.setLocalisedContent(this.modelMapper.map(localisedArticleContent, LocalisedArticleContentViewModel.class));
            localisedArticles.add(articleLocalViewModel);
        }
        return localisedArticles;
    }
}
