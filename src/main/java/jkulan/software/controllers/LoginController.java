package jkulan.software.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for displaying all pages relevant for Login, Authentication and Authorization
 */
@Controller
public class LoginController {

        @RequestMapping("/login")
        public String login()
        {
            return "login";
        }

}

