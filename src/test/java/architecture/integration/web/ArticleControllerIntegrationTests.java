package architecture.integration.web;

import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import architecture.domain.entities.Category;
import architecture.domain.entities.Image;
import architecture.domain.models.bindingModels.ImageBindingModel;
import architecture.domain.models.bindingModels.articles.ArticleCreateBindingModel;
import architecture.repositories.ArticleRepository;
import architecture.repositories.CategoryRepository;
import architecture.util.TestConstants;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ArticleControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void init(){
        this.seedCategory();
    }

    @Test
    public void get_createArticle_returnsCorrectView() throws Exception {
        this.mockMvc.perform(get("/fr/admin/articles/create" )
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ARTICLE_CREATE))
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
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/listAll"))
                .andDo(print());
    }

    @Test
    public void post_createArticle_withValidData_andFullImage_redirectsCorrect() throws Exception {
        ArticleCreateBindingModel correctBindingModel = this.getCorrectBindingModel();
        correctBindingModel.setMainImage(this.getCorrectImageBindingModel());
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, correctBindingModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/listAll"))
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
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel()));

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
        ArticleCreateBindingModel correctBindingModel = this.getCorrectBindingModel();
        correctBindingModel.setMainImage(this.getCorrectImageBindingModel());
        Long categoryId = this.categoryRepository.findAll().get(0).getId();

        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, categoryId.toString())
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, correctBindingModel));

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
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleCreateBindingModel()))
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
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, new ArticleCreateBindingModel()))
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
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, this.getCorrectBindingModel()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CONTROLLER_ERROR))
                .andDo(print());
    }

    //Test post when all data is valid except one invalid field
    @Test
    public void post_createArticle_withNullCountry_returnsForm() throws Exception {
        ArticleCreateBindingModel invalidModel = this.getCorrectBindingModel();
        invalidModel.setCountry(null);
        this.mockMvc.perform(post("/fr/admin/articles/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .param(ViewNames.ARTICLE_CREATE_Category_Id, "")
                .flashAttr(ViewNames.ARTICLE_CREATE_BindingModel_Name, invalidModel))
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
                .param("country", "FI"))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors(ViewNames.ARTICLE_CREATE_BindingModel_Name, ViewNames.ARTICLE_FIELD_COUNTRY))
                .andDo(print());
    }

    private ArticleCreateBindingModel getCorrectBindingModel(){
        ArticleCreateBindingModel model = new ArticleCreateBindingModel();
        model.setTitle(TestConstants.ARTICLE_VALID_TITLE);
        model.setContent(TestConstants.ARTICLE_VALID_CONTENT);
        model.setCountry(CountryCodes.FR);
        model.setMainImage(new ImageBindingModel());
        model.getMainImage().setName("");
        model.getMainImage().setUrl("");
        return model;
    }

    private ImageBindingModel getCorrectImageBindingModel(){
        ImageBindingModel imageBindingModel = new ImageBindingModel();
        imageBindingModel.setUrl(TestConstants.IMAGE_URL);
        imageBindingModel.setName(TestConstants.IMAGE_FR_NAME_2);
        return imageBindingModel;
    }

    private void seedCategory(){
        Category category = new Category();
        category.setLocalCategoryNames(new HashMap<CountryCodes, String>(){{
            put(CountryCodes.BG, TestConstants.CATEGORY_1_BG_NAME);
            put(CountryCodes.FR, TestConstants.CATEGORY_1_FR_NAME);
        }});
        this.categoryRepository.save(category);
    }
}
