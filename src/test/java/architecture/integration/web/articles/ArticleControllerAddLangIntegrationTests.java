package architecture.integration.web.articles;

import architecture.annotations.BeginUppercase;
import architecture.annotations.ImageBindingValidationEmpty;
import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.Image;
import architecture.domain.models.bindingModels.articles.ArticleCreateBindingModel;
import architecture.domain.models.bindingModels.articles.ArticleLangBindingModel;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class ArticleControllerAddLangIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void init() {
        this.seedCategory();
    }

    @Test
    public void get_addLang_withRoleAdmin_returnsCorrectView() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_ADD_LANG))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void get_addLang_withAnonymous_redirectsLogin() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void get_addLang_withRoleUser_redirectsLogin() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void post_addLang_withRoleAdmin_validModelWithImage_redirectsEdit() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(post("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getValidArticleLangBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/articles/edit/" + article.getId()))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void post_addLang_withAnonymous_validModelWithImage_redirectsLogin() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(post("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getValidArticleLangBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void post_addLang_withUserRole_validModelWithImage_redirectsUnauthorized() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(post("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getValidArticleLangBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    private Article createArticleWithImage() {
        Article article = new Article();
        article.setMainImage(new Image());
        Article savedArticle = this.articleRepository.save(article);
        return savedArticle;
    }

    private ArticleLangBindingModel getValidArticleLangBindingModel(){
        ArticleLangBindingModel model = new ArticleLangBindingModel();
        model.setTitle(TestConstants.ARTICLE_VALID_TITLE);
        model.setContent(TestConstants.ARTICLE_VALID_CONTENT);
        model.setCountry(CountryCodes.FR);
        model.setMainImage(TestConstants.IMAGE_FR_NAME);
        return model;
    }

    private void seedCategory() {
        Category category = new Category();
        category.setLocalCategoryNames(new HashMap<CountryCodes, String>() {{
            put(CountryCodes.BG, TestConstants.CATEGORY_1_BG_NAME);
            put(CountryCodes.FR, TestConstants.CATEGORY_1_FR_NAME);
        }});
        this.categoryRepository.save(category);
    }
}
