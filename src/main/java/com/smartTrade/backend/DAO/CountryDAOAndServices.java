package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAOAndServices {

    private JdbcTemplate database;

    public CountryDAOAndServices(JdbcTemplate database) {
        this.database = database;
    }

    public void insertCountryAndCityWhereMissing(){
        // Obtener los IDs de los usuarios que tienen país o ciudad nulos
        List<Integer> ids = database.queryForList("SELECT id FROM Usuario WHERE pais = '' OR ciudad = ''", Integer.class);

        for (int id : ids) {
            // Obtener un país y ciudad aleatorios
            Pair<String, String> countryAndCity = CountriesMethods.getRandomCityAndCountry();

            // Actualizar el usuario con el país y ciudad obtenidos
            database.update("UPDATE Usuario SET pais = ?, ciudad = ? WHERE id = ?", countryAndCity.getSecond(), countryAndCity.getFirst(), id);
        }
    }

    public static Pair<String,String> getRandCountryAndCity(){
        return CountriesMethods.getRandomCityAndCountry();
    }

    public static Pair<Double,Double> getRandomCityCoordinates(){
        Pair<String,String> cityAndCountry = getRandCountryAndCity();
        return CountriesMethods.getCityCoordinates(cityAndCountry.getFirst(), cityAndCountry.getSecond());
    }


    public Pair<String,String> getCountryAndCityFromUser(String nickname){
        String ciudad = database.queryForObject("SELECT ciudad FROM Usuario WHERE nickname = ?", String.class, nickname);
        String pais = database.queryForObject("SELECT pais FROM Usuario WHERE nickname = ?", String.class, nickname);
        return Pair.of(ciudad, pais);
    }


    public double getDistanceFromVendorToUser(String vendorNickname, String userNickname) {
        Pair<String, String> vendorLocation = database.queryForObject("SELECT ciudad, pais FROM Usuario WHERE nickname = ?", Pair.class, vendorNickname);
        Pair<String, String> userLocation = database.queryForObject("SELECT ciudad, pais FROM Usuario WHERE nickname = ?", Pair.class, userNickname);
        return CountriesMethods.calculateDistanceBetweenCities(vendorLocation.getFirst(), vendorLocation.getSecond(), userLocation.getFirst(), userLocation.getSecond());
    }
}
