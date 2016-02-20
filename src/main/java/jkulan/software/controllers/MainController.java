package jkulan.software.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tth on 1/18/16.
 */
@Controller
public class MainController {

    @RequestMapping("/")
//    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }

}
