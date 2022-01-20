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

    @GetMapping("/updateCharacter/{id}")
    public String updateCharacter(@Valid @PathVariable ( value = "id") int id, Model model) {

        FWCharacter fwcharacter = fwCharacterService.getFWCharacterById(id);
        model.addAttribute("fwcharacter",fwcharacter);
        return "edit_character";
    }


    @PostMapping("/saveCharacter")
    public String saveCharacter(@Valid @ModelAttribute("fwcharacter") FWCharacter fwcharacter) {
        fwCharacterService.saveFWCharacter(fwcharacter);
        return "redirect:/charactersPage";
    }

    @GetMapping("/deleteCharacterProfile/{id}")
    public String deleteCharacterProfile(@Valid @PathVariable (value = "id") int id) {
        fwCharacterService.deleteFWCharacterById(id);
        return "redirect:/charactersPage";
    }

}
