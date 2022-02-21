package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.*;
import com.mihaialexandruteodor.FeatherWriter.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    private NoteService noteService;

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

    ModelAndView setUpProjDetails(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_details");
        mv.addObject("novel",novel);
        Set<FWCharacter> assignedCharacterSet = novel.getCharacters();
        Set<Chapter> assignedChapterSet = novel.getChapters();
        Set<Location> assignedLocationSet = novel.getLocations();
        Set<Note> assignedNoteSet = novel.getNotes();
        List<FWCharacter> assignedCharacterList = new ArrayList<>();
        assignedCharacterList.addAll(assignedCharacterSet);
        List<Chapter> assignedChapterList = new ArrayList<>();
        assignedChapterList.addAll(assignedChapterSet);
        List<Location> assignedLocationList = new ArrayList<>();
        assignedLocationList.addAll(assignedLocationSet);
        List<Note> assignedNoteList = new ArrayList<>();
        assignedNoteList.addAll(assignedNoteSet);
        mv.addObject("assignedCharacterList", assignedCharacterList);
        mv.addObject("assignedChapterList", assignedChapterList);
        mv.addObject("assignedLocationList", assignedLocationList);
        mv.addObject("assignedNoteList", assignedNoteList);
        return mv;
    }

    ModelAndView setUpProjDecorationPage(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_decoration");
        mv.addObject("novel",novel);
        
        List<FWCharacter> characterList = characterService.getAllFWCharacters();
        Set<FWCharacter> assignedCharacterSet = novel.getCharacters();
        List<FWCharacter> assignedCharacterList = new ArrayList<>();
        assignedCharacterList.addAll(assignedCharacterSet);
        characterList.removeIf(assignedCharacterList::contains);
        mv.addObject("assignedCharacterList", assignedCharacterList);
        mv.addObject("characterList", characterList);

        List<Chapter> chapterList = chapterService.getAllChapters();
        Set<Chapter> assignedChapterSet = novel.getChapters();
        List<Chapter> assignedChapterList = new ArrayList<>();
        assignedChapterList.addAll(assignedChapterSet);
        chapterList.removeIf(assignedChapterList::contains);
        mv.addObject("assignedChapterList", assignedChapterList);
        mv.addObject("chapterList", chapterList);

        List<Location> locationList = locationService.getAllLocations();
        Set<Location> assignedLocationSet = novel.getLocations();
        List<Location> assignedLocationList = new ArrayList<>();
        assignedLocationList.addAll(assignedLocationSet);
        locationList.removeIf(assignedLocationList::contains);
        mv.addObject("assignedLocationList", assignedLocationList);
        mv.addObject("locationList", locationList);

        List<Note> noteList = noteService.getAllNotes();
        Set<Note> assignedNoteSet = novel.getNotes();
        List<Note> assignedNoteList = new ArrayList<>();
        assignedNoteList.addAll(assignedNoteSet);
        noteList.removeIf(assignedNoteList::contains);
        mv.addObject("assignedNoteList", assignedNoteList);
        mv.addObject("noteList", noteList);

        System.out.println(Arrays.toString(assignedCharacterList.toArray()));
        System.out.println(Arrays.toString(assignedChapterList.toArray()));
        System.out.println(Arrays.toString(assignedLocationList.toArray()));
        System.out.println(Arrays.toString(assignedNoteList.toArray()));
        
        return mv;
    }


    @RequestMapping("/addCharacterToProject/{characterID}/{novelID}")
    public ModelAndView addCharacterToProject(@Valid @PathVariable("characterID") String characterID, @Valid @PathVariable("novelID") String  novelID, Model model){
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

    @RequestMapping("/addChapterToProject/{chapterID}/{novelID}")
    public ModelAndView addChapterToProject(@Valid @PathVariable("chapterID") String chapterID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Chapter chapObj = chapterService.getChapterById(Integer.parseInt(chapterID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.addChapterToProject(novelObj,chapObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/removeChapterFromProject/{chapterID}/{novelID}")
    public ModelAndView removeChapterFromProject(@Valid @PathVariable("chapterID") String  chapterID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Chapter chapObj = chapterService.getChapterById(Integer.parseInt(chapterID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.removeChapterFromProject(novelObj,chapObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/addLocationToProject/{locationID}/{novelID}")
    public ModelAndView addLocationToProject(@Valid @PathVariable("locationID") String locationID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Location locObj = locationService.getLocationById(Integer.parseInt(locationID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.addLocationToProject(novelObj,locObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/removeLocationFromProject/{locationID}/{novelID}")
    public ModelAndView removeLocationFromProject(@Valid @PathVariable("locationID") String  locationID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Location locObj = locationService.getLocationById(Integer.parseInt(locationID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.removeLocationFromProject(novelObj,locObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/addNoteToProject/{noteID}/{novelID}")
    public ModelAndView addNoteToProject(@Valid @PathVariable("noteID") String noteID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Note noteObj = noteService.getNoteById(Integer.parseInt(noteID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.addNoteToProject(novelObj,noteObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @RequestMapping("/removeNoteFromProject/{noteID}/{novelID}")
    public ModelAndView removeNoteFromProject(@Valid @PathVariable("noteID") String  noteID, @Valid @PathVariable("novelID") String  novelID, Model model){
        Note noteObj = noteService.getNoteById(Integer.parseInt(noteID));
        Novel novelObj = novelService.getNovelById(Integer.parseInt(novelID));
        novelService.removeNoteFromProject(novelObj,noteObj);
        novelService.saveNovel(novelObj);
        return setUpProjDecorationPage(model,novelObj);
    }

    @PostMapping("/saveProject")
    public ModelAndView saveProject(@Valid @ModelAttribute("novel") Novel novel, Model model) {
        novelService.saveNovel(novel);
        return setUpProjDecorationPage(model,novel);
    }

    @PostMapping("/updateProject")
    public ModelAndView updateProject(@Valid @ModelAttribute("novel") Novel novel, Model model) {
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
