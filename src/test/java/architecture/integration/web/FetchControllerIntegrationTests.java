package architecture.integration.web;

import architecture.util.TestConstants;
import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import architecture.repositories.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.HashMap;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FetchControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Before
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void setUp(){
        this.populateCategories();
    }

    @Test
    public void fetchCategories_locale_FR_returnsCorrect_whenAllNamesPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fetch/categories/all")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", Matchers.is(TestConstants.CATEGORY_1_FR_NAME)))
                .andExpect(jsonPath("$[1].name", Matchers.is(TestConstants.CATEGORY_2_FR_NAME)));
    }

    @Test
    public void fetchCategories_locale_ES_returnsDefault_whenSomeNamesAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fetch/categories/all")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "es")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", Matchers.is(TestConstants.CATEGORY_1_BG_NAME)))
                .andExpect(jsonPath("$[1].name", Matchers.is(TestConstants.CATEGORY_2_ES_NAME)));
    }

    @Test
    public void fetchCategories_locale_DE_doesNotReturn_whenNameAndDefaultAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fetch/categories/all")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(ApplicationConstants.LOCALE_COOKIE_NAME, "de")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private void populateCategories(){
        Category categoryOne = new Category();
        categoryOne.setLocalCategoryNames(new HashMap<>(){{
            put(CountryCodes.FR, TestConstants.CATEGORY_1_FR_NAME);
            put(CountryCodes.BG, TestConstants.CATEGORY_1_BG_NAME);
        }});
        this.categoryRepository.save(categoryOne);

        Category categoryTwo = new Category();
        categoryTwo.setLocalCategoryNames(new HashMap<>(){{
            put(CountryCodes.FR, TestConstants.CATEGORY_2_FR_NAME);
            put(CountryCodes.BG, TestConstants.CATEGORY_2_BG_NAME);
            put(CountryCodes.ES, TestConstants.CATEGORY_2_ES_NAME);
        }});
        this.categoryRepository.save(categoryTwo);

        Category categoryThree = new Category();
        categoryThree.setLocalCategoryNames(new HashMap<>(){{
            put(CountryCodes.FR, TestConstants.CATEGORY_2_FR_NAME);
            put(CountryCodes.ES, TestConstants.CATEGORY_2_ES_NAME);
        }});
        this.categoryRepository.save(categoryThree);
    }
}
