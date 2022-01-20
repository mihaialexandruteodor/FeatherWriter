package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CharacterProfileController {

    @Autowired
    private FWCharacterService fwCharacterService;

    @Autowired
    public CharacterProfileController(FWCharacterService fwCharacterService) {
        this.fwCharacterService = fwCharacterService;
    }


    @GetMapping("/characterProfile/{id}")
    public ModelAndView characterProfile() {
        ModelAndView mv = new ModelAndView("character_profile");
        return mv;
    }
}
