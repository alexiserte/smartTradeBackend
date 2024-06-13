package com.smartTrade.backend.Utils;

import com.google.gson.GsonBuilder;

public class JSONMethods {

    public static String getPrettyJSON(Object body) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        String bodyString = gsonBuilder.create().toJson(body);
        return bodyString;
    }

}
