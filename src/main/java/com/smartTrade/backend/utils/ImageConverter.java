import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageConverter {

    public static String imageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static void base64ToImage(String base64String, String outputPath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, "png", Paths.get(outputPath).toFile());
        }
    }

    public static void main(String[] args) {
        try {
            String imagePath = "input.png";
            String base64String = imageToBase64(imagePath);
            System.out.println("Image to Base64: ");
            System.out.println(base64String);

            String outputPath = "output.png";
            base64ToImage(base64String, outputPath);
            System.out.println("Base64 to Image: ");
            System.out.println("Image saved at: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
