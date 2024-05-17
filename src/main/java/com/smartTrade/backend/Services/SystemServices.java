package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.SystemDAO;
import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.CompactNumberFormat;
import java.util.List;
import java.util.Map;

@Service
public class SystemServices {

    @Autowired
    private SystemDAO systemDAO;


    public String getTableAttributes(String tableName) {
        return systemDAO.readOne(Map.of("tableName",tableName));
    }

    public List<String> getAllTables() {
        return systemDAO.readAll();
    }

    public List<String> getFullListOfCountries(boolean emoji){
        if(emoji){
            return CountriesMethods.getCountriesListInAlphabeticalWithEmojis();
        }else{
            return CountriesMethods.getCountriesListInAlphabetical();
        }
    }

    public List<String> getCitiesFromCountry(String country,Integer cantidad){
        return CountriesMethods.getCitiesByCountry(country,cantidad);
    }

    public double getDistanceFromTwoCountries(String country1, String country2){
        return CountriesMethods.calculateDistanceBetweenCountries(country1,country2);
    }

    public boolean twoCountriesShareBorder(String country1, String country2){
        return CountriesMethods.hasBorderWith(country1,country2);
    }

    public String getCountryCapital(String country){
        return CountriesMethods.getCapitalCity(country);
    }

    public double getDistanceFromTwoCities(String city1, String country1, String city2, String country2){
        return CountriesMethods.calculateDistanceBetweenCities(city1,country1,city2,country2);
    }

    public void insertCountryAndCityWhereMissing(){
        systemDAO.insertCountryAndCityWhereMissing();
    }




}
