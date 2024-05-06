package com.smartTrade.backend.Factory;
import com.smartTrade.backend.Utils.*;
public class ConverterFactory {
    
    public Object createConversor(String type){
        switch(type){
            case "JPEG":
                return new JPEGConverter();
            case "PNG":
                return new PNGConverter();
            case "MP3":
                return new MP3Converter();
            default:
                return null;
        }
    }
    
}
