package jkulan.software.controllers;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jkulan.software.social.steam.api.Steam;

@Controller
public class SteamController {
	@Inject
	private ConnectionRepository connectionRepository;

	@RequestMapping(value="/steam", method=RequestMethod.GET)
	public String home(Model model) {
		Connection<Steam> connection = connectionRepository.findPrimaryConnection(Steam.class);
		if (connection == null) {
			return "redirect:/connect/steam";
		}
		//model.addAttribute("profile", connection.getApi().userOperations().getUserProfile());
		return "steam/profile";
	}
}
