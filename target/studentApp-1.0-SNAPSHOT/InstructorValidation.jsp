<%-- 
    Document   : InstructorValidation
    Created on : 12-Sep-2021, 16:58:39
    Author     : markt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "com.mtits.studentapp.accounts.InstructorAccount" %>
<jsp:useBean id="instructor" class="com.mtits.studentapp.accounts.InstructorAccount" scope="session" >
</jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instructor validation page</title>
    </head>
    <body>
        <h1>Instructor Log In:</h1>
        <%
            //in a JSP "out" is by default the print writer which prints to a 
            //webpage 
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username.isEmpty() || password.isEmpty()) {
                %>
                Please fill in both fields
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
                <%
            }else{
                if(true){
                instructor.setLogInInfo(username, password);

                if (instructor.isInDatabase()) {
                    out.println("<table border=\"0\" style=\"background-color:\" width=\"100%\" cellpadding=\"3\" cellspacing=\"3\">");
                    out.println("<tr>");
                    out.println("<td>Welcome " + instructor.getFirstName() + " " + instructor.getLastName() + "</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><a href=\"InstructorCoursePage.jsp\">Assign Hw</a></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><a href=\"InstructorGradePage.jsp\">Assign Grade</a></td>");
                    out.println("</tr>");
                    out.println("</table>");
                            
                } else {
                    out.print("Please contact your instructor to ensure you are in the system");
                    out.println("<p><form method=\"get\" action=" + "InstructorLoginPage.jsp>");
                    out.println("<p><input type=\"submit\" value=\"Go Back\" >");
                    out.println("</form>");
                }

            }
        }
        %>


    </body>
</html>
