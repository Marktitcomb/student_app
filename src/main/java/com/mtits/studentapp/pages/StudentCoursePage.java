/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.pages;

import com.mtits.studentapp.accounts.StudentAccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author markt
 */
public class StudentCoursePage extends HttpServlet {

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
            out.println("<title>Servlet StudentCoursePage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentCoursePage at " + request.getContextPath() + "</h1>");
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
        //get your required information from the logged in session 
        HttpSession session = request.getSession();

        StudentAccount account = (StudentAccount) session.getAttribute("student");
        ArrayList<String> courses = account.getCourses();
        //ArrayList<String> courses = new ArrayList<>();

        if (courses.size() > 0) {
            out.println("<form method = \"post\" action = " + request.getContextPath() + "/StudentCoursePage\">");
            out.println("<p> Courses currently registered in");
            out.print("<select name = \"courseRegIn\" size = \"1\">");
            for (int i = 0; i < courses.size(); i++) {
                out.print("<option value = " + courses.get(i) + ">" + courses.get(i) + "</option>");
            }
            out.print("</select>");

            out.println("<p><input type=\"submit\" value=\"Confirm\" >");
            out.println("</form>");
            out.println("</body></html>");
        }else{
           out.println("<p> this student is not enrolled in any courses");
        }

    }

    /**
     * Loads a page for that particular course telling the student if they have
     * any homework and what grade they got for it 
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
