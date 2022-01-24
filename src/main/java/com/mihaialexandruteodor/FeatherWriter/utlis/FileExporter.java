package com.mihaialexandruteodor.FeatherWriter.utlis;


import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileExporter {

    public Path export(String fileContent, String fileName) throws IOException, TransformerException, ParserConfigurationException, Docx4JException, JAXBException;
    public void remove(Path filePath) throws IOException, InterruptedException;
}
