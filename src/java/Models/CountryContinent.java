/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ACO
 */
public class CountryContinent {
    
    @JsonProperty("Country_Name")
    private String Country_Name;

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String Country_Name) {
        this.Country_Name = Country_Name;
    }

    @JsonProperty("Continent_Name")
    private String Continent_Name;

    public String getContinent_Name() {
        return Continent_Name;
    }

    public void setContinent_Name(String Continent_Name) {
        this.Continent_Name = Continent_Name;
    }


}
