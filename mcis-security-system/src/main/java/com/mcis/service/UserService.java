package com.mcis.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.mcis.enums.ClearanceLevel;

@Service
public class UserService {

    public ClearanceLevel getUserClearance(
            Collection<? extends GrantedAuthority> authorities) {

        for (GrantedAuthority authority : authorities) {

            switch (authority.getAuthority()) {

                case "ROLE_COMMANDER":
                case "ROLE_SYSTEM_ADMIN":
                    return ClearanceLevel.TOP_SECRET;

                case "ROLE_GENERAL":
                case "ROLE_INTELLIGENCE_OFFICER":
                    return ClearanceLevel.SECRET;

                case "ROLE_SOLDIER":
                    return ClearanceLevel.RESTRICTED;
            }
        }

        return ClearanceLevel.RESTRICTED;
    }
}