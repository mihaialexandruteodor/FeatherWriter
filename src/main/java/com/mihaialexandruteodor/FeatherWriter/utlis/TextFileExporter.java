package com.mihaialexandruteodor.FeatherWriter.utlis;


import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;


@Service
public class TextFileExporter implements FileExporter {

    private static final String EXPORT_DIRECTORY = DataSingleton.getInstance().getDownloadPath();

    private Logger logger = LoggerFactory.getLogger(TextFileExporter.class);

    @Override
    public Path export(String fileContent, String fileName) throws JAXBException, Docx4JException, IOException {

        Path filePath = Paths.get(EXPORT_DIRECTORY, fileName);
        if(filePath.toFile().renameTo(filePath.toFile()))
             Files.delete(filePath);

        WordprocessingMLPackage docxOut = WordprocessingMLPackage.createPackage();
        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        docxOut.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();

        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(docxOut);

        String xhtml=
                "<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:100%;\"><tbody><tr><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td></tr></tbody></table>";


        docxOut.getMainDocumentPart().getContent().addAll(
               // XHTMLImporter.convert( fileContent, null) );
                XHTMLImporter.convert( xhtml, null) );

        docxOut.save(new java.io.File(EXPORT_DIRECTORY+fileName));
        Files.delete(filePath);
        docxOut.save(new java.io.File(EXPORT_DIRECTORY+fileName));      //strange fix for LibreOfice bug

        return filePath;

    }

    @Override
    public void remove(Path filePath) {
        new Thread(() -> {
            while(!filePath.toFile().renameTo(filePath.toFile())) {
                // Cannot read from file, os still working on it.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
