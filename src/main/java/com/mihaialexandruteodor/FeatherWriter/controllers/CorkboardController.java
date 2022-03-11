package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.CorkboardRepository;
import com.mihaialexandruteodor.FeatherWriter.services.CorkboardService;
import com.mihaialexandruteodor.FeatherWriter.services.NoteService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class CorkboardController {

    @Autowired
    private CorkboardService corkboardService;

    @Autowired
    private NovelService novelService;

    @Autowired
    private NoteService noteService;

    @Autowired
    public CorkboardController(CorkboardService _corkboardService, NovelService _novelService, NoteService _noteService) {
        this.corkboardService = _corkboardService;
        this.novelService= _novelService;
        this.noteService = _noteService;
    }

    @GetMapping("/corkboardPage/{novelID}")
    public ModelAndView projectsPage(@Valid  @PathVariable(value = "novelID") int  novelID) {

        Novel novel = novelService.getNovelById(novelID);
        return setUpCorkboardPage(novel);
    }

    public ModelAndView setUpCorkboardPage( Novel novel)
    {
        ModelAndView mv = new ModelAndView("corkboard_page");
        Corkboard corkboard = novel.getCorkboard();
        mv.addObject("novel",novel);
        mv.addObject("corkboard",corkboard);
        return mv;
    }

    @RequestMapping(value ="/addNoteToCorkboard/{novelID}/{noteID}/{corkboardID}")
    public ModelAndView addNoteToCorkboard(@Valid @PathVariable(value ="noteID") int noteID, @Valid  @PathVariable(value = "corkboardID") int  corkboardID, @Valid  @PathVariable(value = "novelID") int  novelID){
        Note noteObj = noteService.getNoteById(noteID);
        Corkboard corkboard = corkboardService.getCorkboardById(corkboardID);
        corkboardService.addNoteToCorkboard(corkboard,noteObj);
        noteService.addCorkboardToNote(corkboard,noteObj);
        return setUpCorkboardPage(novelService.getNovelById(novelID));
    }

    @RequestMapping(value ="/removeNoteFromCorkboard/{novelID}/{noteID}/{corkboardID}")
    public ModelAndView removeNoteFromCorkboard(@Valid @PathVariable(value ="noteID") int  noteID, @Valid  @PathVariable(value = "corkboardID") int  corkboardID, @Valid  @PathVariable(value = "novelID") int  novelID){
        Note noteObj = noteService.getNoteById(noteID);
        Corkboard corkboard = corkboardService.getCorkboardById(corkboardID);
        corkboardService.removeNoteFromCorkboard(corkboard,noteObj);
        noteService.removeCorkboardFromNote(noteObj);
        return setUpCorkboardPage(novelService.getNovelById(novelID));
    }
}
