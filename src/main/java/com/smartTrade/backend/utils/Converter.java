package com.smartTrade.backend.Utils;

public abstract class Converter<T>{
    public void processAndInsert(String path){
        String data = processData(path);
        convertStringToFile(data, path);
    }
    public abstract String processData(String path);
    public abstract void convertStringToFile(String data, String path);
    public abstract T convertStringToObject(String data);
    public abstract void convertFile(String path);

}
