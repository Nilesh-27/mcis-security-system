package com.mcis.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {

        OidcUser oidcUser = super.loadUser(userRequest);

        Collection<GrantedAuthority> authorities =
                new HashSet<>(oidcUser.getAuthorities());

        String username = oidcUser.getPreferredUsername().toLowerCase();

        switch (username) {

            case "commander":
                authorities.add(new SimpleGrantedAuthority("ROLE_COMMANDER"));
                break;

            case "general":
                authorities.add(new SimpleGrantedAuthority("ROLE_GENERAL"));
                break;

            case "intel":
                authorities.add(new SimpleGrantedAuthority("ROLE_INTELLIGENCE_OFFICER"));
                break;

            case "soldier":
                authorities.add(new SimpleGrantedAuthority("ROLE_SOLDIER"));
                break;

            case "sysadmin":
                authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM_ADMIN"));
                break;
        }

        return new DefaultOidcUser(
                authorities,
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "preferred_username");
    }
}