package com.mihaialexandruteodor.FeatherWriter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LiveEditorController {

    @RequestMapping("/liveEditor")
    public String viewLiveEditor( ) {

        return "live_editor_page";
    }
}
