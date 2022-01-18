package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CharactersPageController {

    @Autowired
    private FWCharacterService fwCharacterService;

    @Autowired
    public CharactersPageController(FWCharacterService fwCharacterService) {
        this.fwCharacterService = fwCharacterService;
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
        int pageSize = 6;

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
