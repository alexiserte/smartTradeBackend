package com.smartTrade.backend.factory;
import com.smartTrade.backend.utils.PNGConverter;

import ch.qos.logback.core.pattern.Converter;

import com.smartTrade.backend.utils.MP3Converter;
import com.smartTrade.backend.utils.Conversor;
import com.smartTrade.backend.utils.JPEGConverter;
public class ConversorFactory {
    
    public ConversorFactory(){}

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
