package architecture.integration.web.articles;

import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.models.bindingModels.articles.ArticleAddImageBindingModel;
import architecture.domain.models.bindingModels.images.ImageBindingModel;
import architecture.util.TestConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser(roles = {"ADMIN"})
public class ArticleControllerAddImageIntegrationTests extends ArticleControllerBaseTests {
    private Article seededArticle;

    @Before
    public void init() {
        this.seededArticle = super.createArticleWithImage();
    }

    @Test
    public void getArticleAddImage_withAdmin_returnsCorrectView() throws Exception {
        super.mockMvc.perform(get("/fr/admin/articles/add-image/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_ADD_IMAGE))
                .andDo(print());
    }

    @Test
    public void putArticleAddImage_withAdmin_validData_returnsCorrectUrl() throws Exception {
        String requestBody = super.getJsonFromObject(this.createValidArticleAddImageBindingModel());

        MockHttpServletResponse res = super.mockMvc.perform(put("/fr/admin/articles/add-image/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(csrf()))
                .andExpect(status().is(202))
                .andDo(print()).andReturn().getResponse();

        String contentAsString = res.getContentAsString();
        Assert.assertEquals(contentAsString, "\"/fr/admin/articles/edit/" + this.seededArticle.getId() + "\"");
    }

    @Test
    public void putArticleAddImage_withAdmin_nullData_returnsErrorMap() throws Exception {
        MockHttpServletResponse res = super.mockMvc.perform(put("/fr/admin/articles/add-image/" + this.seededArticle.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.getJsonFromObject(new ArticleAddImageBindingModel()))
                .with(csrf()))
                .andExpect(status().is(405))
                .andDo(print()).andReturn().getResponse();

        HashMap<String, List<String>> errorMap = (HashMap<String, List<String>>) super.getObjectFromJsonString(res.getContentAsString());
        Assert.assertEquals(2, errorMap.size());
    }

    private ArticleAddImageBindingModel createValidArticleAddImageBindingModel() {
        ArticleAddImageBindingModel model = new ArticleAddImageBindingModel();
        model.setLang(CountryCodes.ES);

        ImageBindingModel image = new ImageBindingModel();
        image.setName(TestConstants.IMAGE_ES_NAME);
        image.setUrl(TestConstants.IMAGE_URL);

        model.setImage(image);
        return model;
    }


}
