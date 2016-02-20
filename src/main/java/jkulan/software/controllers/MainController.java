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
    public String index() {
        return "index";
    }
    
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }

    @RequestMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String users(){
    	return "userlist";
    }

    @RequestMapping("/helper")
    @PreAuthorize("hasRole('ROLE_HELPER')")
    @ResponseBody
    public String helper(){
    	return "you are helper";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String admin(){
    	return "you are admin";
    }
}
