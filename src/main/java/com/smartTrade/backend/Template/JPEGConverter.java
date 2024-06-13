package com.smartTrade.backend.Template;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class JPEGConverter extends Converter<BufferedImage> {

    @Override
    public BufferedImage convertToFile(String base64Image) {
        try {
            String[] parts = base64Image.split(",");
            if (parts.length < 2) {
                System.err.println("La cadena base64 no es válida.");
                return null;
            }
            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                return ImageIO.read(bis);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al decodificar la imagen base64: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String transformFileAndConvertToBase64(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("La imagen proporcionada es nula.");
        }

        int targetWidth = 512;
        int targetHeight = 512;

        if (image.getWidth() == targetWidth && image.getHeight() == targetHeight) {
            return convertToBase64(image);
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return convertToBase64(resizedImage);
    }

    @Override
    public String convertToBase64(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("La imagen proporcionada es nula.");
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpeg", bos);
            byte[] imageBytes = bos.toByteArray();
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir la imagen a Base64: " + e.getMessage(), e);
        }
    }

    public void convertStringToFile(String data, String path) {
        BufferedImage image = convertToFile(data);
        if (image == null) {
            return;
        }
        try {
            ImageIO.write(image, "jpeg", new File(path));
        } catch (IOException e) {
            System.err.println("Error al escribir la imagen en el archivo: " + e.getMessage());
        }
    }

    public String convertFileToBase64(String filePath) {
        try {
            File file = new File(filePath);
            byte[] fileBytes = new byte[(int) file.length()];
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(fileBytes);
            }

            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo o convertirlo a base64: " + e.getMessage());
            return null;
        }
    }
}
