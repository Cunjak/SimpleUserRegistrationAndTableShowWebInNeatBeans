/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;
import Models.Country;
import Models.User;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author ACO
 */
public class XmlOperations {
      
    public static Document SaveUserWhenFileExists(User user, String xmlFilePath, String jsonFilePath, Document document, Country countryObject) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException
    {
        
        boolean isContinentFound = false;
        boolean isCountryFound = false;
        
       NodeList continents = document.getElementsByTagName("continent");
       
       for(int i =0; i < continents.getLength(); i++)
       {
           Element continent =(Element)continents.item(i);
          if(user.GetContinent(jsonFilePath) == null)
          {
              countryObject.exists = false;
              return document;
          }
           
           if(user.GetContinent(jsonFilePath).equals(continent.getAttributes().getNamedItem("name").getNodeValue()))
           {
               isContinentFound = true;
               NodeList countries = continent.getElementsByTagName("country");
               
               for(int j = 0; j < countries.getLength(); j++)
               {
                   Node country = countries.item(j);
                   
                   if(user.getCountry().equals(country.getAttributes().getNamedItem("name").getNodeValue()))
                   {
                       isCountryFound = true;
                       XmlOperations.AddElements(country, user, document);
                       break;
                   }
               }
               if(!isCountryFound)
               {
                   Element country = document.createElement("country");
                   continent.appendChild(country);

                   country.setAttribute("name", user.getCountry());

                   Comment comment = document.createComment("Users");
                   country.appendChild(comment);
                   
                   XmlOperations.AddElements(country, user, document);
                   break;
               }
           }
       }
       if(!isContinentFound)
       {
        Element continent = document.createElement("continent");
        document.getFirstChild().appendChild(continent);

        if(user.GetContinent(jsonFilePath) == null)
          {
              countryObject.exists = false;
              return document;
          }
        
        continent.setAttribute("name", user.GetContinent(jsonFilePath));

       
        Element country = document.createElement("country");
        continent.appendChild(country);
        
        country.setAttribute("name", user.getCountry());

        Comment comment = document.createComment("Users");
        country.appendChild(comment);
        
        XmlOperations.AddElements(country, user, document);
       }
        document.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        removeWhitespaces(document.getDocumentElement());
        transformer.transform(source, result);
        
        return document;
       
    }
    
    public static Document SaveUserWhenFileDoesntExist(User user, String xmlFilePath, String jsonFilePath, Document document, Country countryObject) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException
    {
       
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);

        
        Element continent = document.createElement("continent");
        rootElement.appendChild(continent);

        if(user.GetContinent(jsonFilePath) == null)
          {
              countryObject.exists = false;
              return document;
          }
        
        continent.setAttribute("name", user.GetContinent(jsonFilePath));

       
        Element country = document.createElement("country");
        continent.appendChild(country);
        
        country.setAttribute("name", user.getCountry());

        Comment comment = document.createComment("Users");
        country.appendChild(comment);
        
        XmlOperations.AddElements(country, user, document);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                    
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(xmlFilePath));

        transformer.transform(source, result);
        
        return document;
    }
    
    public static Document DeleteUser(String xmlFilePath, String targetEmail) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document document = domBuilder.parse(xmlFilePath);
        
        NodeList emails = document.getElementsByTagName("email");
        Element targetUser = document.createElement("targetUser");
        for(int i=0;i<emails.getLength();i++)
        {
            Node email = emails.item(i);
            if(email.getTextContent().equals(targetEmail))
            {
                targetUser =(Element)email.getParentNode();
                break;
            }
        }
        Node node = targetUser.getParentNode();
        node.removeChild(targetUser);
        Node parent;
        
         // Remove country if it doesnt have any more users, remove continent if it doesnt have any more countries
         while(node.getParentNode() != null)
         {
             boolean hasElement = false;
             parent = node.getParentNode();
             for(int i =0; i<node.getChildNodes().getLength(); i++)
             {
                 if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                 {
                    hasElement = true;
                    break;
                 }
                 
             }
             if(hasElement == false)
                 parent.removeChild(node);
             
             node = parent;
         }
         
         if(node.getChildNodes().getLength() == 0)
         {
             File xmlFile = new File(xmlFilePath);
             xmlFile.delete();
             return null;
         }
         
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                    
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        removeWhitespaces(document.getDocumentElement());
        transformer.transform(source, result);
        
        return document;
        
        
    }
    
    public static boolean EmailAlreadyExists(Document document, String targetEmail)
    {
        NodeList emails = document.getElementsByTagName("email");
        
        for(int i=0; i < emails.getLength(); i++)
        {
            if(emails.item(i).getTextContent().equals(targetEmail))
                return true;
        }
        return false;
    }
    
    private static void AddElements(Node country, User user, Document document)
    {
        Element userElement = document.createElement("user");
        country.appendChild(userElement);
        
        Element firstName= document.createElement("first_name");
        firstName.appendChild(document.createTextNode(user.getFirstName()));
        userElement.appendChild(firstName);

        Element lastName = document.createElement("last_name");
        lastName.appendChild(document.createTextNode(user.getLastName()));
        userElement.appendChild(lastName);

        Element address = document.createElement("address");
        address.appendChild(document.createTextNode(user.getAddress()));
        userElement.appendChild(address);
        
        Element city = document.createElement("city");
        city.appendChild(document.createTextNode(user.getCity()));
        userElement.appendChild(city);
        
        Element email = document.createElement("email");
        email.appendChild(document.createTextNode(user.getEmail()));
        userElement.appendChild(email);
        
        Element password = document.createElement("password");
        password.appendChild(document.createTextNode(user.getPassword()));
        userElement.appendChild(password);
    }
    public static void removeWhitespaces(Element element) {
    NodeList children = element.getChildNodes();
    for (int i = children.getLength() - 1; i >= 0; i--) {
        Node child = children.item(i);
        if (child instanceof Text
            && ((Text) child).getData().trim().isEmpty()) {
            element.removeChild(child);
        } else if (child instanceof Element) {
            removeWhitespaces((Element) child);
        }
    }
}
    
}
