package com.smartTrade.backend.Factory;
import com.smartTrade.backend.Template.JPEGConverter;
import com.smartTrade.backend.Template.MP3Converter;
import com.smartTrade.backend.Template.PNGConverter;

public class ConverterFactory {
    public Object createConversor(String type){
        switch(type){
            case ".png":
                return new PNGConverter();
            case ".jpeg":
                return new JPEGConverter();
            case ".mp3":
                return new MP3Converter();
            /*  RESTO DE FORMATOS   */
            default:
                return null;
        }
    }
}
