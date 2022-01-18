package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/cp/page/{pageNo}")
    public String findPaginated(@Valid @PathVariable(value = "pageNo") int pageNo,
                                @Valid @RequestParam("sortField") String sortField,
                                @Valid @RequestParam("sortDir") String sortDir,
                                ModelAndView model) {
        int pageSize = 6;

        Page<Chapter> page = chapterService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Chapter> listChapters = page.getContent();

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
