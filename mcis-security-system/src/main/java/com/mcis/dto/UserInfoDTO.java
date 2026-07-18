package com.mcis.dto;

import java.util.List;

import com.mcis.enums.ClearanceLevel;

public class UserInfoDTO {

    private String username;

    private List<String> roles;

    private ClearanceLevel clearance;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public ClearanceLevel getClearance() {
        return clearance;
    }

    public void setClearance(ClearanceLevel clearance) {
        this.clearance = clearance;
    }

}