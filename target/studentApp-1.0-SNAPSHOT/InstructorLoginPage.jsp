<%-- 
    Document   : InstructorLoginPage
    Created on : 12-Sep-2021, 14:59:56
    Author     : markt
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instructor Log in</title>
    </head>
    <body>
        <h1>Instructor Log In:</h1>
        <!-- sent via a post methods -->
        <form method="post" action = "InstructorValidation.jsp">
            <table border="0" width="300" cellpadding="3" cellspacing="3">
                <tr>
                    <td>Username:</td>
                    <td><input type = "text" name = "username" size = "20"> </br></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type = "text" name = "password" size = "20"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Confirm" ></td>
                </tr>
            </table>
        </form>
    </body>
</html>
