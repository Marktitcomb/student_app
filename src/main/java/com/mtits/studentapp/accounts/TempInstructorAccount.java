/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.accounts;

import com.mtits.studentapp.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author markt
 */
@ManagedBean(name = "TempInstructorAccount")
@SessionScoped
public class TempInstructorAccount {

    private String id;
    private String lastName;
    private String firstName;
    private String userName;
    private String userNameConfirm;
    private String password;
    private String passwordConfirm;
    private PreparedStatement pstmt;

    public TempInstructorAccount() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNameConfirm() {
        return userNameConfirm;
    }

    public void setUserNameConfirm(String userNameConfirm) {
        this.userNameConfirm = userNameConfirm;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    public String registerInstructor(){
        String respondingPage;
        if(!passwordConfirm.equals(password) || 
                !userName.equals(userNameConfirm)){
            respondingPage = "InstructorCreationErrorPage";
        }else if(isInDatabase()){
            respondingPage = "InstructorAlreadyExistsPage";
        }else{
            respondingPage = "InstructorCreatedPage";
        }
        return respondingPage;
    } 
    
    
    public boolean isInDatabase(){
        String stmt = "select * from instructoraccounts where "
                + "username = ? and password = ?";
        try{
            pstmt = Database.createConnection().prepareStatement(stmt);
            pstmt.setString(1, this.userName);
            pstmt.setString(2, this.password);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                return true;
            }         
        }catch(SQLException e){    
        }
        return false;
    }
    
    public void insertIntoDatabase(){
        String stmt = "insert into instructoraccounts (id, lastname, firstname, username, password) values (?,?,?,?,?);";
        
       try{
           pstmt = Database.createConnection().prepareStatement(stmt);
           pstmt.setString(1, this.id);
           pstmt.setString(2, this.lastName);
           pstmt.setString(3, this.firstName);
           pstmt.setString(4, this.userName);
           pstmt.setString(5, this.password);
           pstmt.executeUpdate();
       }catch(SQLException e){}
    }

}
