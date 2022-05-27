package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import com.mihaialexandruteodor.FeatherWriter.utlis.DataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
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
        fwcharacter.setUsername(DataSingleton.getInstance().getCurrentUser());
        fwCharacterService.saveFWCharacter(fwcharacter);
        return "redirect:/charactersPage";
    }

    @GetMapping("/deleteCharacterProfile/{id}")
    public String deleteCharacterProfile(@Valid @PathVariable (value = "id") int id) {
        fwCharacterService.deleteFWCharacterById(id);
        return "redirect:/charactersPage";
    }

    @GetMapping("/characterProfile/{id}")
    public ModelAndView characterProfile(@Valid @PathVariable(value = "id") int id) {
        FWCharacter fwcharacter = fwCharacterService.getFWCharacterById(id);
        ModelAndView mv = new ModelAndView("character_profile");
        mv.addObject("fwcharacter", fwcharacter);
        return mv;
    }

    @GetMapping(path = {"/characterSearch"})
    public ModelAndView characterSearch(@Valid @RequestParam(value = "keyword") String keyword) {
        ModelAndView mv = new ModelAndView("characters_search_result");
        List<FWCharacter> fwCharacterListResult = new ArrayList<>();
        if(keyword!=null) {
            fwCharacterListResult = fwCharacterService.searchFWCharacters(DataSingleton.getInstance().getCurrentUser(), keyword);
        }
        mv.addObject("fwCharacterListResult", fwCharacterListResult);
        return mv;
    }

    @GetMapping("/charactersPage")
    public ModelAndView charactersPage() {
        return loadCharactersPageData();
    }

    public ModelAndView loadCharactersPageData() {
        return findPaginated(1, "name", "asc");
    }

    @GetMapping("/charactersPage/page/{pageNo}")
    public ModelAndView findPaginated(@Valid @PathVariable(value = "pageNo") int pageNo,
                                      @Valid @RequestParam("sortField") String sortField,
                                      @Valid @RequestParam("sortDir") String sortDir) {

        ModelAndView model = new ModelAndView("characters_page");
        int pageSize = 12;

        Page<FWCharacter> page = fwCharacterService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<FWCharacter> fwCharacterList = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());

        model.addObject("sortField", sortField);
        model.addObject("sortDir", sortDir);
        model.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addObject("fwCharacterList", fwCharacterList);

        return model;
    }

}
