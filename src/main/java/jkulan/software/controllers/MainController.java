package jkulan.software.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.koraktor.steamcondenser.community.SteamId;
import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;

/**
 * Created by tth on 1/18/16.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
    	String id = "fuero";
    	SteamId fuero;
		try {
			fuero = SteamId.create(id, true);
	        return fuero.getNickname();
		} catch (SteamCondenserException e) {
			e.printStackTrace();
		}
		return "Steam does not know "+id;
    }

}
