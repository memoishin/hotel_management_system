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
public class DeleteInventory  extends DatabaseMethods{
    ObservableList<String> data; 
    public void removeItem(Stage window){
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
        
        Label Name = new Label("No Item Selected");
        Label Info = new Label("No Item Selected");
       // Label staffAddress = new Label("No staff Selected");
        
        showAvailStaff();
        final ComboBox item = new ComboBox(data);
        item.setPromptText("Choose Item ID");
        grid.add(item, 1, 1);
        
        item.setOnAction(e -> {
            getData(item, Name, Info);
        });
        
        Label confirm = new Label();

        grid.add(Name, 1, 2);
        grid.add(Info, 1, 3);
        grid.add(confirm, 1, 6);
        
        Button btnSubmit = new Button("Remove This Item");
        grid.add(btnSubmit, 1, 5);
        btnSubmit.setOnAction(e -> {
            removeRoom(item, Name, Info, confirm);
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
            String sql = "SELECT * FROM inventory;";
            System.out.println(sql);
             rs = stmt.executeQuery(sql);
             String rcode;
             List list = new ArrayList();
             while(rs.next()){
                 rcode = rs.getString("ItemCODE");
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
            String sql = "SELECT * FROM inventory WHERE ItemCODE ='" + scode + "';";  
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Type.setText("Item Name : " + rs.getString("ItemNAME"));
                Descript.setText("Item Price : " + rs.getString("ItemPRICE"));
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
            Confirm.setText("Item Deleted Succesfully");
        }
        catch(Exception f){
            System.out.println("Error 3");
            Confirm.setText("Error - Item  Not Deleted Succesfully");
        }
    }
    
}
