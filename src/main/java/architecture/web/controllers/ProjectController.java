package architecture.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {

    @GetMapping(value = "/category/{id}")
    public ModelAndView projectsByCategory(ModelAndView modelAndView, @PathVariable Long id){
        return modelAndView;
    }
}
