package architecture.integration.web;

import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import architecture.domain.models.bindingModels.CategoryCreateBindingModel;
import architecture.util.TestConstants;
import org.junit.Assert;
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
import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser(roles = {"ADMIN"})
public class AdminControllerIntegrationTests extends CategorySeed {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCreateCategory_Admin_returnsCorrectView() throws Exception {
        this.mockMvc.perform(get("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CATEGORY_CREATE))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void getCreateCategory_Anonymous_redirectsLogin() throws Exception {
        this.mockMvc.perform(get("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void getCreateCategory_User_redirectsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    @Test
    public void postCreateCategory_validDataAndAdmin_returnsCorrectView_andSavesData() throws Exception {
        this.mockMvc.perform(post("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.CATEGORY_CREATE_binding_model_name, this.createValidCategoryCreateModel())
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/fr/admin/category/list"))
                .andDo(print());

        Category category = super.categoryRepository.findAll().get(0);
        Assert.assertEquals(category.getLocalCategoryNames().get(CountryCodes.FR), TestConstants.CATEGORY_1_FR_NAME);
    }

    @Test
    @WithAnonymousUser
    public void postCreateCategory_validDataAndAnonymous_redirectsLogin() throws Exception {
        this.mockMvc.perform(post("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.CATEGORY_CREATE_binding_model_name, this.createValidCategoryCreateModel())
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/fr/users/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void postCreateCategory_validDataAndUser_redirectsUnauthorized() throws Exception {
        this.mockMvc.perform(post("/fr/admin/category/create")
                .locale(Locale.FRANCE)
                .contextPath("/fr")
                .cookie(new Cookie(AppConstants.LOCALE_COOKIE_NAME, "fr"))
                .flashAttr(ViewNames.CATEGORY_CREATE_binding_model_name, this.createValidCategoryCreateModel())
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("/**/unauthorized"))
                .andDo(print());
    }

    private CategoryCreateBindingModel createValidCategoryCreateModel() {
        CategoryCreateBindingModel model = new CategoryCreateBindingModel();
        model.setCountry(CountryCodes.FR);
        model.setName(TestConstants.CATEGORY_1_FR_NAME);
        return model;
    }
}
