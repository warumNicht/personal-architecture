package architecture.integration.web;

import architecture.TestConstants;
import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Image;
import architecture.repositories.ImageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Locale;

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

    @Test
    public void getImage_returnsCorrectView() throws Exception {
        Image image = new Image();
        image.setUrl(TestConstants.IMAGE_URL);
        image.setLocalImageNames(new HashMap<>(){{
            put(CountryCodes.FR, TestConstants.IMAGE_BG_NAME);
            put(CountryCodes.BG, TestConstants.IMAGE_ES_NAME);
            put(CountryCodes.ES, TestConstants.IMAGE_FR_NAME);
        }});
        Image savedImage = this.imageRepository.save(image);
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
        boolean contains1 = contentAsString.contains(TestConstants.IMAGE_BG_NAME);
        boolean contains = contentAsString.contains(TestConstants.IMAGE_ES_NAME);
        boolean contains2 = contentAsString.contains(TestConstants.IMAGE_FR_NAME);
    }
}
