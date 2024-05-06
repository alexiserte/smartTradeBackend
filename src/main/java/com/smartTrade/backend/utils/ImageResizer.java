package com.smartTrade.backend.Utils;


public class ImageResizer {
    /*/
    public static String resizeImageTo512x512(String base64Image) {
        try {
            // Decodificar la imagen base64
            PNGConverter.base64ToImage(base64Image,"tmp.png");

            // Leer la imagen original desde el archivo
            BufferedImage originalImage = ImageIO.read(Paths.get("./tmp.png").toFile());
            
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
            ImageIO.write(resizedImage, "png", bos);
            byte[] resizedImageBytes = bos.toByteArray();
            String resizedBase64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(resizedImageBytes);
            
            return resizedBase64Image;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null; // En caso de error, retornar null
        }
    }
    */
}
