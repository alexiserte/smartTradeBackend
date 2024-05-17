package com.smartTrade.backend.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

public class CountriesMethods {

    private static final String GEONAMES_USERNAME = "smarttrade";
    private static final double RADIUS_OF_EARTH_KM = 6371;


    public static List<String> getCitiesByCountry(String countryCode) {
        String apiUrl = String.format("http://api.geonames.org/searchJSON?country=%s&featureClass=P&maxRows=1000&username=%s", countryCode, GEONAMES_USERNAME);
        String response = makeARequest(apiUrl);
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> citiesList = new ArrayList<>();
        List<String> cityNames = new ArrayList<>();

        try {
            Map<String, Object> responseMap = mapper.readValue(response, Map.class);
            List<Map<String, Object>> geonames = (List<Map<String, Object>>) responseMap.get("geonames");

            // Añadir todas las ciudades a la lista junto con su población
            for (Map<String, Object> city : geonames) {
                citiesList.add(city);
            }

            // Ordenar la lista de ciudades por población en orden descendente
            citiesList.sort(Comparator.comparingInt((Map<String, Object> city) -> (Integer) city.get("population")).reversed());

            // Extraer los nombres de las ciudades ordenadas
            for (Map<String, Object> city : citiesList) {
                cityNames.add((String) city.get("toponymName"));
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityNames;
    }


    public static Map<String, Pair<String, String>> getCountriesAndCodesSpanish() {
        String[] locales = Locale.getISOCountries();
        Map<String, Pair<String, String>> countries = new TreeMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("es").setRegion(countryCode).build();
            String countryName = obj.getDisplayCountry();
            String alpha2Code = obj.getCountry();
            String alpha3Code = new Locale("", countryCode).getISO3Country();
            Pair<String, String> codesPair = Pair.of(alpha2Code, alpha3Code);
            countries.put(countryName, codesPair);
        }
        return countries;
    }

    public static Map<String, Pair<String, String>> getCountriesAndCodesEnglish() {
        String[] locales = Locale.getISOCountries();
        Map<String, Pair<String, String>> countries = new TreeMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("en").setRegion(countryCode).build();
            String countryName = obj.getDisplayCountry(Locale.ENGLISH);
            String alpha2Code = obj.getCountry();
            String alpha3Code = new Locale("", countryCode).getISO3Country();
            Pair<String, String> codesPair = Pair.of(alpha2Code, alpha3Code);
            countries.put(countryName, codesPair);
        }
        return countries;
    }

    public static List<String> getCountriesListInAlphabetical() {
        List<String> countries = new ArrayList<>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("EN").setRegion(countryCode).build();
            countries.add(obj.getDisplayCountry());
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        return countries;
    }

    public static List<String> getCountriesListInAlphabeticalWithEmojis() {
        List<String> countries = new ArrayList<>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("EN").setRegion(countryCode).build();
            String countryName = obj.getDisplayCountry();
            String flagEmoji = getFlagEmoji(countryCode);
            countries.add(countryName + " " + flagEmoji);
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        return countries;
    }

    public static String getRandomCountry(){
        List<String> listaDePaises = getCountriesListInAlphabetical();
        int random = (int) (Math.random() * listaDePaises.size());
        return listaDePaises.get(random);
    }

    public static double calculateDistanceBetweenCountries(String country1, String country2){
        Pair<Double, Double> country1Coordinates = getCountryCoordinates(country1);
        Pair<Double, Double> country2Coordinates = getCountryCoordinates(country2);
        return calculateDistance(country1Coordinates, country2Coordinates);
    }

    public static double calculateDistanceBetweenCities(String city1, String country1, String city2, String country2){
        city1 = StringComparison.quitarAcentos(city1.replaceAll(" ", "%20"));
        city2 = StringComparison.quitarAcentos(city2.replaceAll(" ", "%20"));
        country1 = StringComparison.quitarAcentos(country1.replaceAll(" ", "%20"));
        country2 = StringComparison.quitarAcentos(country2.replaceAll(" ", "%20"));
        Pair<Double, Double> city1Coordinates = getCityCoordinates(city1, country1);
        Pair<Double, Double> city2Coordinates = getCityCoordinates(city2, country2);
        return calculateDistance(city1Coordinates, city2Coordinates);
    }


    public static Pair<Double, Double> getCountryCoordinates(String country) {
        String response = requestToOpenMapsAPI(country);
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> responseList;
        String lat = null;
        String lon = null;
        try {
            responseList = mapper.readValue(response, List.class);
            HashMap<String, Object> firstElement = (HashMap<String, Object>) responseList.get(0);
            lat = firstElement.get("lat").toString();
            lon = firstElement.get("lon").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Country not found");
        }

        return Pair.of(Double.parseDouble(lat), Double.parseDouble(lon));
    }

    private static String requestToOpenMapsAPI(String countryCode) {
        String country = countryCode.replaceAll(" ", "%20");
        country = StringComparison.quitarAcentos(country);
        String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + country;
        return makeARequest(apiUrl);
    }


    private static double calculateDistance(Pair<Double, Double> countryOneCordinates, Pair<Double, Double> countryTwoCordinates) {

        double lat1Rad = Math.toRadians(countryOneCordinates.getFirst());
        double lon1Rad = Math.toRadians(countryOneCordinates.getSecond());
        double lat2Rad = Math.toRadians(countryTwoCordinates.getFirst());
        double lon2Rad = Math.toRadians(countryTwoCordinates.getSecond());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = RADIUS_OF_EARTH_KM * c;

        return Math.round(distance * 100.0) / 100.0;
    }



    private static boolean hasBorderWith(String country1, String country2) {
        Map<String, Pair<String, String>> countriesInSpanish = getCountriesAndCodesSpanish();
        Pair<String, String> country1Codes = countriesInSpanish.get(country1);
        Pair<String, String> country2Codes = countriesInSpanish.get(country2);

        String country1NameInEnglish = new Locale("", country1Codes.getFirst()).getDisplayCountry(Locale.ENGLISH);

        String apiUrl = "https://restcountries.com/v3.1/name/" + StringComparison.quitarAcentos(country1NameInEnglish.replaceAll(" ", "%20")) + "?fields=borders";
        String response = makeARequest(apiUrl);
        System.out.println(response);

        List<HashMap<String,Object>> countryData;
        List<String> countryBorders;
        try{
            countryData = new ObjectMapper().readValue(response, List.class);
            countryBorders = (List<String>) countryData.get(0).get("borders");
            return countryBorders.contains(country2Codes.getSecond());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private static String getFlagEmoji(String countryCode) {
        int firstChar = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondChar = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstChar)) + new String(Character.toChars(secondChar));
    }

    private static String makeARequest(String urlBase){
        URL url;
        try {
            url = new URL(urlBase);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static Pair<Double, Double> getCityCoordinates(String city, String country) {
        String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + city + "," + country;
        String response = makeARequest(apiUrl);
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> responseList;
        String lat = null;
        String lon = null;
        try {
            responseList = mapper.readValue(response, List.class);
            HashMap<String, Object> firstElement = (HashMap<String, Object>) responseList.get(0);
            lat = firstElement.get("lat").toString();
            lon = firstElement.get("lon").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException("City not found");
        }

        return Pair.of(Double.parseDouble(lat), Double.parseDouble(lon));
    }
}
