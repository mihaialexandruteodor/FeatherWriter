package com.mihaialexandruteodor.FeatherWriter.controllers;

import com.mihaialexandruteodor.FeatherWriter.utlis.FileExporter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

@Controller
public class LiveEditorController {

    @Autowired
    private FileExporter fileExporter;

    @RequestMapping("/liveEditor")
    public String viewLiveEditor( ) {

        return "live_editor_page";
    }



    @GetMapping("/downloadTextFile")
    public ResponseEntity<InputStreamResource> saveFileLocally(@RequestParam(value = "fileContent", required = true) String fileContent) throws JAXBException, IOException, ParserConfigurationException, TransformerException, InterruptedException, Docx4JException {
        String fileName = "PLACEHOLDER_USE_FUNC_PARAM.xml";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);

        Thread.sleep(10000);

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

//    @SuppressWarnings("resource")
//    @RequestMapping(value="/downloadTextFile", method=RequestMethod.GET, produces="application/octet-stream")
//    public ResponseEntity<InputStreamResource> saveFileLocally(@RequestParam(value = "fileContent", required = true) String fileContent) throws Throwable {
//
//        String fileName = "example1.docx";
//        Path exportedPath = fileExporter.export(fileContent, fileName);
//        //Path exportedPath = Paths.get("/FeatherWriter/example1.rtf");
//        System.out.println("EXPORTED PATH: " + exportedPath);
//
//        File docx = exportedPath.toFile();
//
//        FileInputStream fileInputStream = new FileInputStream(docx);
//        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment;filename=" + fileName);
//        headers.add("Content-Type", "application/octet-stream;");
//
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(inputStreamResource);
//
//
//    }

}
