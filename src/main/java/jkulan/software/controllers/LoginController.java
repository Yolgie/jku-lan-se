package jkulan.software.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for displaying all pages relevant for Login, Authentication and Authorization
 */
@Controller
public class LoginController {

        @RequestMapping("/login")
        @PreAuthorize("permitAll()")
        public String login()
        {
            return "login";
        }

        @RequestMapping("/logout")
        @PreAuthorize("permitAll()")
        public String logout()
        {
            return "redirect:/login?logout";
        }
}

