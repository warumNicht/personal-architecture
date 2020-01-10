package architecture.integration.web;

import architecture.constants.AppConstants;
import architecture.domain.entities.Article;
import architecture.domain.models.bindingModels.ImageEditBindingModel;
import architecture.repositories.ArticleRepository;
import architecture.util.TestConstants;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Image;
import architecture.repositories.ImageRepository;
import architecture.util.LocaleMessageUtil;

import architecture.util.TestUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ImageControllerIntegrationTests {
    private static final String MODEL_FIELD_url="url";
    private static final String MODEL_FIELD_localImageNames="localImageNames";

    private Image savedImage;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void init() {
        Article article = new Article();
        article = this.articleRepository.save(article);
        Image image = new Image();
        image.setUrl(TestConstants.IMAGE_URL);
        image.setLocalImageNames(new HashMap<>() {{
            put(CountryCodes.FR, TestConstants.IMAGE_FR_NAME);
            put(CountryCodes.BG, TestConstants.IMAGE_BG_NAME);
            put(CountryCodes.ES, TestConstants.IMAGE_ES_NAME);
        }});
        image.setArticle(article);
        this.savedImage = this.imageRepository.save(image);
    }

    @Test
    public void getImage_returnsCorrectView() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/fr/admin/images/edit/" + this.savedImage.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(AppConstants.IMAGE_EDIT_VIEW))
                .andDo(print())
                .andReturn().getResponse();
        String contentAsString = response.getContentAsString();
        Assert.assertTrue(contentAsString.contains(TestConstants.IMAGE_URL));

        for (String value : this.savedImage.getLocalImageNames().values()) {
            String escapedValue = StringEscapeUtils.escapeHtml4(value);
            Assert.assertTrue(contentAsString.contains(escapedValue));
        }
    }

    @Test
    public void getInexistentImage_returnsErrorPage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/fr/admin/images/edit/111")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(AppConstants.CONTROLLER_ERROR_VIEW))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains("Custom error"));

        String expectedErrorMessage = TestUtils.escapeHTML(LocaleMessageUtil.getLocalizedMessage("archSentence", Locale.FRANCE));
        boolean contains = contentAsString.contains(expectedErrorMessage);
        Assert.assertTrue(contains);
    }

    @Test
    public void putImage_withCorrectData_redirectsCorrectAndModifiesData() throws Exception {
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, this.getCorrectBindingModel()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/fr/admin/articles/edit/" + this.savedImage.getArticle().getId()))
                .andDo(print());
        Image modifiedImage = this.imageRepository.findAll().get(0);
        int actualSize = modifiedImage.getLocalImageNames().size();
        Assert.assertEquals(2, actualSize);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.BG), TestConstants.IMAGE_BG_NAME_2);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME_2);
        Assert.assertEquals(modifiedImage.getUrl(), TestConstants.IMAGE_URL_2);
    }

    @Test
    public void putImage_withCorrectData_andInvalidId_returnsErrorPage() throws Exception {
        this.mockMvc.perform(put("/admin/images/edit/555")
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, this.getCorrectBindingModel()))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name(AppConstants.CONTROLLER_ERROR_VIEW))
                .andDo(print());
        Image modifiedImage = this.imageRepository.findAll().get(0);
        int actualSize = modifiedImage.getLocalImageNames().size();
        Assert.assertEquals(3, actualSize);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.BG), TestConstants.IMAGE_BG_NAME);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME);
        Assert.assertEquals(modifiedImage.getUrl(), TestConstants.IMAGE_URL);
    }

    @Test
    public void putImage_withInvalidData_doesNotModifyData() throws Exception {
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, this.getIncorrectBindingModel()));
        Image modifiedImage = this.imageRepository.findAll().get(0);
        int expectedSize = this.savedImage.getLocalImageNames().size();
        int actualSize = modifiedImage.getLocalImageNames().size();
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.BG), TestConstants.IMAGE_BG_NAME);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME);
        Assert.assertEquals(modifiedImage.getUrl(), TestConstants.IMAGE_URL);
    }

    @Test
    public void putImage_withInvalidData_returnsEditPage() throws Exception {
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, this.getIncorrectBindingModel()))
                .andExpect(status().isOk())
                .andExpect(view().name(AppConstants.IMAGE_EDIT_VIEW));
    }

    @Test
    public void putImage_withNullDataFields_hasErrors() throws Exception {
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, this.getIncorrectBindingModel()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(model().attributeHasFieldErrorCode(AppConstants.IMAGE_EDIT_BindingModel_Name, MODEL_FIELD_localImageNames, "NotNull"));
    }

    @Test
    public void putImage_withInvalidLocalImageName_hasErrors() throws Exception {
        ImageEditBindingModel invalidBindingModel = this.getCorrectBindingModel();
        invalidBindingModel.getLocalImageNames().put(CountryCodes.BG,"");
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, invalidBindingModel))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2));
    }

    @Test
    public void putImage_withEmptyUrl_hasErrors() throws Exception {
        ImageEditBindingModel invalidBindingModel = this.getCorrectBindingModel();
        invalidBindingModel.setUrl("");
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, invalidBindingModel))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors(AppConstants.IMAGE_EDIT_BindingModel_Name,MODEL_FIELD_url));
    }

    @Test
    public void putImage_withNullUrl_hasErrors() throws Exception {
        ImageEditBindingModel invalidBindingModel = this.getCorrectBindingModel();
        invalidBindingModel.setUrl(null);
        this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(AppConstants.IMAGE_EDIT_BindingModel_Name, invalidBindingModel))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors(AppConstants.IMAGE_EDIT_BindingModel_Name,MODEL_FIELD_url));
    }

    private ImageEditBindingModel getCorrectBindingModel() {
        ImageEditBindingModel bindingModel = new ImageEditBindingModel();
        bindingModel.setLocalImageNames(new LinkedHashMap<>() {{
            put(CountryCodes.BG, TestConstants.IMAGE_BG_NAME_2);
            put(CountryCodes.FR, TestConstants.IMAGE_FR_NAME_2);
        }});
        bindingModel.setUrl(TestConstants.IMAGE_URL_2);
        return bindingModel;
    }

    private ImageEditBindingModel getIncorrectBindingModel() {
        return new ImageEditBindingModel();
    }


    // тест за хвърлена грешка от GlobalExceptionHandler
//    @Test
//    public void getInexistentImage_returnsNotFound() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(get("/fr/admin/images/edit/111")
//                .locale(Locale.FRANCE)
//                .contextPath("/fr")
//                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
//                .andExpect(status().isNotFound())
////                .andExpect(view().name("error/custom-error"))
//                .andDo(print())
//                .andReturn();
//        Exception resolvedException = mvcResult.getResolvedException();
//        String message = resolvedException.getMessage();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        Assert.assertTrue(resolvedException instanceof CategoryNotFoundException);
//        System.out.println();
//
//    }
}
