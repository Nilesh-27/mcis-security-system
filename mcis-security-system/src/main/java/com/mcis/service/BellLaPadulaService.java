package com.mcis.service;

import org.springframework.stereotype.Service;

import com.mcis.enums.ClearanceLevel;

@Service
public class BellLaPadulaService {

    /*
     * No Read Up
     */

    public boolean canRead(ClearanceLevel user,
                           ClearanceLevel document) {

        return user.ordinal() <= document.ordinal();

    }

}