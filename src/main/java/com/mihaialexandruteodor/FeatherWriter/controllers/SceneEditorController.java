package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.services.ChapterService;
import com.mihaialexandruteodor.FeatherWriter.services.SceneService;
import com.mihaialexandruteodor.FeatherWriter.utlis.FileExporter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SceneEditorController {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    public SceneEditorController(SceneService _sceneService)
    {
        sceneService = _sceneService;
        chapterService = chapterService;
    }

    @Autowired
    private FileExporter fileExporter;

    @RequestMapping("/sceneEditor/{sceneID}/{chapterID}")
    public ModelAndView viewSceneEditor( @Valid @PathVariable(value = "sceneID") int sceneID, @Valid @PathVariable(value = "chapterID") int chapterID) {
        Scene scene = sceneService.getSceneById(sceneID);
        Chapter chapter = chapterService.getChapterById(chapterID);
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene", scene);
        mv.addObject("chapter", chapter);
        return mv;
    }

    @PostMapping("/saveScene")
    public String saveScene(@Valid @ModelAttribute("scene") Scene scene, @Valid @ModelAttribute("chapter") Chapter chapter) {
        sceneService.saveScene(scene);
        return "redirect:/sceneEditor/"+scene.getSceneID()+"/"+chapter.getChapterID();
    }

    @PostMapping("/saveSceneText/{chapterID}")
    public String saveSceneText(@Valid @ModelAttribute("scene") Scene scene, @Valid @PathVariable("chapterID") int chapterID) {
        Chapter chapter = chapterService.getChapterById(chapterID);
        sceneService.addChapterToScene(scene, chapter);
        chapterService.addSceneToChapter(scene,chapter);
        return  "redirect:/showSceneTimeline/" + chapter.getChapterID();
    }

    @GetMapping("/deleteScene/{sceneID}")
    public String deleteCharacterProfile(@Valid @PathVariable(value = "scene_id") int sceneID) {
        int chapterID = sceneService.getSceneById(sceneID).getChapter().getChapterID();
        sceneService.deleteSceneById(sceneID);
        return  "redirect:/showSceneTimeline/" + chapterID;
    }


    @GetMapping("/newScene/{chapterID}")
    public ModelAndView newScene(@Valid @PathVariable("chapterID") int chapterID)
    {
        Scene scene = new Scene();
        Chapter chapter = chapterService.getChapterById(chapterID);
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene",scene);
        mv.addObject("chapter",chapter);
        return mv;
    }

    @GetMapping("/editScene/{sceneID}")
    public ModelAndView editScene(@Valid @PathVariable("sceneID") int sceneID)
    {
        Scene scene = sceneService.getSceneById(sceneID);
        Chapter chapter = scene.getChapter();
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene",scene);
        mv.addObject("chapter",chapter);
        return mv;
    }

    @RequestMapping(value = "/saveSceneText", method = POST)
    @ResponseBody
    public ResponseEntity<?> saveSceneText(@RequestParam(value = "fileContent", required = false, defaultValue = "<p>test</p>") String fileContent, @RequestParam(value = "sceneID") int sceneID, @RequestParam(value = "chapterID") int chapterID) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {

        Chapter chapter = chapterService.getChapterById(chapterID);
        Scene scene = sceneService.getSceneById(sceneID);
        sceneService.addChapterToScene(scene, chapter);
        chapterService.addSceneToChapter(scene,chapter);
        return new ResponseEntity<>(null, HttpStatus.OK);

    }


    @RequestMapping(value = "/downloadTextFileScene", method = POST)
    @ResponseBody
    public ResponseEntity<InputStreamResource> saveSceneLocally(@RequestParam(value = "fileContent", required = false, defaultValue = "<p>test</p>") String fileContent) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        String fileName = "PLACEHOLDER_USE_FUNC_PARAM.xml";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);

        System.out.println("EXPORTED PATH: " + exportedPath);

        // Download file with InputStreamResource
        try{
            File exportedFile = exportedPath.toFile();
            long contentLength = exportedFile.length();
            FileInputStream fileInputStream = new FileInputStream(exportedFile);
            InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(contentLength)
                    .body(inputStreamResource);
        }
        finally {
            // cleanup local folder
            fileExporter.remove(exportedPath);
        }

    }

}
