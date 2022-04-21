package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.utlis.FileExporter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;


import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class LiveEditorController {

    @Autowired
    private FileExporter fileExporter;

    @RequestMapping("/liveEditor")
    public String viewLiveEditor( ) {

        return "live_editor_page";
    }

    @RequestMapping(value = "/downloadTextFile", method = GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> saveFileLocally(@RequestParam(value = "fileContent", required = false, defaultValue = "<p></p>") String fileContent) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        String fileName = "FeatherWriter.docx";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);


        // Download file with InputStreamResource
        try{
            File exportedFile = exportedPath.toFile();
            long contentLength = exportedFile.length();
            FileInputStream fileInputStream = new FileInputStream(exportedFile);
            InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName)
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
