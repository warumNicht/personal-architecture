package architecture.unit.web;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.domain.models.viewModels.LocalisedCategoryViewModel;
import architecture.services.interfaces.CategoryService;
import architecture.services.interfaces.ImageService;
import architecture.services.interfaces.LocaleService;
import architecture.web.controllers.FetchController;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FetchController.class)
public class FetchControllerTests {
    private final String CATEGORY_1_NAME="Обществени сгради";
    private final String CATEGORY_2_NAME="Жилищни сгради";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ImageService imageService;
    @MockBean
    private LocaleService localeService;
    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void setUp(){
        Mockito.when(localeService.getCurrentCookieLocale()).thenReturn(CountryCodes.DE);
    }

    @Test
    public void whenNoCategories_returnsEmptyArray() throws Exception {
        Mockito.when(categoryService.getAllCategoriesByLocale(ApplicationConstants.DEFAULT_COUNTRY_CODE, CountryCodes.DE))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/fetch/categories/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenCategories_returnsCorrect() throws Exception {
        List<LocalisedCategoryViewModel> categories = Arrays.asList(new LocalisedCategoryViewModel(1L, CATEGORY_1_NAME),
                new LocalisedCategoryViewModel(2L, CATEGORY_2_NAME)
        );
        Mockito.when(categoryService.getAllCategoriesByLocale(ApplicationConstants.DEFAULT_COUNTRY_CODE, CountryCodes.DE))
                .thenReturn(categories);

        ResultActions perform = mockMvc.perform(get("/fetch/categories/all").contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(get("/fetch/categories/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect( jsonPath("$[0].name", Matchers.is(CATEGORY_1_NAME)))
                .andExpect( jsonPath("$[1].name", Matchers.is(CATEGORY_2_NAME)));
    }
}
