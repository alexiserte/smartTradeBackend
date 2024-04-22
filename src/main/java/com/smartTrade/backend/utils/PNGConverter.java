package com.smartTrade.backend.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class PNGConverter extends Converter<BufferedImage>{

    public String processData(String base64Image) {
        // Decodificar la imagen base64
        BufferedImage originalImage = convertStringToObject(base64Image);

        // Verificar si la decodificación fue exitosa
        if (originalImage == null) {
            return base64Image; 
        }

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
            return null; // Devolver null en caso de error
        }
        byte[] resizedImageBytes = bos.toByteArray();
        String resizedBase64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(resizedImageBytes);

        return resizedBase64Image;
    }


    public BufferedImage convertStringToObject(String base64Image) {
        try {
            String[] parts = base64Image.split(",");
            if (parts.length < 2) {
                System.err.println("La cadena base64 no es válida.");
                return null;
            }
            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bis);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al decodificar la imagen base64: " + e.getMessage());
            return null;
        }
    }

    public void convertStringToFile(String data, String path) {
        BufferedImage image = convertStringToObject(data);
        if (image == null) {
            return;
        }
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            System.err.println("Error al escribir la imagen en el archivo: " + e.getMessage());
        }
    }
}
