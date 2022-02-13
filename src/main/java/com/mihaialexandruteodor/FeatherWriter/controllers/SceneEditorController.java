package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.services.SceneService;
import com.mihaialexandruteodor.FeatherWriter.utlis.FileExporter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
    public SceneEditorController(SceneService _sceneService)
    {
        sceneService = _sceneService;
    }

    @Autowired
    private FileExporter fileExporter;

    @RequestMapping("/sceneEditor/{scene_id}")
    public ModelAndView viewSceneEditor( @Valid @PathVariable(value = "scene_id") int scene_id) {
        Scene scene = sceneService.getSceneById(scene_id);
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene", scene);
        return mv;
    }

    @PostMapping("/saveScene")
    public String saveCharacter(@Valid @ModelAttribute("scene") Scene scene) {
        sceneService.saveScene(scene);
        return "redirect:/scenesPage";
    }

    @GetMapping("/deleteScene/{scene_id}")
    public String deleteCharacterProfile(@Valid @PathVariable(value = "scene_id") int scene_id) {
        sceneService.deleteSceneById(scene_id);
        return "redirect:/scenesPage";
    }

    @GetMapping("/newScene")
    public ModelAndView newScene(Model model) {
        Scene scene = new Scene();
        ModelAndView mv = new ModelAndView("scene_editor_page");
        mv.addObject("scene",scene);
        return mv;
    }

    @RequestMapping(value = "/downloadTextFile", method = POST)
    @ResponseBody
    public ResponseEntity<InputStreamResource> saveFileLocally(@RequestParam(value = "fileContent", required = false, defaultValue = "<p>test</p>") String fileContent) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
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
