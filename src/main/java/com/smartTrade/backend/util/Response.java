package com.smartTrade.backend.util;

public class Response<T> {
    private boolean success;
    private T result;

    public Response(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

}
