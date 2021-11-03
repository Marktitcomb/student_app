/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.courses;

import com.mtits.studentapp.accounts.InstructorAccount;
import com.mtits.studentapp.accounts.StudentAccount;
import com.mtits.studentapp.database.Database;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author markt
 */
@ManagedBean(name = "course")
@RequestScoped
public class Course {
    
    private PreparedStatement pStmt;
    private String name;
    private String id;
    private ArrayList<String> instructorslist;
    private ArrayList<String> studentsList;
    private ArrayList<String> selectInstructors;
    private ArrayList<String> selectStudents;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getInstructorslist() {
        return instructorslist;
    }

    public void setInstructorslist(ArrayList<String> instructorslist) {
        this.instructorslist = instructorslist;
    }

    public ArrayList<String> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<String> studentsList) {
        this.studentsList = studentsList;
    }

    public ArrayList<String> getSelectInstructors() {
        return selectInstructors;
    }

    public void setSelectInstructors(ArrayList<String> selectInstructors) {
        this.selectInstructors = selectInstructors;
    }

    public ArrayList<String> getSelectStudents() {
        return selectStudents;
    }

    public void setSelectStudents(ArrayList<String> selectStudents) {
        this.selectStudents = selectStudents;
    }
    
    public Course(){
        createInstructorsList();
        createStudentsList();
    }
    
    
    public String courseCreationProcess(){
        String ret;
        if(name.equals("")|| id.equals("")){
            ret = "CourseCreationError";
        }else if(isInDatabase()){
            ret = "CourseCreationError";
        }else{
            //creates a new course table in the database
            //this is the table connecting course student and instructor
            if(!tableExists()){
                createTable();
                insertIntoCourses();
            }

            ret = "CourseCreatedPage";
        }
        return ret;
    }
    
    private void createTable(){
        
        String stmt = "create table " + name + " (id varchar(4), instructorid varchar(4), studentid varchar(4));";
        
        try{
            pStmt = Database.createConnection().prepareStatement(stmt);
            pStmt.executeUpdate();

            createHwFolder();
            insertDataIntoTable();
        }catch(SQLException e){}
    }
    
    private void insertIntoCourses(){
        String sql = "insert into courses (id, coursename, numHw) values (?,?,?)";
        try{
            pStmt = Database.createConnection().prepareStatement(sql);
            pStmt.setString(1, this.id);
            pStmt.setString(2, this.name);
            pStmt.setInt(3, 0);
            pStmt.executeUpdate();
                    
        }catch(SQLException e){}
    }
    
    private void createHwFolder(){
        File dir = new File("C:\\Users\\" + System.getProperty("user.name") + 
                "\\netBeanProjects\\studentApp\\src\\HomeworkAssignment\\" + name);
        dir.mkdir();
    }
    
    private boolean isInDatabase(){
        String sql = "Select * from course;";
        try{
            pStmt = Database.createConnection().prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){}
        return false;
    }
    
    private final void insertDataIntoTable(){
        String stmt =  "insert into " + this.name + " (id, instructorid) values (?,?);";
        try{
            pStmt = Database.createConnection().prepareStatement(stmt);
            for(int i = 0; i < this.selectInstructors.size() ; i++){
                pStmt.setString(1, String.valueOf(i+1));
                pStmt.setString(2, selectInstructors.get(i).substring(0, 4));
                pStmt.executeUpdate();
            }
            stmt =  "insert into " + this.name + " (id, studentid) values (?,?);";
            pStmt = Database.createConnection().prepareStatement(stmt);
            for(int i = 0; i < this.selectStudents.size() ; i++){
                pStmt.setString(1, String.valueOf(i+1));
                pStmt.setString(2, selectStudents.get(i).substring(0, 4));
                pStmt.executeUpdate();
            }
        }catch(SQLException e){}
        
    }
    
    private final void createInstructorsList(){
        String stmt = "select id, firstname, lastname from instructoraccounts;";
        try{
            pStmt = Database.createConnection().prepareStatement(stmt);
            ResultSet rs = pStmt.executeQuery();
            instructorslist = new ArrayList<>();
            while(rs.next()){
                instructorslist.add(rs.getString(1) + "_" + rs.getString(2) + "_" + rs.getString(3));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private final void createStudentsList(){
        String stmt = "select id, firstname, lastname from studentaccounts;";
        try{
            pStmt = Database.createConnection().prepareStatement(stmt);
            ResultSet rs = pStmt.executeQuery();
            studentsList = new ArrayList<>();
            while(rs.next()){
                studentsList.add(rs.getString(1) + "_" + rs.getString(2) + "_" + rs.getString(3));
            }
        }catch(SQLException e){}
    }
    
        private boolean tableExists() {
        boolean ret = false;

        String sql = "select * from courses where id = " + id;
        try {
            pStmt = Database.createConnection().prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();
            studentsList = new ArrayList<String>();
            if (rs.next()) {
                ret = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
