package com.mcis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mcis.service.AuditService;

@Controller
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/audit")
    public String audit(Model model) {

        model.addAttribute("logs",
                auditService.getAllLogs());

        return "audit";

    }

}