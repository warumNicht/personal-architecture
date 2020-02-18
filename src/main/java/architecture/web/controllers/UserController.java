package architecture.web.controllers;

import architecture.constants.ViewNames;
import architecture.domain.models.bindingModels.users.UserCreateBindingModel;
import architecture.domain.models.bindingModels.users.UserLoginBindingModel;
import architecture.domain.models.serviceModels.UserServiceModel;
import architecture.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/register")
    public String registerUser(@ModelAttribute(name = ViewNames.USER_REGISTER_binding_model) UserCreateBindingModel model) {
        return ViewNames.USER_REGISTER;
    }

    @PostMapping(value = "/register")
    public String registerUserPost(@Valid @ModelAttribute(name = ViewNames.USER_REGISTER_binding_model) UserCreateBindingModel bindingModel,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ViewNames.USER_REGISTER_binding_model, bindingModel);
            return ViewNames.USER_REGISTER;
        }
        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "user.password.notMatch");
            model.addAttribute(ViewNames.USER_REGISTER_binding_model, bindingModel);
            return ViewNames.USER_REGISTER;
        }
        UserServiceModel user = this.modelMapper.map(bindingModel, UserServiceModel.class);
        this.userService.registerUser(user);
        return "redirect:/" + super.getLocale() + "/";
    }

    @GetMapping(value = "/login")
    public String loginUser(@ModelAttribute(name = "userLogin") UserLoginBindingModel model) {
        return ViewNames.USER_LOGIN;
    }

//    @PostMapping(value = "/login")
//    public String loginUserPost(@ModelAttribute(name = "userLogin") UserLoginBindingModel model) {
//        return "redirect:/" +super.getLocale() + "/";
//    }

    @PostConstruct
    public void doLog() {
        super.logger.info("User controller started");
    }
}
