package jkulan.software.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/saml")
public class SSOController {

	// Logger
	private static final Log log = LogFactory.getLog(SSOController.class);

	@Autowired
	private MetadataManager metadata;

	@RequestMapping(value = "/idpSelection", method = RequestMethod.GET)
	@PreAuthorize("permitAll()")
	public String idpSelection(HttpServletRequest request, Model model) {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			log.warn("The current user is already logged.");
			return "redirect:/";
		} else {
			if (isForwarded(request)) {
				Set<String> idps = metadata.getIDPEntityNames();
				for (String idp : idps)
					log.debug("Configured Identity Provider for SSO: " + idp);
				model.addAttribute("idps", idps);
				return "saml/idpSelection";
			} else {
				log.warn("Direct accesses to '/idpSelection' route are not allowed");
				return "redirect:/";
			}
		}
	}

	/*
	 * Checks if an HTTP request is forwarded from servlet.
	 */
	private boolean isForwarded(HttpServletRequest request){
		return !(request.getAttribute("javax.servlet.forward.request_uri") == null);
	}
}
