package com.smartTrade.backend;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class smartTradeConexion {

    private static final URI smartTradeURI = URI.create("http://13.53.197.14:8080");
    private static HttpClient client = null;


    public smartTradeConexion() {
        client = HttpClient.newHttpClient();
    }

    public HttpResponse<String> get(String path) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(smartTradeURI + path))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpResponse<String> post(String path, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(smartTradeURI + path))
                .header("Content-Type", "application/json")
                .POST(body == null ? HttpRequest.BodyPublishers.ofString(body) : HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpResponse<String> put(String path, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(smartTradeURI + path))
                .header("Content-Type", "application/json")
                .PUT(body == null ? HttpRequest.BodyPublishers.ofString(body) : HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpResponse<String> delete(String path) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(smartTradeURI + path))
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
