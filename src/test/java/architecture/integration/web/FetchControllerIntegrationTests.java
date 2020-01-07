package architecture.integration.web;

import architecture.TestConstants;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import architecture.repositories.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
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

import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FetchControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void cat() throws Exception {
        Category category = new Category();
        Map<CountryCodes, String> localCategoryNames = new HashMap<>();
        localCategoryNames.put(CountryCodes.FR, TestConstants.CATEGORY_1_FR_NAME);
        localCategoryNames.put(CountryCodes.BG, TestConstants.CATEGORY_1_BG_NAME);
        category.setLocalCategoryNames(localCategoryNames);

        categoryRepository.save(category);
        long count = categoryRepository.count();

        Assert.assertEquals(count, 1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/fetch/categories/all").contentType(MediaType.APPLICATION_JSON).cookie(new Cookie("lang", "fr")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is(TestConstants.CATEGORY_1_FR_NAME)));
    }
}
