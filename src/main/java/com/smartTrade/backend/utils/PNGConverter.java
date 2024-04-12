package com.smartTrade.backend.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class PNGConverter {

    public static String convertImageToBase64(String imagePath) {
        String base64Image = "";
        try (FileInputStream fileInputStreamReader = new FileInputStream(imagePath)) {
            byte[] imageData = new byte[fileInputStreamReader.available()];
            fileInputStreamReader.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de imagen: " + e.getMessage());
        }
        return "data:image/png;base64," + base64Image;
    }

    public static void base64ToImage(String base64Data, String dataDir) throws IOException {
        // Decodificar la cadena base64 pasada como parámetro
        String base64ImageString = base64Data.replace("data:image/png;base64,", "");
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);

        // Convertir Base64 a imagen JPG o PNG
        try (FileOutputStream fos = new FileOutputStream("./converted/" + dataDir + "Base64 to Image.png")) {
            fos.write(imageBytes);
        }
    }

}
