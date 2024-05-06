package com.smartTrade.backend.Utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;

public class PNGConverter extends Converter<BufferedImage>{

    public BufferedImage convertToFile(String base64Image) {
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

    public BufferedImage transformFile(BufferedImage image){
        BufferedImage originalImage = image;

        if (originalImage == null) {
            return null;
        }

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth == 512 && originalHeight == 512) {
            return null;
        }

        BufferedImage resizedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage.getScaledInstance(512, 512, Image.SCALE_SMOOTH), 0, 0, 512, 512, null);
        g.dispose();

        return resizedImage;
    }


    public String convertToBase64(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("La imagen proporcionada es nula");
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            byte[] resizedImageBytes = bos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(resizedImageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir la imagen a Base64: " + e.getMessage());
        }
    }


    // Este método solo se usa para convertir los códigos QR a PNG. ¡ NO SE DEBE DE USAR PARA EL RESTO DE PRODUCTOS!
    public String convertFileToBase64(String filePath) {
        try {
            // Leer el archivo de la ruta especificada
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);
            fileInputStream.close();

            // Convertir los bytes a base64
            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileBytes);

            return base64Image;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo o convertirlo a base64: " + e.getMessage());
            return null;
        }
    }


}

