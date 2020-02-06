package architecture.integration.web.articles;

import architecture.annotations.BeginUppercase;
import architecture.annotations.ImageBindingValidationEmpty;
import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
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
public class ArticleControllerEditIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    private Article seededArticle;

    @Before
    public void init() {
        this.seededArticle = this.articleRepository.save(new Article());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getArticleEdit_withAdmin_returnsCorrectView() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_EDIT))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void getArticleEdit_withAnonymous_redirectsLogin() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void getArticleEdit_withRoleUser_redirectsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("/**/fr/unauthorized"))
                .andDo(print());
    }


}
