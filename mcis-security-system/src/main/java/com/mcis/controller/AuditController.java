package com.mcis.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mcis.enums.ClearanceLevel;
import com.mcis.service.AuditService;
import com.mcis.service.UserService;

@Controller
public class AuditController {

    private final AuditService auditService;
    private final UserService userService;

    public AuditController(AuditService auditService,
                           UserService userService) {

        this.auditService = auditService;
        this.userService = userService;
    }

    @GetMapping("/audit")
    public String audit(Authentication authentication,
                        Model model) {

        ClearanceLevel clearance =
                userService.getUserClearance(authentication.getAuthorities());

        model.addAttribute(
                "logs",
                auditService.getAllowedLogs(clearance));

        return "audit";
    }

}