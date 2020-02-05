package architecture.integration.web;

import architecture.annotations.BeginUppercase;
import architecture.annotations.ImageBindingValidationEmpty;
import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.Image;
import architecture.domain.models.bindingModels.articles.ArticleAddEditLangBindingModel;
import architecture.domain.models.bindingModels.images.ImageBindingModel;
import architecture.domain.models.bindingModels.articles.ArticleCreateBindingModel;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser(roles = {"ADMIN"})
public class ArticleControllerIntegrationTests {
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
    public void get_createArticle_withRoleAdmin_returnsCorrectView() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_CREATE))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void get_createArticle_anonymous_redirectsLogin() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void get_createArticle_withRoleUser_redirectsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/fr/unauthorized"))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withValidData_andEmptyImage_redirectsCorrect() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/listAll"))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withValidData_andFullImage_redirectsCorrect() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getFullCorrectBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/listAll"))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void post_createArticle_withValidData_Anonymous_redirectsLogin() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getFullCorrectBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void post_createArticle_withValidData_RoleUser_redirectsUnauthorized() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getFullCorrectBindingModel())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withValidData_AndEmptyImage_storesCorrect() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel())
                .with(csrf()));

        Article storedArticle = this.articleRepository.findAll().get(0);

        Assert.assertEquals(storedArticle.getCategory().getId(), categoryId);
        Assert.assertEquals(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.BG), TestConstants.CATEGORY_1_BG_NAME);
        Assert.assertEquals(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.FR), TestConstants.CATEGORY_1_FR_NAME);
        Assert.assertNull(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.DE));

        Assert.assertEquals(storedArticle.getLocalContent().size(), 1);
        Assert.assertEquals(storedArticle.getLocalContent().get(CountryCodes.FR).getTitle(), TestConstants.ARTICLE_VALID_TITLE);
        Assert.assertEquals(storedArticle.getLocalContent().get(CountryCodes.FR).getContent(), TestConstants.ARTICLE_VALID_CONTENT);

        Assert.assertNull(storedArticle.getMainImage());
    }

    @Test
    public void post_createArticle_withValidData_AndFullImage_storesCorrect() throws Exception {
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getFullCorrectBindingModel())
                .with(csrf()));

        Article storedArticle = this.articleRepository.findAll().get(0);

        Assert.assertEquals(storedArticle.getCategory().getId(), categoryId);
        Assert.assertEquals(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.BG), TestConstants.CATEGORY_1_BG_NAME);
        Assert.assertEquals(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.FR), TestConstants.CATEGORY_1_FR_NAME);
        Assert.assertNull(storedArticle.getCategory().getLocalCategoryNames().get(CountryCodes.DE));

        Assert.assertEquals(storedArticle.getLocalContent().size(), 1);
        Assert.assertEquals(storedArticle.getLocalContent().get(CountryCodes.FR).getTitle(), TestConstants.ARTICLE_VALID_TITLE);
        Assert.assertEquals(storedArticle.getLocalContent().get(CountryCodes.FR).getContent(), TestConstants.ARTICLE_VALID_CONTENT);

        Assert.assertNotNull(storedArticle.getMainImage());
        Assert.assertEquals(storedArticle.getMainImage().getUrl(), TestConstants.IMAGE_URL);
        Assert.assertEquals(storedArticle.getMainImage().getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME_2);
    }

    @Test
    public void post_createArticle_withNullData_returnsForm() throws Exception {
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleCreateBindingModel())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_CREATE))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withInvalidData_doesNotModifyData() throws Exception {
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "invalidId")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleCreateBindingModel())
                .with(csrf()))
                .andDo(print());

        Assert.assertEquals(this.articleRepository.count(), 0L);
    }

    @Test
    public void post_createArticle_withValidData_AndNonexistentCategory_returnsErrorPage() throws Exception {
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, TestConstants.CATEGORY_INVALID_ID)
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CONTROLLER_ERROR))
                .andDo(print());
    }

    //Test post when all data is valid except one invalid field

    //Country
    @Test
    public void post_createArticle_withNullCountry_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setCountry(null);
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode(ViewNames.ARTICLE_CREATE_BindingModel_Name, ViewNames.ARTICLE_FIELD_COUNTRY, "NotNull"))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withInvalidCountry_returnsErrorPage() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setCountry(null);
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .param("title", TestConstants.ARTICLE_VALID_TITLE)
                .param("content", TestConstants.ARTICLE_VALID_CONTENT)
                .param("mainImage.url", TestConstants.IMAGE_URL)
                .param("mainImage.name", TestConstants.IMAGE_FR_NAME)
                .param("country", "FI")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors(ViewNames.ARTICLE_CREATE_BindingModel_Name, ViewNames.ARTICLE_FIELD_COUNTRY))
                .andDo(print());
    }

    //Url
    @Test
    public void post_createArticle_withNullUrl_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.getMainImage().setUrl(null);
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andDo(print());
    }

    @Test
    public void post_createArticle_invalidUrl_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getFullCorrectBindingModel();
        invalidModel.getMainImage().setUrl("invalidUrl");
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrorCode(ViewNames.ARTICLE_CREATE_BindingModel_Name, "mainImage.url", ImageBindingValidationEmpty.class.getSimpleName()))
                .andDo(print());
    }

    //Title
    @Test
    public void post_createArticle_withNullTitle_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setTitle(null);
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withEmptyTitle_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setTitle("");
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withLowercaseTitle_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setTitle("title");
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode(ViewNames.ARTICLE_CREATE_BindingModel_Name, "title", BeginUppercase.class.getSimpleName()))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withTitleLessThen2_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setTitle("T");
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode(ViewNames.ARTICLE_CREATE_BindingModel_Name, "title", Size.class.getSimpleName()))
                .andDo(print());
    }

    // testing admin/articles/addLang/{id}
    @Test
    public void get_addLang_withRoleAdmin_returnsCorrectView() throws Exception {
        Article article = this.seedArticle();
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
        Article article = this.seedArticle();
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
        Article article = this.seedArticle();
        this.mockMvc.perform(get("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void post_addLang_withRoleAdmin_returnsCorrectView() throws Exception {
        Article article = this.seedArticle();
        this.mockMvc.perform(post("/fr/admin/articles/addLang/" + article.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleAddEditLangBindingModel())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_ADD_LANG))
                .andDo(print());
    }

    private Article seedArticle() {
        Article article = new Article();
        article.setMainImage(new Image());
        Article savedArticle = this.articleRepository.save(article);
        return savedArticle;
    }

    private ArticleCreateBindingModel getCorrectBindingModel() {
        ArticleCreateBindingModel model = new ArticleCreateBindingModel();
        model.setTitle(TestConstants.ARTICLE_VALID_TITLE);
        model.setContent(TestConstants.ARTICLE_VALID_CONTENT);
        model.setCountry(CountryCodes.FR);
        model.setMainImage(new ImageBindingModel());
        model.getMainImage().setName("");
        model.getMainImage().setUrl("");
        return model;
    }

    private ArticleCreateBindingModel getFullCorrectBindingModel() {
        ArticleCreateBindingModel correctBindingModel = this.getCorrectBindingModel();
        correctBindingModel.getMainImage().setUrl(TestConstants.IMAGE_URL);
        correctBindingModel.getMainImage().setName(TestConstants.IMAGE_FR_NAME_2);
        return correctBindingModel;
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
