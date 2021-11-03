/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtits.studentapp.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author markt
 */
public class Database {
    //singleton uses a static instance so IT WONT BE VALID FOR GARBAGE COLLECTION 
    
    private final static String className = "com.mysql.cj.jdbc.Driver";
    private final static String url = "jdbc:mysql://localhost:3306/student_management";
    private final static String password = "Guitar0903";
    private final static String user = "root";
    private static Connection connection;
    
    private static Database dbInstance;
    
    private Database(){
    }
    //you dont actually need this, its just demonstrating how a singleton works 
    public static Database getInstance(){
        if(dbInstance == null)
            dbInstance = new Database();
        return dbInstance; 
    }
    
    public static Connection createConnection(){
        if(connection == null){
            try{
                Class.forName(className);//com.mysql.jdbc.Driver is not in the class path
                connection = DriverManager.getConnection(url, user, password);
            }catch(ClassNotFoundException e){
                System.out.println("Class Not found");
            }
            catch(SQLException e){
                System.out.println("SQL Exception");
            }
            
        }
        //so here connection is defineed and the program remembers this 
        //so you visit the class once and then this stays in memory
        //the instance of database is also stored in instance
        //classses dont just disappear they're in memory
        //objects can but this static class 
        return connection; 
    }
    
}
