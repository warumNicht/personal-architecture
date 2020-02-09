package architecture.integration.web.articles;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.Image;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.repositories.ArticleRepository;
import architecture.repositories.CategoryRepository;
import architecture.util.TestConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.HashMap;

public abstract class ArticleControllerBaseTests {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ArticleRepository articleRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    protected void seedCategories() {
        Category category = new Category();
        category.setLocalCategoryNames(new HashMap<CountryCodes, String>() {{
            put(CountryCodes.BG, TestConstants.CATEGORY_1_BG_NAME);
            put(CountryCodes.FR, TestConstants.CATEGORY_1_FR_NAME);
        }});
        this.categoryRepository.save(category);

        Category category2 = new Category();
        category2.setLocalCategoryNames(new HashMap<CountryCodes, String>() {{
            put(CountryCodes.BG, TestConstants.CATEGORY_2_BG_NAME);
            put(CountryCodes.FR, TestConstants.CATEGORY_2_FR_NAME);
        }});
        this.categoryRepository.save(category2);
    }

    protected String getJsonFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    protected Object getObjectFromJsonString(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Object.class);
    }

    protected Article createArticleWithImage() {
        Article article = new Article();
        LocalisedArticleContent localisedContent = new LocalisedArticleContent();
        localisedContent.setTitle(TestConstants.ARTICLE_VALID_TITLE);
        localisedContent.setContent(TestConstants.ARTICLE_VALID_CONTENT);

        article.setLocalContent(new HashMap<>() {{
            put(CountryCodes.FR, localisedContent);
        }});
        article.setMainImage(new Image());
        return this.articleRepository.save(article);
    }
}
