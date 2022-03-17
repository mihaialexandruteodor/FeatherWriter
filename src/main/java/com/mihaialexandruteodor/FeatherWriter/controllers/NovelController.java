package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class NovelController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private FWCharacterService characterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CorkboardService corkboardService;


    @Autowired
    public NovelController(NovelService novelService, CorkboardService _corkboardService, ChapterService _chapterService, LocationService _locationService) {
        this.novelService = novelService;
        this.corkboardService = _corkboardService;
        this.chapterService = _chapterService;
        this.locationService = _locationService;
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

    ModelAndView setUpProjDetails(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_details");
        mv.addObject("novel",novel);
        List<FWCharacter> assignedCharacterList = novel.getCharacters();
        List<Chapter> assignedChapterList = novel.getChapters();
        List<Location> assignedLocationList = novel.getLocations();
        mv.addObject("assignedCharacterList", assignedCharacterList);
        mv.addObject("assignedChapterList", assignedChapterList);
        mv.addObject("assignedLocationList", assignedLocationList);
        return mv;
    }

    ModelAndView setUpProjDecorationPage(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_decoration");
        mv.addObject("novel",novel);
        
        List<FWCharacter> characterList = characterService.getAllFWCharacters();
        List<FWCharacter> assignedCharacterList = novel.getCharacters();
        characterList.removeIf(assignedCharacterList::contains);
        mv.addObject("assignedCharacterList", assignedCharacterList);
        mv.addObject("characterList", characterList);

        List<Chapter> chapterList = chapterService.getAllChapters();
        List<Chapter> assignedChapterList = novel.getChapters();
        chapterList.removeIf(assignedChapterList::contains);
        mv.addObject("assignedChapterList", assignedChapterList);
        mv.addObject("chapterList", chapterList);

        List<Location> locationList = locationService.getAllLocations();
        List<Location> assignedLocationList = novel.getLocations();
        locationList.removeIf(assignedLocationList::contains);
        mv.addObject("assignedLocationList", assignedLocationList);
        mv.addObject("locationList", locationList);


        System.out.println(Arrays.toString(assignedCharacterList.toArray()));
        System.out.println(Arrays.toString(assignedChapterList.toArray()));
        System.out.println(Arrays.toString(assignedLocationList.toArray()));
        
        return mv;
    }


    @RequestMapping(value ="/addCharacterToProject/{characterID}/{novelID}")
    public ModelAndView addCharacterToProject(@Valid @PathVariable(value = "characterID") int characterID, @Valid @PathVariable(value = "novelID") int  novelID, Model model){
        FWCharacter charObj = characterService.getFWCharacterById(characterID);
        Novel novel = novelService.getNovelById(novelID);
        novelService.addCharacterToProject(novel,charObj);
        characterService.addProjectToCharacter(novel,charObj);
        return setUpProjDecorationPage(model,novel);
    }

    @RequestMapping(value ="/removeCharacterFromProject/{characterID}")
    public ModelAndView removeCharacterFromProject(@Valid @PathVariable(value ="characterID") int  characterID, @Valid  @PathVariable(value = "novelID") int  novelID, Model model){
        FWCharacter charObj = characterService.getFWCharacterById(characterID);
        Novel novel = novelService.getNovelById(novelID);
        novelService.removeCharacterFromProject(novel,charObj);
        characterService.removeProjectFromCharacter(charObj);
        return setUpProjDecorationPage(model,novel);
    }

    @RequestMapping(value ="/addChapterToProject/{chapterID}/{novelID}")
    public ModelAndView addChapterToProject(@Valid @PathVariable(value ="chapterID") int chapterID, @Valid  @PathVariable(value = "novelID") int  novelID, Model model){
        Chapter chapObj = chapterService.getChapterById(chapterID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.addChapterToProject(novelObj,chapObj);
        chapterService.addProjectToChapter(novelObj,chapObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping(value ="/removeChapterFromProject/{chapterID}/{novelID}")
    public ModelAndView removeChapterFromProject(@Valid @PathVariable(value ="chapterID") int  chapterID, @Valid  @PathVariable(value = "novelID") int  novelID, Model model){
        Chapter chapObj = chapterService.getChapterById(chapterID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.removeChapterFromProject(novelObj,chapObj);
        chapterService.removeProjectFromChapter(chapObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping(value ="/addLocationToProject/{locationID}/{novelID}")
    public ModelAndView addLocationToProject(@Valid @PathVariable(value ="locationID") int locationID, @Valid  @PathVariable(value = "novelID") int  novelID, Model model){
        Location locObj = locationService.getLocationById(locationID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.addLocationToProject(novelObj,locObj);
        locationService.addProjectToLocation(novelObj,locObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping(value ="/removeLocationFromProject/{locationID}/{novelID}")
    public ModelAndView removeLocationFromProject(@Valid @PathVariable(value ="locationID") int  locationID, @Valid  @PathVariable(value = "novelID") int  novelID, Model model){
        Location locObj = locationService.getLocationById(locationID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelService.removeLocationFromProject(novelObj,locObj);
        locationService.removeProjectFromLocation(locObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @PostMapping("/saveProject")
    public ModelAndView saveProject(@Valid @ModelAttribute("novel") Novel novel, Model model) {
        corkboardService.saveCorkboard(novel.getCorkboard());
        novelService.saveNovel(novel);
        return setUpProjDecorationPage(model,novel);
    }

    @PostMapping("/updateProject")
    public ModelAndView updateProject(@Valid @ModelAttribute("novel") Novel novel) {
        novelService.saveNovel(novel);
        return loadProjectsPageData();
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

    @GetMapping("/novelPage/{novelID}")
    public ModelAndView novelPage(@Valid @PathVariable("novelID") int novelID,Model model) {
        Novel novel = novelService.getNovelById(novelID);
        return setUpProjDetails(model, novel);
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
