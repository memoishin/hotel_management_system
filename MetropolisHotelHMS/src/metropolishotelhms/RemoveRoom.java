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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class RemoveRoom extends DatabaseMethods{
    ObservableList<String> data;
    public void removeStaff(Stage window){
        connectDatabase();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface main = new ManagerInterface();
            main.managerMode(window);
        });
        grid.add(btnBack, 2, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        grid.add(btnLogOut, 3, 0);
        showAvailRooms();
        final ComboBox roomIDSelect = new ComboBox(data);
        roomIDSelect.setPromptText("Choose Room ID");
        grid.add(roomIDSelect, 1, 1);
        
        Label roomType = new Label("No Room Selected");
        Label roomDescription = new Label("No Room Selected");
        
        roomIDSelect.setOnAction(e -> {
            getData(roomIDSelect, roomType, roomDescription);
        });
        
        Label confirm = new Label();
        grid.add(confirm,1,5);
        
        grid.add(roomType, 1, 2);
        grid.add(roomDescription, 1, 3);
        
        Button btnSubmit = new Button("Remove This Room");
        grid.add(btnSubmit, 1, 4);
        btnSubmit.setOnAction(e -> {
            removeRoom(roomIDSelect, roomType, roomDescription, confirm);
        });
        
        Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add(RemoveRoom.class.getResource("HMStyles.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
    
    void showAvailRooms(){
        ResultSet roomDisplay = null;
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM room WHERE ROOM_AVAIL = 1;";
            System.out.println(sql);
            String []roomCodes = new String[200];
             
             roomDisplay = stmt.executeQuery(sql);
             int i = 0;
             String rcode;
             List list = new ArrayList();
             while(roomDisplay.next()){
                 rcode = roomDisplay.getString("ROOM_CODE");
                 roomCodes[i] = "R"+rcode;
                 list.add(roomCodes[i]);
                 i++;
            }
             data = FXCollections.observableArrayList(list);
         }
         catch(Exception y){
            System.out.println("Error In Displaying");
         }  
    }

    void getData(ComboBox room, Label Type, Label Descript){
        ResultSet rs = null;
        String rcode = room.getValue().toString();
        rcode = rcode.substring(1);
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM room WHERE ROOM_CODE =" + rcode + ";";  
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Type.setText("Room Type : " + rs.getString("ROOM_TYPE"));
                Descript.setText("Room Description : " + rs.getString("ROOM_DESCRIPTION"));
            }
        }
        catch(Exception e){
            System.out.println("Error 2");
        }
    }
    
    void removeRoom(ComboBox room, Label Type, Label Descript, Label Confirm){
        String rcode = room.getValue().toString();
        rcode = rcode.substring(1);
         try{
            stmt = conn.createStatement();
            String sql = "DELETE FROM room WHERE ROOM_CODE =" + rcode + ";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            Type.setText(" ");
            Descript.setText(" ");
            Confirm.setText("Room Deleted Succesfully");
        }
        catch(Exception f){
            System.out.println("Error 3");
            Confirm.setText("Error - Room  Not Deleted Succesfully");
        }
    }
}
