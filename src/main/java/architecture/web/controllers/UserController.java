package architecture.web.controllers;

import architecture.constants.ViewNames;
import architecture.domain.models.bindingModels.users.UserCreateBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping(value = "/register")
    public String registerUser(@ModelAttribute(name = "userRegister") UserCreateBindingModel model){
        return ViewNames.USER_CREATE;
    }

    @PostMapping(value = "/register")
    public String registerUserPost(@ModelAttribute(name = "userRegister") UserCreateBindingModel bindingModel){
        System.out.println();
        return "redirect:/";
    }
}
