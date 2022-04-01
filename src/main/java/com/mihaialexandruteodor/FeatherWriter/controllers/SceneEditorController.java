package com.mihaialexandruteodor.FeatherWriter.controllers;


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
import org.springframework.web.bind.annotation.*;
;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class SceneEditorController {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    public SceneEditorController(SceneService _sceneService, ChapterService _chapterService)
    {
        sceneService = _sceneService;
        chapterService = _chapterService;
    }

    @Autowired
    private FileExporter fileExporter;



    @RequestMapping(value = "/saveSceneText", method = POST)
    @ResponseBody
    public ResponseEntity<?> saveSceneText(@RequestParam(value = "fileContent", required = false, defaultValue = "<p>test</p>") String fileContent, @RequestParam(value = "sceneID") int sceneID) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        Scene scene = sceneService.getSceneById(sceneID);
        scene.setText(fileContent);
        sceneService.saveScene(scene);
        return new ResponseEntity<>(null, HttpStatus.OK);

    }


    @RequestMapping(value = "/downloadTextFileScene", method = GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> saveSceneLocally(@RequestParam(value = "fileContent", required = false, defaultValue = "<p>test</p>") String fileContent) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        String fileName = "PLACEHOLDER_USE_FUNC_PARAM.docx";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);


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
            //  fileExporter.remove(exportedPath);
        }

    }

}
