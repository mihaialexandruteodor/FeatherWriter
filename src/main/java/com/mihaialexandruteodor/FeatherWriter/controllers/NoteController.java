package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

}
