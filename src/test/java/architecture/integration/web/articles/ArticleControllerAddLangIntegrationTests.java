package architecture.integration.web.articles;

import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Image;
import architecture.domain.entities.LocalisedArticleContent;
import architecture.domain.models.bindingModels.articles.ArticleLangBindingModel;
import architecture.repositories.ArticleRepository;
import architecture.util.TestConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
public class ArticleControllerAddLangIntegrationTests extends ArticleControllerBaseTests{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

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
    public void post_addLang_withRoleAdmin_validModelWithImageNull_redirectsEdit() throws Exception {
        Article article = this.articleRepository.save(new Article());
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

    @Test
    public void post_addLang_withAdmin_nullModel_returnsForm() throws Exception {
        Article article = this.articleRepository.save(new Article());
        this.mockMvc.perform(post("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleLangBindingModel())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name(ViewNames.ARTICLE_ADD_LANG))
                .andDo(print());
    }

    // admin/articles/edit/{articleId}/{lang}

    @Test
    public void get_editExistingLang_withRoleAdmin_returnsCorrectView() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + article.getId() + "/FR")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_EDIT_LANG))
                .andDo(print());
    }

    @Test
    public void get_editNonExistingLang_withRoleAdmin_returnsNotFound() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + article.getId() + "/DE")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CONTROLLER_ERROR))
                .andDo(print());
    }

    @Test
    public void get_editNonExistingArticle_withRoleAdmin_returnsNotFound() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/edit/1054/DE")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CONTROLLER_ERROR))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void get_editLang_withAnonymous_redirectsLogin() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + article.getId() + "/FR")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void get_editLang_withRoleUser_redirectsUnauthorized() throws Exception {
        Article article = this.createArticleWithImage();
        this.mockMvc.perform(get("/fr/admin/articles/edit/" + article.getId() + "/FR")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void patch_editLang_withRoleAdmin_validModel_returnsCorrectView_andModifiesData() throws Exception {
        Article article = this.createArticleWithImage();
        ArticleLangBindingModel bindingModel = this.getValidArticleLangBindingModel();
        bindingModel.setId(article.getId());

        String requestBody = super.getJsonFromObject(bindingModel);

        MockHttpServletResponse response = this.mockMvc.perform(patch("/fr/admin/articles/edit/")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(csrf()))
                .andExpect(status().is(202))
                .andDo(print()).andReturn().getResponse();

        Assert.assertEquals(response.getContentAsString(),
                "\"/fr/admin/articles/edit/" + article.getId() + "\"");
        Article modified = this.articleRepository.findById(article.getId()).orElse(null);

        Assert.assertEquals(modified.getLocalContent().get(CountryCodes.FR).getTitle(), TestConstants.ARTICLE_VALID_TITLE_2);
        Assert.assertEquals(modified.getLocalContent().get(CountryCodes.FR).getContent(), TestConstants.ARTICLE_VALID_CONTENT_2);
        Assert.assertEquals(modified.getMainImage().getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME_2);
    }

    @Test
    @WithAnonymousUser
    public void patch_editLang_withAnonymous_redirectsLogin() throws Exception {
        this.mockMvc.perform(patch("/fr/admin/articles/edit/")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void patch_editLang_withRoleUser_redirectsUnauthorized() throws Exception {
        this.mockMvc.perform(patch("/fr/admin/articles/edit/")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void patch_editLang_withRoleAdmin_invalidModel_returnsForm() throws Exception {
        String requestBody = super.getJsonFromObject(new ArticleLangBindingModel());

        MockHttpServletResponse response = this.mockMvc.perform(patch("/fr/admin/articles/edit/")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(csrf()))
                .andExpect(status().is(202))
                .andDo(print()).andReturn().getResponse();

        HashMap<String, List<String>> errorMap = (HashMap<String, List<String>>) this.getObjectFromJsonString(response.getContentAsString());
        Assert.assertTrue(errorMap.size()>0);
    }

    @Test
    public void patch_editLang_withRoleAdmin_nonexistentArticle_returnsNotFound() throws Exception {
        ArticleLangBindingModel bindingModel = this.getValidArticleLangBindingModel();
        bindingModel.setId(345L);
        String requestBody = super.getJsonFromObject(bindingModel);

        this.mockMvc.perform(patch("/fr/admin/articles/edit/")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(view().name(ViewNames.CONTROLLER_ERROR))
                .andDo(print());
    }

    private Article createArticleWithImage() {
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

    private ArticleLangBindingModel getValidArticleLangBindingModel() {
        ArticleLangBindingModel model = new ArticleLangBindingModel();
        model.setTitle(TestConstants.ARTICLE_VALID_TITLE_2);
        model.setContent(TestConstants.ARTICLE_VALID_CONTENT_2);
        model.setCountry(CountryCodes.FR);
        model.setMainImage(TestConstants.IMAGE_FR_NAME_2);
        return model;
    }

}
