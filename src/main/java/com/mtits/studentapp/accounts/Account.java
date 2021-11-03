/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.accounts;

import java.sql.PreparedStatement;

/**
 *
 * @author Peter Sun
 */
public abstract class Account {
    
    protected String userName;
    protected String name;
    protected String surname;
    protected String id;
    protected String password;
    protected PreparedStatement pStmt;
    
    abstract boolean isInDatabase();
    
    
}

//accounts need to 
// have a first and last name
// have a username and password