package com.smartTrade.backend.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class MP3Coder {

    // Codificar un archivo MP3 a texto
    public static String codificarMP3aTexto(String rutaArchivo) throws Exception {
        byte[] archivoBytes = Files.readAllBytes(Paths.get(rutaArchivo));
        return Base64.getEncoder().encodeToString(archivoBytes);
    }

    // Decodificar texto a archivo MP3
    public static void decodificarTextoaMP3(String textoCodificado, String nombreArchivo) throws Exception {
        byte[] archivoBytes = Base64.getDecoder().decode(textoCodificado);
        Files.write(Paths.get(nombreArchivo), archivoBytes);
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        String archivoMP3 = "2024-03-29 02-48-58.mp3";
        String textoCodificado = null;

        try {
            // Codificar el archivo MP3 a texto
            textoCodificado = codificarMP3aTexto(archivoMP3);
            System.out.println("Contenido codificado en texto:");
            System.out.println(textoCodificado);

            // Decodificar el texto y guardar como archivo MP3
            String nombreArchivoRecuperado = "ejemplo_recuperado.mp3";
            decodificarTextoaMP3(textoCodificado, nombreArchivoRecuperado);
            System.out.println("Archivo MP3 decodificado guardado como: " + nombreArchivoRecuperado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
