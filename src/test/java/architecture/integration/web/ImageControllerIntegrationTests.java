package architecture.integration.web;

import architecture.domain.entities.Article;
import architecture.domain.models.bindingModels.ImageEditBindingModel;
import architecture.repositories.ArticleRepository;
import architecture.util.TestConstants;
import architecture.constants.ApplicationConstants;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ImageControllerIntegrationTests {
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
            put(CountryCodes.FR, TestConstants.IMAGE_BG_NAME);
            put(CountryCodes.BG, TestConstants.IMAGE_ES_NAME);
            put(CountryCodes.ES, TestConstants.IMAGE_FR_NAME);
        }});
        image.setArticle(article);
        this.savedImage=this.imageRepository.save(image);
    }

    @Test
    public void getImage_returnsCorrectView() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/fr/admin/images/edit/" + this.savedImage.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-image"))
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
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ApplicationConstants.CONTROLLER_ERROR_VIEW))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains("Custom error"));

        String expectedErrorMessage = TestUtils.escapeHTML(LocaleMessageUtil.getLocalizedMessage("archSentence", Locale.FRANCE));
        boolean contains = contentAsString.contains(expectedErrorMessage);
        Assert.assertTrue(contains);
    }

    @Test
    public void putImage_withCorrectData_redirectsCorrect() throws Exception {
        ImageEditBindingModel bindingModel = new ImageEditBindingModel();
        bindingModel.setLocalImageNames(new LinkedHashMap<>(){{
            put(CountryCodes.BG, TestConstants.IMAGE_BG_NAME_2);
            put(CountryCodes.FR, TestConstants.IMAGE_FR_NAME_2);
        }});
        bindingModel.setUrl(TestConstants.IMAGE_URL_2);
        MockHttpServletResponse response = this.mockMvc.perform(put("/admin/images/edit/" + this.savedImage.getArticle().getId())
                .locale(Locale.FRANCE)
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr("imageEdit", bindingModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fr/admin/articles/edit/" + this.savedImage.getArticle().getId()))
                .andDo(print())
                .andReturn().getResponse();
        Image modifiedImage = this.imageRepository.findAll().get(0);
        int actualSize = modifiedImage.getLocalImageNames().size();
        Assert.assertEquals(2, actualSize);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.BG), TestConstants.IMAGE_BG_NAME_2);
        Assert.assertEquals(modifiedImage.getLocalImageNames().get(CountryCodes.FR), TestConstants.IMAGE_FR_NAME_2);
        Assert.assertEquals(modifiedImage.getUrl(), TestConstants.IMAGE_URL_2);

    }


    // тест за хвърлена грешка от GlobalExceptionHandler
//    @Test
//    public void getInexistentImage_returnsNotFound() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(get("/fr/admin/images/edit/111")
//                .locale(Locale.FRANCE)
//                .contextPath("/fr")
//                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr")))
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
