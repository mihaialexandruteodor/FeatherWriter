package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FWCharacterController {

    @Autowired
    private FWCharacterService fwCharacterService;

    @Autowired
    public FWCharacterController(FWCharacterService fwCharacterService) {
        this.fwCharacterService = fwCharacterService;
    }
}
