package com.mcis.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mcis.enums.ClearanceLevel;
import com.mcis.service.AuditService;
import com.mcis.service.MissionService;
import com.mcis.service.UserService;

@Controller
public class HomeController {

    private final MissionService missionService;
    private final AuditService auditService;
    private final UserService userService;

    public HomeController(
            MissionService missionService,
            AuditService auditService,
            UserService userService) {

        this.missionService = missionService;
        this.auditService = auditService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {

        if (authentication == null) {
            return "redirect:/oauth2/authorization/keycloak";
        }

        System.out.println("========== AUTHORITIES ==========");

        authentication.getAuthorities().forEach(System.out::println);

        System.out.println("=================================");

        model.addAttribute("username", authentication.getName());

        return "dashboard";
    }
}