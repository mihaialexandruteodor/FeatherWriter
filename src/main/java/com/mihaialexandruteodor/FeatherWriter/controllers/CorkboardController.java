package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.services.CorkboardService;
import com.mihaialexandruteodor.FeatherWriter.services.NoteService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView corkboardPage(@Valid  @PathVariable(value = "novelID") int  novelID) {

        Novel novel = novelService.getNovelById(novelID);
        return setUpCorkboardPage(novel);
    }

    public ModelAndView setUpCorkboardPage( Novel novel)
    {
        ModelAndView mv = new ModelAndView("corkboard_page");
        Corkboard corkboard = novel.getCorkboard();
        mv.addObject("novel",novel);
        mv.addObject("corkboard",corkboard);
        mv.addObject("listNotes",corkboard.getNotes());
        return mv;
    }

    @GetMapping("/newNote/{novelID}")
    public ModelAndView newNote(@Valid @PathVariable("novelID") int novelID)
    {
        ModelAndView mv = new ModelAndView("note_creation");
        Note note = new Note();
        Novel novelObj = novelService.getNovelById(novelID);
        mv.addObject("note",note);
        mv.addObject("novel",novelObj);
        return mv;
    }

    @GetMapping("/removeNote/{novelID}/{noteID}")
    public ModelAndView newNote(@Valid @PathVariable("novelID") int novelID, @Valid @PathVariable("noteID") int noteID)
    {
        Note note = noteService.getNoteById(noteID);
        Novel novelObj = novelService.getNovelById(novelID);
        novelObj.getCorkboard().removeNote(note);
        noteService.deleteNoteById(noteID);
        corkboardService.saveCorkboard(novelObj.getCorkboard());
        return corkboardPage(novelID);
    }

    @GetMapping("/editNote/{noteID}")
    public ModelAndView editNote(@Valid @PathVariable("noteID") int noteID)
    {
        Note note = noteService.getNoteById(noteID);
        ModelAndView mv = new ModelAndView("note_creation");
        mv.addObject("note",note);
        return mv;
    }

    @RequestMapping(value ="/addNoteToCorkboard/{novelID}")
    public ModelAndView addNoteToCorkboard(@Valid @ModelAttribute(value ="note") Note noteObj, @Valid  @PathVariable(value = "novelID") int  novelID){
        Novel novel = novelService.getNovelById(novelID);
        Corkboard corkboard = novel.getCorkboard();
        corkboardService.addNoteToCorkboard(corkboard,noteObj);
        noteService.addCorkboardToNote(corkboard,noteObj);
        return setUpCorkboardPage(novelService.getNovelById(novelID));
    }

    @RequestMapping(value ="/removeNoteFromCorkboard/{novelID}/{noteID}")
    public ModelAndView removeNoteFromCorkboard(@Valid @ModelAttribute(value ="note") Note  noteObj, @Valid  @PathVariable(value = "novelID") int  novelID){
        Novel novel = novelService.getNovelById(novelID);
        int noteID = noteObj.getNoteID();
        Corkboard corkboard = novel.getCorkboard();
        corkboardService.removeNoteFromCorkboard(corkboard,noteObj);
        noteService.deleteNoteById(noteID);
        return setUpCorkboardPage(novelService.getNovelById(novelID));
    }
}
