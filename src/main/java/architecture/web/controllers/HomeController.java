package architecture.web.controllers;

import architecture.domain.ArticleBindingModel;
import architecture.domain.entities.Localised;
import architecture.repositories.LocalizedRepo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import architecture.domain.entities.Article;
import architecture.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class HomeController {

    private ArticleRepo articleRepo;
    private LocalizedRepo localizedRepo;
    @Autowired
    public HomeController(ArticleRepo articleRepo, LocalizedRepo localizedRepo) {
        this.articleRepo = articleRepo;
        this.localizedRepo = localizedRepo;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView) {
        Article article = this.articleRepo.findById(1L).orElse(null);
        Object fr = this.localizedRepo.getValue("FR", 3L);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Article article = this.articleRepo.findById(1L).orElse(null);
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }

    @GetMapping("/create")
    public ModelAndView createArticle(@ModelAttribute(name = "articleBinding") ArticleBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("articleBinding",model);
        modelAndView.setViewName("create");
        return modelAndView;
    }

    @PostMapping("/create")
    private ModelAndView createArticlePost(@Valid @ModelAttribute(name = "articleBinding") ArticleBindingModel model,
                                           BindingResult bindingResult, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("articleBinding",model);
            modelAndView.setViewName("create");
            return modelAndView;
        }
        Article article = new Article();
        article.setName("EN", "Business");
        article.setDescription("EN", "This is the business category");
        article.setName("FR", "La Business");
        article.setDescription("FR", "Ici es la Business");
        article = this.articleRepo.saveAndFlush(article);

        System.out.println(article.getDescription("EN"));
        System.out.println(article.getName("FR"));

        Article c2 = new Article();
        c2.setDescription("EN", "Second Description");
        c2.setName("EN", "Second Name");
        c2.setDescription("DE", "Zwei  Description");
        c2.setName("DE", "Zwei  Name");
        c2=this.articleRepo.saveAndFlush(c2);


        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
