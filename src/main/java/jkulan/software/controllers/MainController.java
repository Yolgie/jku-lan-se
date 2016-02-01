package jkulan.software.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tth on 1/18/16.
 */
@Controller
public class MainController implements ErrorController {
	private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    @RequestMapping("/")
    public String index() {
    	return "index";
    }
}
