package architecture.web.controllers;

import architecture.domain.entities.Article;
import architecture.domain.entities.TraducedText;
import architecture.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
        Article article=new Article();
        article.setDate(new Date());
        TraducedText bg=new TraducedText();
        bg.setLocale("bg");
        bg.setText("baj stoen");
        article.getContent().put("BG", bg);

        TraducedText fr=new TraducedText();
        fr.setLocale("fr");
        fr.setText("Francois");
        article.getContent().put("FR", fr);

        TraducedText de=new TraducedText();
        de.setLocale("de");
        de.setText("Mutti");

        article.getTitle().put("de", de);

        this.articleRepo.saveAndFlush(article);

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }
}
