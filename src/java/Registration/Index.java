/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registration;

import Helper.Conversion;
import Models.User;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ACO
 */
@WebServlet(name = "Index", urlPatterns = {"/Index"})
public class Index extends HttpServlet {

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String xmlFilePath = getServletContext().getRealPath("/Content/Xml_Files/User.xml");
        List<User> userList = new ArrayList<User>();
        try {
             File xmlFile = new File(xmlFilePath);
        if (xmlFile.exists()) {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
            Document document = domBuilder.parse(xmlFilePath);
            userList = Conversion.XmlToUserList(document);
        }
           
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ParserConfigurationException ex) {
            request.setAttribute("javax.servlet.error.exception", ex);
            request.getRequestDispatcher("ErrorHandler").forward(request, response);
        } catch (SAXException ex) {
            request.setAttribute("javax.servlet.error.exception", ex);
            request.getRequestDispatcher("ErrorHandler").forward(request, response);
        }
        
    }

   
}
