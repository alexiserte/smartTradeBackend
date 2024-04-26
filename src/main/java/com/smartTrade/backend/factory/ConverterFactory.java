package com.smartTrade.backend.Factory;
import com.smartTrade.backend.utils.PNGConverter;

import com.smartTrade.backend.utils.MP3Converter;
import com.smartTrade.backend.utils.JPEGConverter;
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
