package com.ynov.webfullstack.imagelib.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFileService {
    private static final String imagesDirectory = "files/images/";
    
    public static final void saveImageFile(String imageFileName, byte[] fileBytes) throws IOException {
        Path imagePath = Paths.get(imagesDirectory, imageFileName);
        Files.createDirectories(imagePath.getParent());
        FileOutputStream output = new FileOutputStream(imagePath.toString());
        output.write(fileBytes);
        output.flush();
        output.close();
    }

    public static final boolean removeImageFile(String imageFileName) throws IOException {
        Path imagePath = Paths.get(imagesDirectory, imageFileName);
        return Files.deleteIfExists(imagePath);
    }
}