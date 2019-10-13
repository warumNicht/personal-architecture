package architecture.web.controllers;

import architecture.domain.models.viewModels.ArticleLocalViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {

    @GetMapping(value = "/category/{id}")
    public ModelAndView projectsByCategory(ModelAndView modelAndView, @PathVariable Long id){
        List<ArticleLocalViewModel> localisedArticles=new ArrayList<>();
        modelAndView.addObject("articles",localisedArticles);
        modelAndView.setViewName("projects-category");
        return modelAndView;
    }
}
