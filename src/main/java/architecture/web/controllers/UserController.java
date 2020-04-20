package architecture.web.controllers;

import architecture.constants.AppConstants;
import architecture.constants.ViewNames;
import architecture.domain.models.bindingModels.users.UserCreateBindingModel;
import architecture.domain.models.bindingModels.users.UserLoginBindingModel;
import architecture.domain.models.serviceModels.UserServiceModel;
import architecture.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping(value = "/register")
    public String registerUser(@ModelAttribute(name = ViewNames.USER_REGISTER_binding_model) UserCreateBindingModel model,
                               Principal principal) {
        if (principal == null) {
            return ViewNames.USER_REGISTER;
        }
        return "redirect:/" + super.getLocale() + "/";
    }

    @PostMapping(value = "/register")
    public String registerUserPost(@Valid @ModelAttribute(name = ViewNames.USER_REGISTER_binding_model) UserCreateBindingModel bindingModel,
                                   BindingResult bindingResult, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/" + super.getLocale() + "/";
        }
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
    public String loginUser(@ModelAttribute(name = "userLogin") UserLoginBindingModel model,
                            Principal principal) {
        if (principal == null) {
            return ViewNames.USER_LOGIN;
        }
        return "redirect:/" + super.getLocale() + "/";
    }

    @PostMapping(value = "/rest-authentication")
    @ResponseBody
    public Object loginRest(@RequestBody UserLoginBindingModel userBinding, ServletRequest request){
        System.out.println("loc");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            UserDetails loggingUser = userService.loadUserByUsername(userBinding.getUsername());
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loggingUser,
                            userBinding.getPassword(), loggingUser.getAuthorities());

            Authentication authenticate = this.authenticationManager.authenticate(token);

            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                super.logger.info(String.format("Login of user: %s, successfully!", userBinding.getUsername()));
                            CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            String token2 = csrf.getToken();
            return  token2;
            }
        } catch (AuthenticationException e) {
            return ViewNames.USER_LOGIN;
        }
        return null;
    }

    @PostMapping(value = "/authentication")
    public String loginUserPost(@ModelAttribute(name = "userLogin") UserLoginBindingModel userBinding,
                                Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            return "redirect:/" + super.getLocale() + "/";
        }

        try {
            UserDetails loggingUser = userService.loadUserByUsername(userBinding.getUsername());
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loggingUser,
                            userBinding.getPassword(), loggingUser.getAuthorities());

            Authentication authenticate = this.authenticationManager.authenticate(token);

            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                super.logger.info(String.format("Login of user: %s, successfully!", userBinding.getUsername()));
            }
        } catch (AuthenticationException e) {
            model.addAttribute("exception", e);
            return ViewNames.USER_LOGIN;
        }
        Object attribute = session.getAttribute(AppConstants.LOGIN_REFERRER_SESSION_ATTRIBUTE_NAME);
        if (attribute != null) {
            session.removeAttribute(AppConstants.LOGIN_REFERRER_SESSION_ATTRIBUTE_NAME);
            return "redirect:/" + super.getLocale() + attribute;
        }
        return "redirect:/" + super.getLocale() + "/";
    }

    @PostConstruct
    public void doLog() {
        super.logger.info("User controller started");
    }
}
