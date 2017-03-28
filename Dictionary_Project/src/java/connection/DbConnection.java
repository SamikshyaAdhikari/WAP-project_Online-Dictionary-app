package connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chetan
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    static Connection conn;

    public static Connection connect() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/entries";
            String username = "root";
            String password = "root";

            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return conn;
        }

    }
    public static  void disconnect(){
        try{
         conn.close();
        }
        catch(Exception e){
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
