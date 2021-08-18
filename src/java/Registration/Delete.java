/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registration;

import Helper.Conversion;
import Helper.XmlOperations;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ACO
 */
@WebServlet(name = "Delete", urlPatterns = {"/Delete"})
public class Delete extends HttpServlet {

   

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String xmlFilePath = getServletContext().getRealPath("/Content/Xml_Files/User.xml");
        String email = request.getParameter("email");
        try {
           List<User> userList = new ArrayList<User>();
           Document document = XmlOperations.DeleteUser(xmlFilePath, email);
           if(document == null)
           {
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("userTable.jsp").forward(request, response);
                return;
           }
           userList = Conversion.XmlToUserList(document);
           request.setAttribute("userList", userList);
           request.getRequestDispatcher("userTable.jsp").forward(request, response);
           
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

   
}
}