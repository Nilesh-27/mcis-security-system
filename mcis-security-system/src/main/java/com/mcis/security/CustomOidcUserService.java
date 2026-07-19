package com.mcis.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> claims = oidcUser.getClaims();

        if (claims.containsKey("realm_access")) {

            Map<String, Object> realmAccess =
                    (Map<String, Object>) claims.get("realm_access");
            System.out.println("========== ID TOKEN CLAIMS ==========");
            oidcUser.getIdToken().getClaims()
                    .forEach((k,v) -> System.out.println(k + " = " + v));
            System.out.println("=====================================");
            System.out.println("========== ID TOKEN CLAIMS ==========");
            oidcUser.getIdToken().getClaims()
                    .forEach((k,v) -> System.out.println(k + " = " + v));
            System.out.println("=====================================");

            List<String> roles =
                    (List<String>) realmAccess.get("roles");

            if (roles != null) {

                for (String role : roles) {

                    authorities.add(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + role));

                }

            }

        }

        return new DefaultOidcUser(
                authorities,
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "preferred_username");

    }

}