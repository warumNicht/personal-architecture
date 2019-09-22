package architecture.web.controllers;

import architecture.domain.entities.Article;
import architecture.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin" )
public class AdminController {
    private ArticleRepo articleRepo;

    @Autowired
    public AdminController(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @GetMapping("/listAll")
    public ModelAndView listAll(ModelAndView modelAndView){
        List<Article> allArticles = this.articleRepo.findAll();
        modelAndView.addObject("allArticles",allArticles);
        modelAndView.setViewName("listAll");

        return modelAndView;
    }
}
