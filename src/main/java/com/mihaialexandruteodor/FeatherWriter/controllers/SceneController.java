package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.services.ChapterService;
import com.mihaialexandruteodor.FeatherWriter.services.NovelService;
import com.mihaialexandruteodor.FeatherWriter.services.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SceneController {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private NovelService novelService;


    @Autowired
    public SceneController(SceneService _sceneService, ChapterService _chapterService)
    {
        sceneService = _sceneService;
        chapterService = _chapterService;
    }

    @RequestMapping("/sceneEditor/{sceneID}/{novelID}")
    public ModelAndView viewSceneEditor(@Valid @PathVariable(value = "sceneID") int sceneID, @Valid @PathVariable(value = "novelID") int novelID) {
        Scene scene = sceneService.getSceneById(sceneID);
        Novel novel = novelService.getNovelById(novelID);
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene", scene);
        mv.addObject("novel", novel);
        return mv;
    }

    @PostMapping("/saveScene/{chapterID}/{novelID}")
    public ModelAndView saveScene(@Valid @ModelAttribute("scene") Scene scene, @Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable(value = "novelID") int novelID) {
        sceneService.saveScene(scene);
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);
        sceneService.addChapterToScene(scene,chapter);
        chapterService.addSceneToChapter(scene,chapter);
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene", scene);
        mv.addObject("chapter", chapter);
        mv.addObject("novel", novel);
        return mv;
    }


    @GetMapping("/deleteScene/{sceneID}/{chapterID}")
    public String deleteScene(@Valid @PathVariable(value = "sceneID") int sceneID, @Valid @PathVariable(value = "chapterID") int chapterID) {
        Chapter chapter = chapterService.getChapterById(chapterID);
        chapter.removeScene(sceneService.getSceneById(sceneID));
        sceneService.deleteSceneById(sceneID);
        return  "redirect:/showSceneTimeline/" + chapterID;
    }


    @GetMapping("/newScene/{chapterID}/{novelID}")
    public ModelAndView newScene(@Valid @PathVariable("chapterID") int chapterID, @Valid @PathVariable(value = "novelID") int novelID)
    {
        Scene scene = new Scene();
        Chapter chapter = chapterService.getChapterById(chapterID);
        Novel novel = novelService.getNovelById(novelID);
        ModelAndView mv = new ModelAndView("scene_creation");
        mv.addObject("scene",scene);
        mv.addObject("chapter",chapter);
        mv.addObject("novel",novel);
        return mv;
    }

    @GetMapping("/editScene/{sceneID}")
    public ModelAndView editScene(@Valid @PathVariable("sceneID") int sceneID)
    {
        Scene scene = sceneService.getSceneById(sceneID);
        //System.out.println("TEST: " + scene.getText());
        Chapter chapter = scene.getChapter();
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene",scene);
        mv.addObject("chapter",chapter);
        return mv;
    }
}
