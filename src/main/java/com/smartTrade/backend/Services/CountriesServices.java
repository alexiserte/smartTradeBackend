package com.smartTrade.backend.Services;


import com.smartTrade.backend.DAO.CountryDAOAndServices;
import com.smartTrade.backend.DAO.SystemDAO;
import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountriesServices {

    @Autowired
    private CountryDAOAndServices countryDAO;

    public void insertCountryAndCityWhereMissing(){
        countryDAO.insertCountryAndCityWhereMissing();
    }

    public double getDistanceFromVendorToUser(String vendorNickname, String userNickname) {
        return countryDAO.getDistanceFromVendorToUser(vendorNickname, userNickname);
    }

}
