package com.smartTrade.backend.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class CountriesMethods {

    public static List<String> getCountriesListInAlphabetical() {
        List<String> countries = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countries.add(obj.getDisplayCountry());
        }

        Collections.sort(countries);

        return countries;
    }


    public static String getRandomCountry(){
        List<String> listaDePaises = getCountriesListInAlphabetical();
        int random = (int) (Math.random() * listaDePaises.size());
        return listaDePaises.get(random);
    }


    public static void main(String[] args){
        System.out.println("Tu pedido se encuentra en tránsito actualmente. Actualmente se localiza en el siguiente país: " + getRandomCountry());
    }


}
