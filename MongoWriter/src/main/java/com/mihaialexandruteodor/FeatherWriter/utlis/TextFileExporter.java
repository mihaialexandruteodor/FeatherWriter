package com.mihaialexandruteodor.FeatherWriter.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class TextFileExporter implements FileExporter {

    private static final String EXPORT_DIRECTORY = DataSingleton.getInstance().getDownloadPath();

    private Logger logger = LoggerFactory.getLogger(TextFileExporter.class);

    @Override
    public Path export(String fileContent, String fileName) {
        Path filePath = Paths.get(EXPORT_DIRECTORY, fileName);
        System.out.println(filePath);
        try {
            Path exportedFilePath = Files.write(filePath, fileContent.getBytes(), StandardOpenOption.CREATE);
            return exportedFilePath;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void remove(Path filePath) throws IOException, InterruptedException {
        new Thread(new Runnable() {
            public void run(){
                while(!filePath.toFile().renameTo(filePath.toFile())) {
                    // Cannot read from file, windows still working on it.
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
            }
        }).start();



    }
}
