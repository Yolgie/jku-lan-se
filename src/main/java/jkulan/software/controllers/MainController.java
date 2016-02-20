package jkulan.software.controllers;

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

}
