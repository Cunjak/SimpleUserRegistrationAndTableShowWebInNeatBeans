/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Models.CountryContinent;
import Models.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ACO
 */
public class Conversion {

    public static Map<String, String> JsonToCountryContinent(String jsonFilePath) throws IOException {
        File jsonFile = new File(jsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<CountryContinent> countryContinentList = objectMapper.readValue(jsonFile, new TypeReference<List<CountryContinent>>() {
        });

        String[] parts;
        Map<String, String> countryContinentMap = new HashMap<String, String>();
        for (CountryContinent countryContinent : countryContinentList) {
            parts = countryContinent.getCountry_Name().split(",");
            countryContinentMap.put(parts[0], countryContinent.getContinent_Name());
        }

        return countryContinentMap;

    }

    public static List<User> XmlToUserList(Document document) throws ParserConfigurationException, SAXException, IOException {
        
        List<User> userList = new ArrayList<User>();

        NodeList continents = document.getElementsByTagName("continent");

            for (int i = 0; i < continents.getLength(); i++) {
                Element continentElement = (Element) continents.item(i);
                NodeList countries = continentElement.getElementsByTagName("country");

                for (int j = 0; j < countries.getLength(); j++) {
                    Element countryElement = (Element) countries.item(j);
                    NodeList users = countryElement.getElementsByTagName("user");

                    for (int k = 0; k < users.getLength(); k++) {
                        Element userElement = (Element) users.item(k);
                        User user = new User();
                        user.setFirstName(userElement.getElementsByTagName("first_name").item(0).getTextContent());
                        user.setLastName(userElement.getElementsByTagName("last_name").item(0).getTextContent());
                        user.setAddress(userElement.getElementsByTagName("address").item(0).getTextContent());
                        user.setCity(userElement.getElementsByTagName("city").item(0).getTextContent());
                        user.setCountry(countryElement.getAttribute("name"));
                        user.setEmail(userElement.getElementsByTagName("email").item(0).getTextContent());
                        user.setPassword(userElement.getElementsByTagName("password").item(0).getTextContent());
                        userList.add(user);
                    }
                }

            }
        
        return userList;
    }
}
