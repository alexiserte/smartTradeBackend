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

public class PNGConverter{

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

    public void base64ToImage(String base64Data, String outputPath) throws IOException {
        // Guarde la cadena base64 en el archivo TXT porque la cadena es larga
FileInputStream fis = new FileInputStream(dataDir + "base64.txt");
String base64 = IOUtils.toString(fis, "UTF-8");
String base64ImageString = base64.replace("data:image/png;base64,", "");
byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64ImageString);

// Convertir Base64 a imagen JPG o PNG
FileOutputStream fos = new FileOutputStream(dataDir + "Base64 to Image.jpg");
//FileOutputStream fos = new FileOutputStream(dataDir + "Base64 to Image.png");
try {
    fos.write(imageBytes);
}
finally {
    fos.close();
}
    }

    public static void main(String[] args) throws IOException{
        PNGConverter converter  = new PNGConverter();
        String text = "";
        Scanner sc = new Scanner("./BASE64.txt");
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            text = text  + line + "\n";
        }
        converter.base64ToImage(text,"resultado");
    }
}
