package architecture.web.controllers;

import architecture.domain.ArticleBindingModel;
import architecture.domain.CountryCodes;
import architecture.domain.entities.LocalisedArticleContent;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import architecture.domain.entities.Article;
import architecture.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
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
        Article article = this.articleRepo.findById(1L).orElse(null);
        Object de = this.articleRepo.getValue(CountryCodes.DE, 1L);
        Object[] all = this.articleRepo.getAllNestedSelect(CountryCodes.ES, CountryCodes.BG);
        Object[] max = this.articleRepo.getAllMax(CountryCodes.ES, CountryCodes.BG);
        Object nativeQuery = this.articleRepo.getAllNativeQuery("ES", "BG");
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
        Article article=new Article();
        article.setDate(new Date());
        LocalisedArticleContent contentFr = new LocalisedArticleContent("Louvre", "Musee du Louvre");
        LocalisedArticleContent contentFr2 = new LocalisedArticleContent("Palais royal", "Champs Elysees");
        LocalisedArticleContent contentDe = new LocalisedArticleContent("Brandenburg", "Potsdamer Platz");
        LocalisedArticleContent contentEs = new LocalisedArticleContent("Calle Vicente", "plaza Mayor");
        LocalisedArticleContent contentEs2 = new LocalisedArticleContent("San Isidre", "Fiesta del verano");
        LocalisedArticleContent contentEn = new LocalisedArticleContent("The king", "Henri VIII");
        LocalisedArticleContent contentBg = new LocalisedArticleContent("УАСГ", "Университет по архитектура строителство и геодезия");
        LocalisedArticleContent contentBg2 = new LocalisedArticleContent("Проекти", "Фирма рога и копита");

        article.getLocalContent().put(CountryCodes.DE, contentDe);
        article.getLocalContent().put(CountryCodes.ES, contentEs);
        article.getLocalContent().put(CountryCodes.FR, contentFr);
        article.getLocalContent().put(CountryCodes.BG, contentBg);
        article=this.articleRepo.saveAndFlush(article);

        Article article2=new Article();
        article2.setDate(new Date());

        article2.getLocalContent().put(CountryCodes.BG, contentBg2);
        article2.getLocalContent().put(CountryCodes.EN, contentEn);
        article2.getLocalContent().put(CountryCodes.FR, contentFr2);
        article2.getLocalContent().put(CountryCodes.ES, contentEs2);
        article2=this.articleRepo.saveAndFlush(article2);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
