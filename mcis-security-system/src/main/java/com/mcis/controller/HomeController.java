package com.mcis.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Authentication authentication, Model model) {

	    if (authentication == null) {
	        return "redirect:/oauth2/authorization/keycloak";
	    }

	    System.out.println("Principal Type: " + authentication.getPrincipal().getClass());

	    model.addAttribute("username", authentication.getName());
	    model.addAttribute("authorities", authentication.getAuthorities());

	    return "dashboard";
	}

}