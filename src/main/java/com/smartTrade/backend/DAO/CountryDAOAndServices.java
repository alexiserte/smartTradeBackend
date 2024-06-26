package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDAOAndServices {

    private JdbcTemplate database;

    public CountryDAOAndServices(JdbcTemplate database) {
        this.database = database;
    }

    public void insertCountryAndCityWhereMissing() {

        List<Integer> ids = database.queryForList("SELECT id FROM Usuario WHERE pais = '' OR ciudad = ''", Integer.class);

        for (int id : ids) {
            Pair<String, String> countryAndCity = CountriesMethods.getRandomCityAndCountry();
            database.update("UPDATE Usuario SET pais = ?, ciudad = ? WHERE id = ?", countryAndCity.getSecond(), countryAndCity.getFirst(), id);
        }
    }

    public static Pair<Double, Double> getDefaultCoordinates() {
        return Pair.of(39.48254552490556, -0.3467672635500624);
    }


    public Pair<String, String> getCountryAndCityFromUser(String nickname) {
        String ciudad = database.queryForObject("SELECT ciudad FROM Usuario WHERE nickname = ?", String.class, nickname);
        String pais = database.queryForObject("SELECT pais FROM Usuario WHERE nickname = ?", String.class, nickname);
        return Pair.of(ciudad, pais);
    }

    public List<String> getListaDePaises() {
        List<String> paises = database.queryForList("SELECT DISTINCT pais FROM Pais_Ciudad", String.class);
        List<String> res = new ArrayList<>();
        for (String pais : paises) {
            if (!pais.equals("")) {
                res.add(pais + " " + CountriesMethods.getFlagEmojiFromCountryName(pais));
            }
        }
        return res;
    }

    public List<String> getListaDeCiudades(String pais) {
        List<String> ciudades = database.queryForList("SELECT ciudad FROM Pais_Ciudad WHERE pais = ?", String.class, pais);
        return ciudades;
    }

    public double getDistanceFromVendorToUser(String vendorNickname, String userNickname) {
        Pair<String, String> vendorLocation = database.queryForObject("SELECT ciudad, pais FROM Usuario WHERE nickname = ?", Pair.class, vendorNickname);
        Pair<String, String> userLocation = database.queryForObject("SELECT ciudad, pais FROM Usuario WHERE nickname = ?", Pair.class, userNickname);
        return CountriesMethods.calculateDistanceBetweenCities(vendorLocation.getFirst(), vendorLocation.getSecond(), userLocation.getFirst(), userLocation.getSecond());
    }

    public void saveValidCountriesAndCities() {
        Map<String, List<String>> validCountriesAndCities = new HashMap<>();
        List<String> countries = CountriesMethods.getCountriesListInAlphabetical();

        for (String country : countries) {
            List<String> validCities = new ArrayList<>();
            List<String> cities = CountriesMethods.getCitiesByCountry(country, 100);

            for (String city : cities) {
                try {
                    CountriesMethods.getCityCoordinates(city, country);
                    validCities.add(city);
                    database.update("INSERT INTO Pais_Ciudad(pais,ciudad) VALUES (?,?)", country, city);
                } catch (RuntimeException e) {
                    continue;
                }
            }
            validCountriesAndCities.put(country, validCities);
        }
    }

}
