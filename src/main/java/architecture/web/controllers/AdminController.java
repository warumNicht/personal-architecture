package architecture.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin" )
public class AdminController {
    @GetMapping("/listAll")
    public ModelAndView listAll(ModelAndView modelAndView){
        modelAndView.setViewName("listAll");

        return modelAndView;
    }
}
