<%-- 
    Document   : newjsp
    Created on : Feb 27, 2021, 8:13:39 PM
    Author     : ACO
--%>

<%@page import="java.util.List"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
        <link href="Content/Css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="Content/Scripts/jquery-3.3.1.min.js" type="text/javascript"></script>
    </head>
    <body>       
        <div class="container">
            <div class="row">
                <div class="col-8 mx-auto">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col text-center">
                                    <img src="Content/Images/generaluser.png" width="150px" />
                                    <h3>User Registration</h3>
                                    <hr />
                                </div>
                            </div>
                            <form id="myForm" action="Register" method="post">
                                <div class="row mt-4 mb-4">
                                    <div class="col-md-6">
                                        <input name="FirstName"  class="form-control" style = "max-width:none" placeholder="First Name" required />
                                    </div>
                                    <div class="col-md-6">
                                        <input name="LastName"  class="form-control" style="max-width:none" placeholder="Last Name" required />
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <div class="col-md-4">
                                        <input name="Address"  class="form-control" style="max-width:none" placeholder="Address" required />
                                    </div>
                                    <div class="col-md-4">
                                        <input name="City"  class="form-control" style="max-width:none" placeholder="City" required />
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" name="Country"  list="countryList" class="form-control" style="max-width:none" placeholder="Country" required>
                                        <datalist id="countryList">
                                            <option value="China">
                                            <option value="Canada">
                                            <option value="Japan">
                                            <option value="Germany">
                                            <option value="France">
                                            <option value="United Kingdom">
                                            <option value="United States">
                                        </datalist>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <div class="col-md-6">
                                        <input name="Email"  class="form-control" style="max-width:none" placeholder="Email" required />
                                    </div>
                                    <div class="col-md-6">
                                        <input name="Password" type="password" class="form-control" style="max-width:none" placeholder="Password" required />
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <div class="col text-center">
                                        <input value="Register" type="submit" class="btn btn-primary btn-lg btn-block mt-5 mx-auto" style="width: 80% " />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="myTable">
            <jsp:include page="userTable.jsp">
                <jsp:param  name="userList" value='<%=request.getAttribute("userList")%>'></jsp:param>
            </jsp:include>
        </div>
        <script src="Content/Scripts/Registration.js" type="text/javascript"></script>
    </body>
</html>
