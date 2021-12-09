package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.CorkboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorkboardController {

    @Autowired
    private CorkboardService corkboardService;

    @Autowired
    public CorkboardController(CorkboardService corkboardService) {
        this.corkboardService = corkboardService;
    }
}
