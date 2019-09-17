package architecture.web.controllers;

import architecture.domain.ArticleBindingModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
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
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
