/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registration;

import Helper.Conversion;

import Helper.XmlOperations;
import Models.Country;
import Models.User;
import Models.UserListValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ACO
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User user = new User();
            List<User> userList = new ArrayList<User>();

            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            UserListValidation userListValidation = new UserListValidation();
            userListValidation.userList = userList;
            userListValidation.validation = "";

            String xmlFilePath = getServletContext().getRealPath("/Content/Xml_Files/User.xml");
            String jsonFilePath = getServletContext().getRealPath("/Content/Json_Files/Countries.json");

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
            Document document = null;

            Country country = new Country();
            country.exists = true;

            boolean fileExists = false;
            File xmlFile = new File(xmlFilePath);

            if (xmlFile.exists()) {
                document = domBuilder.parse(xmlFilePath);
                fileExists = true;
            } else {
                document = domBuilder.newDocument();
            }

            user.setFirstName(request.getParameter("FirstName"));
            if (user.getFirstName() == null || user.getFirstName().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "FirstNameEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setLastName(request.getParameter("LastName"));
            if (user.getLastName() == null || user.getLastName().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "LastNameEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setAddress(request.getParameter("Address"));
            if (user.getAddress() == null || user.getAddress().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "AddressEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setCity(request.getParameter("City"));
            if (user.getCity() == null || user.getCity().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "CityEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setCountry(request.getParameter("Country"));
            if (user.getCountry() == null || user.getCountry().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "CountryEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setEmail(request.getParameter("Email"));
            if (user.getEmail() == null || user.getEmail().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "EmailEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }
            user.setPassword(request.getParameter("Password"));
            if (user.getPassword() == null || user.getPassword().equals("")) {
                userList = Conversion.XmlToUserList(document);

                userListValidation.userList = userList;
                userListValidation.validation = "PasswordEmpty";

                String json = objectMapper.writeValueAsString(userListValidation);
                response.getWriter().write(json);
                return;
            }

            if (fileExists) {
                if (XmlOperations.EmailAlreadyExists(document, user.getEmail())) {
                    userList = Conversion.XmlToUserList(document);

                    userListValidation.userList = userList;
                    userListValidation.validation = "EmailExists";

                    String json = objectMapper.writeValueAsString(userListValidation);
                    response.getWriter().write(json);
                    return;

                }
                document = XmlOperations.SaveUserWhenFileExists(user, xmlFilePath, jsonFilePath, document, country);
            } else {
                document = XmlOperations.SaveUserWhenFileDoesntExist(user, xmlFilePath, jsonFilePath, document, country);
            }

            if (country.exists == false) {
                userListValidation.validation = "WrongCountry";
            }

            userList = Conversion.XmlToUserList(document);
            userListValidation.userList = userList;
            String json = objectMapper.writeValueAsString(userListValidation);
            response.getWriter().write(json);

        } catch (ParserConfigurationException ex) {
            request.setAttribute("javax.servlet.error.exception", ex);
            request.getRequestDispatcher("ErrorHandler").forward(request, response);
        } catch (TransformerException ex) {
            request.setAttribute("javax.servlet.error.exception", ex);
            request.getRequestDispatcher("ErrorHandler").forward(request, response);
        } catch (SAXException ex) {
            request.setAttribute("javax.servlet.error.exception", ex);
            request.getRequestDispatcher("ErrorHandler").forward(request, response);
        }

    }

}
