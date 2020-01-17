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
    public void post_createArticle_withValidData_emptyImage_redirectsCorrect() throws Exception {
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
    public void post_createArticle_withValidData_emptyImage_storesCorrect() throws Exception {
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

    }

    private ArticleCreateBindingModel getCorrectBindingModel(){
        ArticleCreateBindingModel model = new ArticleCreateBindingModel();
        model.setTitle("Fofofo");
        model.setContent("Content");
        model.setCountry(CountryCodes.DE);
        model.setMainImage(new ImageBindingModel());
        model.getMainImage().setName("");
        model.getMainImage().setUrl("");
        return model;
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
