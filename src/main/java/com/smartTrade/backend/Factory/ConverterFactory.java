package com.smartTrade.backend.Factory;
import com.smartTrade.backend.Utils.*;
public class ConverterFactory {
    public Object createConversor(String type){
        switch(type){
            case ".png":
                return new JPEGConverter();
            case ".jpeg":
                return new PNGConverter();
            case ".mp3":
                return new MP3Converter();
            /*  RESTO DE FORMATOS   */
            default:
                return null;
        }
    }
}
