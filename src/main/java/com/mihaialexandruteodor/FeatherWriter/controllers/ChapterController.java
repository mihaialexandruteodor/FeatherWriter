package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.services.ChapterService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private NovelService novelService;

    @Autowired
    public ChapterController(ChapterService chapterService, NovelService novelService) {
        this.chapterService = chapterService;
        this.novelService = novelService;
    }

    @GetMapping("/allChaptersPage/{novelID}")
    public ModelAndView allChaptersPage(@Valid @PathVariable("novelID") int novelID, Model model)
    {
        Novel novelObj = novelService.getNovelById(novelID);
        return setUpChaptersPage(model,novelObj);
    }

    @GetMapping("/newChapter/{novelID}")
    public String newChapter(@Valid @PathVariable("novelID") int novelID, Model model)
    {
        Chapter chapter = new Chapter();
        Novel novelObj = novelService.getNovelById(novelID);
        model.addAttribute("chapter",chapter);
        model.addAttribute("novelObj",novelObj);
        return "chapter_creation";
    }

    ModelAndView setUpChaptersPage(Model model, Novel novel)
    {
        ModelAndView mv = new ModelAndView("project_chapters_timeline");
        List<Chapter> assignedChapterList = novel.getChapters();

        mv.addObject("novel",novel);
        mv.addObject("assignedChapterList", assignedChapterList);
        return mv;
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
