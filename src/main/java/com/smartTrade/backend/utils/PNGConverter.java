package com.smartTrade.backend.utils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

public class PNGConverter {

    public static String convertAndResizeImageToBase64(String imagePath) {
        // Convertir la imagen en base64
        String base64Image = convertImageToBase64(imagePath);

        // Decodificar la imagen base64
        BufferedImage originalImage = base64ToImage(base64Image);

        // Obtener el ancho y alto originales
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Verificar si la imagen ya está en 512x512
        if (originalWidth == 512 && originalHeight == 512) {
            return base64Image; // La imagen ya está en el tamaño deseado
        }

        // Crear una nueva imagen en blanco con el tamaño deseado (512x512)
        BufferedImage resizedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage.getScaledInstance(512, 512, Image.SCALE_SMOOTH), 0, 0, 512, 512, null);
        g.dispose();

        // Convertir la imagen redimensionada a base64
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(resizedImage, "png", bos);
        } catch (IOException e) {
            System.err.println("Error al escribir la imagen redimensionada: " + e.getMessage());
            return base64Image; // Devolver la imagen original en caso de error
        }
        byte[] resizedImageBytes = bos.toByteArray();
        String resizedBase64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(resizedImageBytes);

        return resizedBase64Image;
    }

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

    private static BufferedImage base64ToImage(String base64Image) {
        try {
            String[] parts = base64Image.split(",");
            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bis);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error al decodificar la imagen base64: " + e.getMessage());
            return null;
        }
    }
    

    public static void main(String[] args) {
        String imagePath = "./image.png";
        String resizedBase64Image = convertAndResizeImageToBase64(imagePath);
        System.out.println("Imagen redimensionada en base64: " + resizedBase64Image);
    }
}
