/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.accounts;

import com.mtits.studentapp.database.Database;
import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author markt
 */
@ManagedBean(name = "adminaccount")
@SessionScoped
public class AdministratorAccount{

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String id;
    private PreparedStatement pstmt;

    public AdministratorAccount() {
        
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String validateAdministrator() {
        String ret = "";
        if (userName.equals("") || password.equals("")) {
            ret = "AdministratorInvalidEntry";
        } else {
            if (isInDatabase()) {
                ret = "AdministratorValidatePage";
            } else {
                ret = "AdministratorNotinDb";
            }
        }
        return ret;
    }

    public boolean isInDatabase() {
        boolean ret = false;
        String sql = "select id, lastname, firstname from "
                + "administratoraccounts where username = ? and password = ?";
        try{
           pstmt = Database.createConnection().prepareStatement(sql); 
           pstmt.setString(1, this.userName);
           pstmt.setString(2, this.password);
           
           ResultSet result = pstmt.executeQuery();
           if(result.next()){
               initialiseAccount(result);
               ret = true;
           }
        }catch(SQLException e){
            
        }    
        return ret;
    }
       
    private void initialiseAccount(ResultSet result){
        try {
            this.id = result.getString(1);
            this.lastName = result.getString(2);
            this.firstName = result.getString(3);
        } catch (SQLException ex) {
            Logger.getLogger(AdministratorAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
