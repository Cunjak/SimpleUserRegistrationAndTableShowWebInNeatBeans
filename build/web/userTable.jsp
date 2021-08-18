<%-- 
    Document   : userTable
    Created on : Mar 2, 2021, 4:39:09 PM
    Author     : ACO
--%>

<%@page import="java.util.List"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Table</title>
    </head>
    <body>
        <div id="UserTable" class="mt-5">
        <%
            if(!((List<User>)request.getAttribute("userList")).isEmpty())
            {
        %>
        
            <table class="mx-auto table table-striped table-responsive-sm">
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>Country</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (User user : (List<User>) request.getAttribute("userList")) {
                    %>
                    <tr>
                        <td><%= user.getFirstName() + " " + user.getLastName()%></td>
                        <td><%= user.getAddress()%></td>
                        <td><%= user.getCity()%></td>
                        <td><%= user.getCountry()%></td>
                        <td><%= user.getEmail()%></td>
                        <td><a class="delete" onclick="deleteFunction(this)"  data-email="<%= user.getEmail()%>" href="#">Delete</a></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
    <% } %>    
        </div>
        <script src="Content/Scripts/Delete.js" type="text/javascript"></script>
    </body>
</html>
