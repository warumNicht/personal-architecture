package architecture.integration.web.articles;

import architecture.annotations.BeginUppercase;
import architecture.annotations.ImageBindingValidationEmpty;
import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.bindingModels.articles.ArticleCreateBindingModel;
import architecture.domain.models.bindingModels.images.ImageBindingModel;
import architecture.repositories.ArticleRepository;
import architecture.repositories.CategoryRepository;
import architecture.util.TestConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser(roles = {"ADMIN"})
public class ArticleControllerChangeCategoryIntegrationTests extends ArticleControllerBaseTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    private Article seededArticle;


    @Before
    public void init() {
        super.seedCategories();
        Article article = new Article();
        article.setCategory(super.categoryRepository.findAll().get(0));
        article.setLocalContent(new HashMap<>() {{
            put(CountryCodes.FR,
                    new LocalisedArticleContent(TestConstants.ARTICLE_VALID_TITLE, TestConstants.ARTICLE_VALID_CONTENT));
        }});
        this.seededArticle = this.articleRepository.save(article);
    }

    @Test
    public void patch_changeCategory_withAdmin_works() throws Exception {
        String newCategoryId = super.categoryRepository.findAll().get(1).getId().toString();

        MockHttpServletResponse response = this.mockMvc.perform(patch("/fr/admin/articles/change-category/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCategoryId)
                .with(csrf()))
                .andExpect(status().is(202))
                .andDo(print())
                .andReturn().getResponse();

        Object responseBody = super.getObjectFromJsonString(response.getContentAsString());
        HashMap<String, Object> responseObject = (HashMap<String, Object>) responseBody;

        Assert.assertEquals(responseObject.get("oldCategoryName"), TestConstants.CATEGORY_1_FR_NAME);
        Assert.assertEquals(responseObject.get("newCategoryName"), TestConstants.CATEGORY_2_FR_NAME);
        Assert.assertEquals(responseObject.get("title"), TestConstants.ARTICLE_VALID_TITLE);
    }

    @Test
    public void patch_changeCategory_withAdmin_nonexistentCategory_returnsError() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(patch("/fr/admin/articles/change-category/555" )
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("555")
                .with(csrf()))
                .andExpect(status().is(404))
                .andDo(print()).andReturn().getResponse();

        Object res = super.getObjectFromJsonString(response.getContentAsString());
        HashMap<String, Object> hashMap = (HashMap<String, Object>) res;
        Integer status = (Integer) hashMap.get("status");
        Assert.assertEquals(404, (int) status);
    }

    @Test
    public void patch_changeCategory_withAdmin_nonexistentArticle_returnsError() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(patch("/fr/admin/articles/change-category/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("555")
                .with(csrf()))
                .andExpect(status().is(404))
                .andDo(print()).andReturn().getResponse();

        Object res = super.getObjectFromJsonString(response.getContentAsString());
        HashMap<String, Object> hashMap = (HashMap<String, Object>) res;
        Integer status = (Integer) hashMap.get("status");
        Assert.assertEquals(404, (int) status);
    }

}
