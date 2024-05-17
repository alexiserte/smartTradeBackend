package com.smartTrade.backend.Template;


public abstract class Converter<T>{

    public String procesar(String source){
        T file = convertToFile(source);
        return transformFileAndConvertToBase64(file);
    }

    public abstract T convertToFile(String source);
    public abstract String transformFileAndConvertToBase64(T file);
    public abstract String convertToBase64(T file);


    public static String getFileFormatFromBase64(String base64){
        int limit1 = base64.indexOf("/");
        int limit2 = base64.indexOf(";");
        return "." +  base64.substring(limit1 + 1, limit2);
    }
}
