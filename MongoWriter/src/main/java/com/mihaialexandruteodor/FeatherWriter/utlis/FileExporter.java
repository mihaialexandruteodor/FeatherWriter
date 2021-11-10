package com.mihaialexandruteodor.FeatherWriter.utlis;

import java.io.IOException;
import java.nio.file.Path;

public interface FileExporter {

    public Path export(String fileContent, String fileName);
    public void remove(Path filePath) throws IOException, InterruptedException;
}
