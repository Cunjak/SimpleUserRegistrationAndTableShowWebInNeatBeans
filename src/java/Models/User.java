/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Helper.Conversion;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author ACO
 */
public class User {
    
    private String FirstName;

    public String getFirstName() {
        return FirstName;
    }
   
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    private String LastName;

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    private String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    private String City;

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    private String Country;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    private String Password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    } 
    
    public String GetContinent(String jsonFilePath) throws IOException
    {
       Map<String, String> countryContinentMap = Conversion.JsonToCountryContinent(jsonFilePath);
       
       for(Entry<String, String> entry:countryContinentMap.entrySet())
       {
           if(entry.getKey().equals(Country))
               return  entry.getValue();
       }
        return null;
    }

}
