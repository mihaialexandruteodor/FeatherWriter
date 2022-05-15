package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.services.CorkboardService;
import com.mihaialexandruteodor.FeatherWriter.services.NoteService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import com.mihaialexandruteodor.FeatherWriter.utlis.DataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/noteProfile/{novelID}/{noteID}")
    public ModelAndView noteProfile(@Valid @PathVariable("novelID") int novelID, @Valid @PathVariable("noteID") int noteID)
    {
        ModelAndView mv = new ModelAndView("note_profile");
        Novel novelObj = novelService.getNovelById(novelID);
        Note note =noteService.getNoteById(noteID);
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

    @GetMapping("/editNote/{novelID}/{noteID}")
    public ModelAndView editNote(@Valid @PathVariable("novelID") int novelID, @Valid @PathVariable("noteID") int noteID)
    {
        Note note = noteService.getNoteById(noteID);
        Novel novelObj = novelService.getNovelById(novelID);
        ModelAndView mv = new ModelAndView("edit_note");
        mv.addObject("note",note);
        mv.addObject("novel",novelObj);
        return mv;
    }

    @GetMapping(path = {"/noteSearch/{novelID}"})
    public ModelAndView noteSearch(@Valid @PathVariable("novelID") int novelID, @Valid @RequestParam(value = "keyword") String keyword) {
        ModelAndView mv = new ModelAndView("note_search_result");
        if(keyword!=null) {
            List<Note> listNotes = noteService.searchNoteByTitle(DataSingleton.getInstance().getCurrentUser(), keyword);
            Novel novel = novelService.getNovelById(novelID);
            mv.addObject("listNotes", listNotes);
            mv.addObject("novel", novel);
        }
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
    public ModelAndView removeNoteFromCorkboard(@Valid @PathVariable("noteID") int noteID, @Valid  @PathVariable(value = "novelID") int  novelID){
        Novel novel = novelService.getNovelById(novelID);
        Note note = noteService.getNoteById(noteID);
        Corkboard corkboard = novel.getCorkboard();
        corkboardService.removeNoteFromCorkboard(corkboard,note);
        noteService.deleteNoteById(noteID);
        return setUpCorkboardPage(novelService.getNovelById(novelID));
    }
}
