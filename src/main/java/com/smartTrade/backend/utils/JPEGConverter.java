package com.smartTrade.backend.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.PrintWriter;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class JPEGConverter{

    public String imageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public void base64ToImage(String base64String, String outputPath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, "jpg", Paths.get(outputPath).toFile());
        }
    }
}
