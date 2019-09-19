package architecture.web.controllers;

import architecture.domain.ArticleBindingModel;
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
    @Autowired
    public HomeController(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView) {
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
//        Article article=new Article();
//        article.setDate(new Date());
//        article.getText().put("BG", model.getTitle());
//        article.getText().put("FR", "francais");
//
//        article.getContent().put("EN", model.getContent());
//        article.getContent().put("DE", "deutsch");
//        article.getContent().put("ES", "espanol");
//        this.articleRepo.saveAndFlush(article);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
