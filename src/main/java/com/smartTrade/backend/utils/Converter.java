package com.smartTrade.backend.utils;

public abstract class Converter<T>{
    
    public abstract String processData(String path);
    public abstract void convertStringToFile(String data, String path);
    public abstract T convertStringToObject(String data);
    
}
