package architecture.web.controllers;

import architecture.constants.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {
    @GetMapping("/unauthorized")
    public String unauthorized(){
        return ViewNames.UNAUTHORIZED;
    }
}
