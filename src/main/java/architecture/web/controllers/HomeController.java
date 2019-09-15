package architecture.web.controllers;

import architecture.domain.entities.Category;
import architecture.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HomeController {

    private  CategoryRepo categoryRepo;
    @Autowired
    public HomeController(CategoryRepo categoryRepo) {
        this.categoryRepo=categoryRepo;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView) {

        Category category = new Category();
        category.setName("EN", "Business");
        category.setDescription("EN", "This is the business category");
        category.setName("FR", "La Business");
        category.setDescription("FR", "Ici es la Business");
        category = categoryRepo.saveAndFlush(category);
        
        System.out.println(category.getDescription("EN"));
        System.out.println(category.getName("FR"));

        Category c2 = new Category();
        c2.setDescription("EN", "Second Description");
        c2.setName("EN", "Second Name");
        c2.setDescription("DE", "Zwei  Description");
        c2.setName("DE", "Zwei  Name");
        c2=categoryRepo.saveAndFlush(c2);

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }
}
