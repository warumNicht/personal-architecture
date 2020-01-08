package architecture.integration.web;

import architecture.TestConstants;
import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Image;
import architecture.repositories.ImageRepository;
import architecture.services.interfaces.LocaleService;
import architecture.util.LocaleMessageUtil;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Locale;

import org.apache.wicket.util.string.Strings;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ImageControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void init() {
        Image image = new Image();
        image.setUrl(TestConstants.IMAGE_URL);
        image.setLocalImageNames(new HashMap<>() {{
            put(CountryCodes.FR, TestConstants.IMAGE_BG_NAME);
            put(CountryCodes.BG, TestConstants.IMAGE_ES_NAME);
            put(CountryCodes.ES, TestConstants.IMAGE_FR_NAME);
        }});
        this.imageRepository.save(image);
    }

    @Test
    public void getImage_returnsCorrectView() throws Exception {
        Image savedImage = this.imageRepository.findAll().get(0);
        MockHttpServletResponse response = this.mockMvc.perform(get("/fr/admin/images/edit/" + savedImage.getId())
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-image"))
                .andDo(print())
                .andReturn().getResponse();
        String contentAsString = response.getContentAsString();
        Assert.assertTrue(contentAsString.contains(TestConstants.IMAGE_URL));

        for (String value : savedImage.getLocalImageNames().values()) {
            String escapedValue = StringEscapeUtils.escapeHtml4(value);
            Assert.assertTrue(contentAsString.contains(escapedValue));
        }
    }

    @Test
    public void getInexistentImage_returnsNotFound() throws Exception {
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

        String archSentence = Strings.escapeMarkup(LocaleMessageUtil.getLocalizedMessage("archSentence", Locale.FRANCE)).toString();

        String expectedErrorMessage = escapeHTML(LocaleMessageUtil.getLocalizedMessage("archSentence", Locale.FRANCE));
        boolean contains = contentAsString.contains(expectedErrorMessage);
        Assert.assertTrue(contains);
        System.out.println();

    }

    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\'' || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
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
