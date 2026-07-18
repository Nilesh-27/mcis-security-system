package com.mcis.service;

import org.springframework.stereotype.Service;

import com.mcis.enums.ClearanceLevel;

@Service
public class UserService {

    public ClearanceLevel getUserClearance(String username) {

        switch (username.toLowerCase()) {

            case "commander":
                return ClearanceLevel.TOP_SECRET;

            case "intel":
                return ClearanceLevel.SECRET;

            case "general":
                return ClearanceLevel.SECRET;

            case "officer":
                return ClearanceLevel.CONFIDENTIAL;

            case "soldier":
                return ClearanceLevel.RESTRICTED;

            default:
                return ClearanceLevel.RESTRICTED;
        }
    }
}