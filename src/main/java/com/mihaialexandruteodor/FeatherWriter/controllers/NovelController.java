package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.services.FWCharacterService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NovelController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private FWCharacterService characterService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @GetMapping("/projectsPage")
    public ModelAndView projectsPage() {
        return loadProjectsPageData();
    }

    public ModelAndView loadProjectsPageData() {
        return findPaginated(1, "title", "asc");
    }

    @GetMapping("/newProject")
    public ModelAndView newNovel(Model model) {
        Novel novel = new Novel();
        return setUpProjPage(model, novel);
    }

    @GetMapping("/decorateProject/{novelID}")
    public ModelAndView decorateProject(Model model, @Valid @PathVariable("novelID") int novelID)
    {
        Novel novelObj = novelService.getNovelById(novelID);
        return setUpProjDecorationPage(model,novelObj);
    }

    ModelAndView setUpProjPage(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_editor_page");
        mv.addObject("novel",novel);
        return mv;
    }

    ModelAndView setUpProjDecorationPage(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_decoration");
        mv.addObject("novel",novel);
        List<FWCharacter> characterList = characterService.getAllFWCharacters();
        List<FWCharacter> assignedCharactersList = novel.getCharacters();
        characterList.removeIf(assignedCharactersList::contains);
        mv.addObject("characterList", characterList);
        mv.addObject("assignedCharactersList", assignedCharactersList);
        return mv;
    }


    @RequestMapping(value = "/addCharacterToProject/{characterID}/{novelID}")
    public String addTeamToProject(@Valid @PathVariable (value = "characterID") int characterID, @Valid @PathVariable("novelID") int novelID, Model model){
        FWCharacter charObj = characterService.getFWCharacterById(characterID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.addTeamToProject(novelObj,charObj);
        return "redirect:/showFormForUpdateProj/"+novelID;
    }

    @RequestMapping(value = "/removeCharacterFromProject/{characterID}/{novelID}")
    public String removeCharacterFromProject(@Valid @PathVariable (value = "characterID") int characterID, @Valid @PathVariable("novelID") int novelID, Model model){
        FWCharacter charObj = characterService.getFWCharacterById(characterID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.removeTeamToProject(novelObj,charObj);
        return "redirect:/showFormForUpdateProj/"+novelID;
    }

    @PostMapping("/saveProj")
    public String saveProj(@Valid @ModelAttribute("project") Novel novel) {
        novelService.saveNovel(novel);
        return "redirect:/showFormForUpdateProj/"+novel.getNovelID();
    }

    @GetMapping("/showFormForUpdateProj/{id}")
    public ModelAndView showFormForUpdate(@Valid @PathVariable ( value = "novelID") int novelID, Model model)
    {
        Novel novelObj = novelService.getNovelById(novelID);
        return setUpProjPage(model, novelObj);
    }

    @GetMapping("/projectsPage/page/{pageNo}")
    public ModelAndView findPaginated(@Valid @PathVariable(value = "pageNo") int pageNo,
                                      @Valid @RequestParam("sortField") String sortField,
                                      @Valid @RequestParam("sortDir") String sortDir) {

        ModelAndView model = new ModelAndView("projects_page");
        int pageSize = 12;

        Page<Novel> page = novelService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Novel> novelList = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());

        model.addObject("sortField", sortField);
        model.addObject("sortDir", sortDir);
        model.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addObject("novelList", novelList);

        return model;
    }
}
