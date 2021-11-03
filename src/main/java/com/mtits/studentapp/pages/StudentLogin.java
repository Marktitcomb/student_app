/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.pages;

import com.mtits.studentapp.accounts.StudentAccount;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author markt
 */
public class StudentLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentLoginPage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentLoginPage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<form method=\"post\" action =\"" + request.getContextPath() + "/studentservlet\">");

        out.println("Log In Here");
        out.println("<p>User Name:");
        out.print("<input type = \"text\" name = \"username\" size = \"20\">");
        out.println("<p>Password:");
        out.print("<input type = \"text\" name = \"password\" size = \"20\">");

        out.println("<p><input type=\"submit\" value=\"Confirm\" >");
        out.println("</form>");
        out.println("</body></html>");

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        /**
         * I don't think he meant "Bad design" so much as "Bad practice."
         * Generally speaking, a web application should be as stateless as
         * conceivably possible. Even though, for example, you may need to know
         * user information in order to authorize page viewing, that information
         * could be saved on the client machine in the form of a cookie and the
         * server simply validates the user information each and every time.
         *
         * That would be ideal, but you can't always count on the client being
         * able to save cookies. Furthermore, it involves validating the user in
         * a stateless fashion, which potentially involves querying information
         * from the database for a simple page request. Often times it's just
         * simpler to save such information in the session.
         */
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isEmpty() || password.isEmpty()) {
            //TODO
            // provide further checks here via TDD
            out.println("Username and password required");
        } else {
            StudentAccount student = new StudentAccount(username, password);
            //then lets see if this is within the database
            if (student.isInDatabase()) {
                //give the student obj to the session so you can refer to it
                //for the duration of the student being logged in 
                session.setAttribute("student", student);
                out.println("<table border=\"0\" style=\"background-color:\" width=\"100%\" cellpadding=\"3\" cellspacing=\"3\">");
                out.println("<tr>");
                out.println("<td>Welcome " + student.getName() + " " + student.getLastName() + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>ID: " + student.getID() + "</td>");
                out.println("</tr>");
                out.println("<td><a href=\"StudentCoursePage\">Check Courses</a> </td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <a href=\"StudentUploadHw\">Upload HW</a> </td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");

            } else {
                //this just takes you back to a form with a button to the login page
                // this... request.getContextPath() prints out "/studentApp"
                out.println("<form method=\"post\" action =\"" + request.getContextPath() + "/studentservlet\">");
                out.println(request.getContextPath());  
                out.println("Log In Here");
                out.println("<p>Cannot find user in db");
                out.println("<p>User Name:");
                out.print("<input type = \"text\" name = \"username\" size = \"20\">");
                out.println("<p>Password:");
                out.print("<input type = \"text\" name = \"password\" size = \"20\">");

                out.println("<p><input type=\"submit\" value=\"Confirm\" >");
                out.println("</form>");
                out.println("</body></html>");
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
