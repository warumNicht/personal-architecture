package architecture.services;

import architecture.domain.entities.Article;
import architecture.domain.models.serviceModels.ArticleServiceModel;
import architecture.repositories.ArticleRepository;
import architecture.services.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ArticleServiceModel findById(Long id){
        Article article = this.articleRepository.findById(id).orElse(null);
        ArticleServiceModel articleServiceModel = this.modelMapper.map(article, ArticleServiceModel.class);
        return articleServiceModel;
    }

    @Override
    public void updateArticle(ArticleServiceModel article) {
        Article articleToUpdate = this.modelMapper.map(article, Article.class);
        this.articleRepository.saveAndFlush(articleToUpdate);
    }
}
