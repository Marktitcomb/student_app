/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.accounts;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.mtits.studentapp.database.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author markt
 */
// so this class is used to test how valid a student account is
//checks... is it already in the database 
//
@ManagedBean(name = "TempStudentAccount")
@SessionScoped
public class TempStudentAccount {

    private String userName;
    private String userNameConfirm;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String ID;
    private PreparedStatement pstmt; 

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getUsernameConfirm() {
        return userNameConfirm;
    }

    public void setUsernameConfirm(String userNameConfirm) {
        this.userNameConfirm = userNameConfirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public TempStudentAccount() {

    }

    //registers the student in the db 
    public String registerProcess() {
        String respondingPage;
        if(!userName.equals(userNameConfirm) || !password.equals(passwordConfirm)) {
            respondingPage = "StudentCreationErrorPage";
        }else if(isInDatabase()){
            respondingPage = "StudentAlreadyExistsPage"; 
        }else{
            insertIntoDb();
            respondingPage = "StudentCreatedPage";
        }
        return respondingPage;    
    }

    //
    //checks if the student is already in the db 
    private boolean isInDatabase() {
        boolean ret = false;

        String stmt = "select id, lastname, firstname from studentaccounts where username = ? and password = ?;";
        
        try {
            pstmt = Database.createConnection().prepareStatement(stmt);
            pstmt.setString(1, this.userName);
            pstmt.setString(2, this.password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                ret =  true;
            }
        } catch (SQLException ex) {      
        }
        return ret;
    }

    //if it passed the reg process then insert the student into the db 
    private void insertIntoDb() {
        String stmt = "insert into studentaccounts (id, lastname, firstname, username, password) values (?,?,?,?,?)";
        try{
            pstmt = Database.createConnection().prepareStatement(stmt);
            pstmt.setString(1, this.ID);
            pstmt.setString(2, this.lastName);
            pstmt.setString(3, this.firstName);
            pstmt.setString(4, this.userName);
            pstmt.setString(5, this.password);
            pstmt.executeUpdate();
        }catch(SQLException e){}
    }

}
