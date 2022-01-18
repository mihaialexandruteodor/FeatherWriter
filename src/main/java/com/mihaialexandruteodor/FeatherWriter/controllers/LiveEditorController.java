package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.utlis.FileExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;

@Controller
public class LiveEditorController {

    @Autowired
    private FileExporter fileExporter;

    @RequestMapping("/liveEditor")
    public String viewLiveEditor( ) {

        return "live_editor_page";
    }


    @PostMapping("/downloadTextFile")
    public ResponseEntity<InputStreamResource> saveFileLocally(@RequestParam(value = "fileContent", required = true) String fileContent) throws IOException, InterruptedException {
        String fileName = "example1.rtf";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);
        //Path exportedPath = Paths.get("/FeatherWriter/example1.rtf");
        System.out.println("EXPORTED PATH: " + exportedPath);

        // Download file with InputStreamResource
        try{
            File exportedFile = exportedPath.toFile();
            exportedFile.createNewFile();
            long contentLength = exportedFile.length();
            FileInputStream fileInputStream = new FileInputStream(exportedFile);
            InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(contentLength)
                    .body(inputStreamResource);
        }
        finally {
            // cleanup local folder
           // fileExporter.remove(exportedPath);
        }

    }

    @PostMapping("/saveFileToCloud")
    public ResponseEntity saveFileToCloud(@RequestParam(value = "fileContent", required = true) String fileContent) throws IOException, InterruptedException {
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
