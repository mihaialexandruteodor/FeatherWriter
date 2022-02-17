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
        if(assignedCharactersList != null) {
            System.out.println("Characters assigned to Novel: " + assignedCharactersList.toString());
            characterList.removeIf(assignedCharactersList::contains);
            mv.addObject("assignedCharactersList", assignedCharactersList);
        }
        mv.addObject("characterList", characterList);
        return mv;
    }


    @RequestMapping("/addCharacterToProject/{characterID}/{novelID}")
    public ModelAndView addCharacterToProject(@Valid @PathVariable("characterID") String characterID, @Valid @PathVariable("novelID") String  novelID, Model model){
        System.out.println("ch id: " + characterID + " , nov id: " + novelID);
        FWCharacter charObj = characterService.getFWCharacterById(Integer.parseInt(characterID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.addCharacterToProject(novelObj,charObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/removeCharacterFromProject/{characterID}/{novelID}")
    public ModelAndView removeCharacterFromProject(@Valid @PathVariable("characterID") String  characterID, @Valid @PathVariable("novelID") String  novelID, Model model){
        FWCharacter charObj = characterService.getFWCharacterById(Integer.parseInt(characterID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.removeCharacterFromProject(novelObj,charObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @PostMapping("/saveProject")
    public ModelAndView saveProject(@Valid @ModelAttribute("novel") Novel novel, Model model) {
        novelService.saveNovel(novel);
        return setUpProjDecorationPage(model,novel);
    }


    @GetMapping("/showFormForUpdateProj/{novelID}")
    public ModelAndView showFormForUpdate(@Valid @PathVariable("novelID") int novelID, Model model)
    {
        Novel novelObj = novelService.getNovelById(novelID);
        return setUpProjPage(model,novelObj);
    }

    @GetMapping("/showFormForDecorationProj/{novelID}")
    public ModelAndView showFormForDecorationProj(@Valid @PathVariable("novelID") int novelID, Model model)
    {
        Novel novel = novelService.getNovelById(novelID);
        return setUpProjDecorationPage(model,novel);
    }

    @GetMapping("/projectsPage/page/{pageNo}")
    public ModelAndView findPaginated(@Valid @PathVariable("pageNo") int pageNo,
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
