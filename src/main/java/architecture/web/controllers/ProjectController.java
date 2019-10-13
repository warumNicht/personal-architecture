package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.domain.models.viewModels.ArticleLocalViewModel;
import architecture.services.interfaces.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController extends BaseController{
    private  final ArticleService articleService;

    @Autowired
    public ProjectController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/category/{id}")
    public ModelAndView projectsByCategory(ModelAndView modelAndView, @PathVariable Long id){
        CountryCodes wantedCode = super.getCurrentCookieLocale();
        List<ArticleLocalViewModel> localisedArticles=this.articleService.findArticlesByCategory(id, wantedCode);
        modelAndView.addObject("articles",localisedArticles);
        modelAndView.setViewName("projects-category");
        return modelAndView;
    }
}
