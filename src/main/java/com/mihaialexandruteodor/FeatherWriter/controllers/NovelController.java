package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovelController {

    @Autowired
    private NovelService novelService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }


}
