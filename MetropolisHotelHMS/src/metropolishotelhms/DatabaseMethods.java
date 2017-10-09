/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Moishin
 */
public class DatabaseMethods {
        
         
    protected Connection conn, conn2;
    protected Statement stmt, stmt2;

    public DatabaseMethods() {
        this.stmt = null;
        this.conn = null;
    }
        
    public void connectDatabase(){
    try {
            Class.forName("com.mysql.jdbc.Driver");
         }
         catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
         }
        try{
            String URL = "jdbc:mysql://localhost/hms";
            String USER = "root";
            String PASS = "";
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected");
        }
        catch(Exception e){
            System.out.println("Not Connected");
        }

    }
    
}
