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
}
