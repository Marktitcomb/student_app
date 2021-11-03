/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.accounts;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mtits.studentapp.database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author markt
 */
public class StudentAccount extends Account {

    //so looks like this courses thing is not in the database 
    //rather its a thing specific to the class
    private ArrayList<String> courses;
    
    public StudentAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public String getID(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    public String getLastName(){
        return this.surname;
    }
    
    public ArrayList<String> getCourses(){
        //1. you select the courses table 
        String sql = "select coursename from courses";
        
        try{
            pStmt = Database.createConnection().prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            courses = new ArrayList<>();
            //so rs contains all courses
            //now go over the created course table 
            //each course gets it's own table in the database
            while(rs.next()){
                //
                sql = "select * from " + rs.getString(1) + " where studentid = " + this.id;
                pStmt = Database.createConnection().prepareStatement(sql);
                ResultSet studentInClassRs = pStmt.executeQuery();
                
                if(studentInClassRs.next())
                    courses.add("Course ID: " + studentInClassRs.getString(1));
            }
        }catch(SQLException e){   
        }  
        return courses;
    }

    public boolean isInDatabase() {

        boolean ret = false;
        String sql = "select id, lastname, firstname from studentaccounts where username = ? and password = ?";

        Database dbObj = Database.getInstance();// you can just call getConnection() or do this
        try {
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.setString(1, this.userName);
            pStmt.setString(2, this.password);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                initializeAccount(rs);
                ret = true;
            }
        } catch (SQLException ex) {

        }
        return ret;
    }

    //getting values from the database to create a student class 
    private void initializeAccount(ResultSet rs) {
        try {
            this.id = rs.getString(1);
            this.surname = rs.getString(2);
            this.name = rs.getString(3);
        }catch(SQLException e){
            
        }

    }
}
