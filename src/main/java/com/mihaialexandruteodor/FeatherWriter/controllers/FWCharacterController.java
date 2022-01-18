package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FWCharacterController {

    @Autowired
    private FWCharacterService fwCharacterService;

    @Autowired
    public FWCharacterController(FWCharacterService fwCharacterService) {
        this.fwCharacterService = fwCharacterService;
    }


    @GetMapping("/newCharacter")
    public String viewCharacterPage(Model model) {

        FWCharacter fwCharacter = new FWCharacter();
        model.addAttribute("fwcharacter",fwCharacter);
        return "character_creation";
    }


    @PostMapping("/saveCharacter")
    public String saveCharacter(@Valid @ModelAttribute("fwcharacter") FWCharacter fwcharacter) {
        fwCharacterService.saveFWCharacter(fwcharacter);
        return "redirect:/charactersPage";
    }

}
