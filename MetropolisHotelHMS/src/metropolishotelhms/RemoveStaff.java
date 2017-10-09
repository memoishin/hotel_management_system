/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class RemoveStaff  extends DatabaseMethods{
    ObservableList<String> data; 
    public void removeStaff(Stage window){
        connectDatabase();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        grid.add(btnLogOut, 3, 0);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface man = new ManagerInterface();
            man.managerMode(window);
        });
        grid.add(btnBack, 2, 0);
        
        Label staffName = new Label("No staff Selected");
        Label staffInfo = new Label("No staff Selected");
       // Label staffAddress = new Label("No staff Selected");
        
        showAvailStaff();
        final ComboBox staffIDSelect = new ComboBox(data);
        staffIDSelect.setPromptText("Choose Staff ID");
        grid.add(staffIDSelect, 1, 1);
        
        staffIDSelect.setOnAction(e -> {
            getData(staffIDSelect, staffName, staffInfo);
        });
        
        Label confirm = new Label();
        
        
        
        grid.add(staffName, 1, 2);
        grid.add(staffInfo, 1, 3);
        grid.add(confirm, 1, 6);
        
        Button btnSubmit = new Button("Remove This Staff");
        grid.add(btnSubmit, 1, 5);
        btnSubmit.setOnAction(e -> {
            removeRoom(staffIDSelect, staffName, staffInfo, confirm);
        });
        Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add(RemoveStaff.class.getResource("HMStyles.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
    
    void showAvailStaff(){
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM staff;";
            System.out.println(sql);
             rs = stmt.executeQuery(sql);
             String rcode;
             List list = new ArrayList();
             while(rs.next()){
                 rcode = rs.getString("STAFF_ID");
                 list.add(rcode);
            }
             data = FXCollections.observableArrayList(list);
         }
         catch(Exception y){
            System.out.println("Error In Displaying");
         }  
    }

    void getData(ComboBox staff, Label Type, Label Descript){
        ResultSet rs = null;
        String scode = staff.getValue().toString();
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM staff WHERE STAFF_ID ='" + scode + "';";  
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Type.setText("Staff Name : " + rs.getString("STAFF_FNAME") +" " + rs.getString("STAFF_LNAME"));
                Descript.setText("Staff Department : " + rs.getString("STAFF_SECTION") + " " + "\nStaff Country : " + rs.getString("STAFF_COUNTRY"));
            }
        }
        catch(Exception e){
            System.out.println("Error 2");
        }
    }
    
    void removeRoom(ComboBox staff, Label Type, Label Descript, Label Confirm){
        String scode = staff.getValue().toString();
         try{
            stmt = conn.createStatement();
            String sql = "DELETE FROM staff WHERE STAFF_ID ='" + scode + "';";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            Type.setText(" ");
            Descript.setText(" ");
            Confirm.setText("Staff Deleted Succesfully");
        }
        catch(Exception f){
            System.out.println("Error 3");
            Confirm.setText("Error - Staff  Not Deleted Succesfully");
        }
    }
    
}
