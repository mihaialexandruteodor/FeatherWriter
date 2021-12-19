package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.services.NoteService;
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
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/nt/page/{pageNo}")
    public String findPaginated(@Valid @PathVariable(value = "pageNo") int pageNo,
                                @Valid @RequestParam("sortField") String sortField,
                                @Valid @RequestParam("sortDir") String sortDir,
                                ModelAndView model) {
        int pageSize = 5;

        Page<Note> page = noteService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Note> listChapters = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());

        model.addObject("sortField", sortField);
        model.addObject("sortDir", sortDir);
        model.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addObject("listProjects", listChapters);

        return "index";
    }
}