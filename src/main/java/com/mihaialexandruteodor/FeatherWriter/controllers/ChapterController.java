package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.services.ChapterService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import com.mihaialexandruteodor.FeatherWriter.services.SceneService;
import com.mihaialexandruteodor.FeatherWriter.utlis.DataSingleton;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private NovelService novelService;

    @Autowired
    public ChapterController(ChapterService chapterService, NovelService novelService, SceneService _sceneService) {
        this.chapterService = chapterService;
        this.novelService = novelService;
        this.sceneService = _sceneService;
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

    @PostMapping("/saveChapter/{novelID}")
    public ModelAndView saveChapter(@Valid @PathVariable("novelID") int novelID, @Valid @ModelAttribute("chapter") Chapter chapter, Model model) {
        chapter.setUsername(DataSingleton.getInstance().getCurrentUser());
        chapterService.saveChapter(chapter);
        Novel novel = novelService.getNovelById(novelID);
        chapterService.addProjectToChapter(novel,chapter);
        novelService.addChapterToProject(novel,chapter);
        return setUpChaptersPage(model,novel);
    }

    @GetMapping("/deleteChapter/{chapterID}")
    public ModelAndView deleteChapter(@Valid @PathVariable (value = "chapterID") int chapterID, @Valid @ModelAttribute("novel") Novel novel, Model model) {
        List<Scene> scenes = chapterService.getChapterById(chapterID).getScenes();
        for(Scene scene : scenes)
            sceneService.deleteSceneById(scene.getSceneID());
        chapterService.deleteChapterById(chapterID);
        return setUpChaptersPage(model,novel);
    }

    @GetMapping("/showSceneTimeline/{chapterID}/{novelID}")
    public ModelAndView setUpSceneTimeline(Model model, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable("novelID") int novelID)
    {
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);
        return setUpSceneTimeline( model,  chapter, novel);
    }

    @RequestMapping(value = "/saveSceneText", method = GET)
    @ResponseBody
    public ModelAndView saveSceneText(@RequestParam(value = "fileContent", required = false, defaultValue = "<p></p>") String fileContent, @RequestParam("chapterID") int chapterID, @RequestParam(value = "novelID") int novelID , @RequestParam(value = "sceneID") int sceneID,Model model) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        Scene scene = sceneService.getSceneById(sceneID);
        scene.setText(fileContent);
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);

        sceneService.saveScene(scene);

        return setUpSceneTimeline(model,chapter,novel);

    }

    @GetMapping("/moveUpSceneInTimeline/{sceneID}/{chapterID}/{novelID}")
    public ModelAndView moveUpSceneInTimeline(Model model, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable("sceneID") int sceneID, @Valid @PathVariable("novelID") int novelID)
    {
        Scene donor = sceneService.getSceneById(sceneID);
        Scene receiver = sceneService.getSceneById(sceneID-1);
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);
        sceneService.swapScenesText(donor, receiver);
        return setUpSceneTimeline(model, chapter, novel);
    }

    @GetMapping("/moveDownSceneInTimeline/{sceneID}/{chapterID}/{novelID}")
    public ModelAndView moveDownSceneInTimeline(Model model, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable("sceneID") int sceneID, @Valid @PathVariable("novelID") int novelID)
    {
        Scene donor = sceneService.getSceneById(sceneID);
        Scene receiver = sceneService.getSceneById(sceneID+1);
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);
        sceneService.swapScenesText(donor, receiver);
        return setUpSceneTimeline(model, chapter, novel);
    }

    public ModelAndView setUpSceneTimeline(Model model, Chapter chapter, Novel novel)
    {
        ModelAndView mv = new ModelAndView("chapter_scenes_timeline");
        List<Scene> assignedSceneList = chapter.getScenes();

        mv.addObject("chapter",chapter);
        mv.addObject("novel",novel);
        mv.addObject("assignedSceneList", assignedSceneList);
        return mv;
    }

    @GetMapping("/moveChapterUpInTimeline/{novelID}/{chapterID}")
    public ModelAndView moveChapterUpInTimeline(Model model, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable("novelID") int novelID)
    {
        Chapter donor = chapterService.getChapterById(chapterID);
        Chapter receiver = chapterService.getChapterById(chapterID-1);
        chapterService.swapChapters(donor, receiver);
        Novel novel = novelService.getNovelById(novelID);
        return setUpChaptersPage(model, novel);
    }

    @GetMapping("/moveChapterDownInTimeline/{novelID}/{chapterID}")
    public ModelAndView moveChapterDownInTimeline(Model model, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable("novelID") int novelID)
    {
        Chapter donor = chapterService.getChapterById(chapterID);
        Chapter receiver = chapterService.getChapterById(chapterID+1);
        chapterService.swapChapters(donor, receiver);
        Novel novel = novelService.getNovelById(novelID);
        return setUpChaptersPage(model, novel);
    }

    public ModelAndView setUpChaptersPage(Model model, Novel novel)
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
