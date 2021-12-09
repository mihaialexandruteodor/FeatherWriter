package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.SceneService;
import org.springframework.beans.factory.annotation.Autowired;

public class SceneController {

    @Autowired
    private SceneService sceneService;

    @Autowired
    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}
