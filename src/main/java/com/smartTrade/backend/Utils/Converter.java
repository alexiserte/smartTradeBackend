package com.smartTrade.backend.Utils;

public abstract class Converter<T>{
    public String procesar(String source){
        T file = convertToFile(source);
        file = transformFile(file);
        return convertToBase64(file);
    }

    public abstract T convertToFile(String source);
    public abstract T transformFile(T file);
    public abstract String convertToBase64(T file);
}
