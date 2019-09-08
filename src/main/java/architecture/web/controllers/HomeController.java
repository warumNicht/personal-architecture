package architecture.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String index(){
        return "<h1 style=\"color: red;\">Under construction</h1>";
    }

    @GetMapping("/home")
    public ModelAndView getIndex(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
