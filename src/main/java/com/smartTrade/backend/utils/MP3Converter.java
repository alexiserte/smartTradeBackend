package com.smartTrade.backend.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.PrintWriter;
import java.io.File;

public class MP3Converter{

    // Codificar un archivo MP3 a texto
    public String codificarMP3aTexto(String rutaArchivo) throws Exception {
        byte[] archivoBytes = Files.readAllBytes(Paths.get(rutaArchivo));
        return Base64.getEncoder().encodeToString(archivoBytes);
    }


    public void decodificarTextoaMP3(String textoCodificado, String nombreArchivo) throws Exception {
        byte[] archivoBytes = Base64.getDecoder().decode(textoCodificado);
        Files.write(Paths.get(nombreArchivo + ".mp3"), archivoBytes);
    }
}
