package com.smartTrade.backend.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class CountriesMethods {
    public static void main(String[] args) {
        getCountriesListInAlphabetical();
    }

    public static void getCountriesListInAlphabetical() {
        List<String> countries = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countries.add(obj.getDisplayCountry());
        }

        Collections.sort(countries);
       
        ListIterator<String> crunchifyListIterator = countries.listIterator();

        while (crunchifyListIterator.hasNext()) System.out.println(crunchifyListIterator.next());
    }
}
