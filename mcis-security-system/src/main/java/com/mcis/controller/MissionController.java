package com.mcis.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.mcis.entity.Mission;
import com.mcis.enums.ClearanceLevel;
import com.mcis.service.AuditService;
import com.mcis.service.BellLaPadulaService;
import com.mcis.service.MissionService;
import com.mcis.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MissionController {

    private final MissionService missionService;
    private final UserService userService;

    private final BellLaPadulaService bellLaPadulaService;
	private final AuditService auditService;

    public MissionController(
            MissionService missionService,
            UserService userService,
            BellLaPadulaService bellLaPadulaService,
            AuditService auditService) {

        this.missionService = missionService;
        this.userService = userService;
        this.bellLaPadulaService = bellLaPadulaService;
        this.auditService = auditService;
    }

    @GetMapping("/missions")
    public String missions(Authentication authentication,
                           Model model) {

        if (authentication == null) {
            return "redirect:/oauth2/authorization/keycloak";
        }

        ClearanceLevel clearance =
                userService.getUserClearance(authentication.getAuthorities());

        model.addAttribute(
                "missions",
                missionService.getAllowedMissions(clearance));

        model.addAttribute("clearance", clearance);
        boolean admin =
                authentication.getAuthorities().stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_COMMANDER")
                             || a.getAuthority().equals("ROLE_SYSTEM_ADMIN"));

        model.addAttribute("admin", admin);

        return "missions";
    }
    
    @GetMapping("/missions/new")
    public String newMission(Authentication authentication,
                             Model model) {

        if (!isAdmin(authentication)) {
            return "access-denied";
        }

        model.addAttribute("mission", new Mission());

        return "mission-form";
    }
    
    @PostMapping("/missions/save")
    public String saveMission(
            @ModelAttribute Mission mission,
            Authentication authentication,
            HttpServletRequest request) {

        if (!isAdmin(authentication)) {

            auditService.log(
                    authentication.getName(),
                    userService.getUserClearance(authentication.getAuthorities()).name(),
                    "CREATE_MISSION",
                    mission.getMissionName(),
                    mission.getClassification().name(),
                    "DENIED",
                    request.getRemoteAddr()
            );

            return "access-denied";
        }

        missionService.saveMission(mission);

        auditService.log(
                authentication.getName(),
                userService.getUserClearance(authentication.getAuthorities()).name(),
                "CREATE_MISSION",
                mission.getMissionName(),
                mission.getClassification().name(),
                "GRANTED",
                request.getRemoteAddr()
        );

        return "redirect:/missions";
    }
    
    @GetMapping("/missions/delete/{id}")
    public String deleteMission(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest request) {

        if (!isAdmin(authentication)) {

            auditService.log(
                    authentication.getName(),
                    userService.getUserClearance(authentication.getAuthorities()).name(),
                    "DELETE_MISSION",
                    "Unknown",
                    "N/A",
                    "DENIED",
                    request.getRemoteAddr()
            );

            return "access-denied";
        }

        Mission mission = missionService.findById(id);

        auditService.log(
                authentication.getName(),
                userService.getUserClearance(authentication.getAuthorities()).name(),
                "DELETE_MISSION",
                mission.getMissionName(),
                mission.getClassification().name(),
                "GRANTED",
                request.getRemoteAddr()
        );

        missionService.deleteMission(id);

        return "redirect:/missions";
    }
    
    @GetMapping("/missions/{id}")
    public String missionDetails(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest request,
            Model model) {

        Mission mission = missionService.findById(id);

        if (mission == null) {
            return "redirect:/missions";
        }

        ClearanceLevel clearance =
                userService.getUserClearance(authentication.getAuthorities());

        boolean allowed =
                bellLaPadulaService.canRead(
                        clearance,
                        mission.getClassification());

        if (!allowed) {

        	auditService.log(
        	        authentication.getName(),
        	        clearance.name(),
        	        "VIEW_MISSION",
        	        mission.getMissionName(),
        	        mission.getClassification().name(),
        	        "DENIED",
        	        request.getRemoteAddr()
        	);

            return "access-denied";
        }

        model.addAttribute("mission", mission);
        model.addAttribute("clearance", clearance);

        auditService.log(
                authentication.getName(),
                clearance.name(),
                "VIEW_MISSION",
                mission.getMissionName(),
                mission.getClassification().name(),
                "GRANTED",
                request.getRemoteAddr()
        );

        return "mission-details";
    }
    private boolean isAdmin(Authentication authentication) {

        return authentication.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_COMMANDER")
                     || a.getAuthority().equals("ROLE_SYSTEM_ADMIN"));
    }

}