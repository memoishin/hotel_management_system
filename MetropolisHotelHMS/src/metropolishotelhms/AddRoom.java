/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class AddRoom extends DatabaseMethods {
    
    public void addRoomMode(Stage window){
        connectDatabase();
        GridPane addroomGrid = new GridPane(); 
        addroomGrid.setAlignment(Pos.CENTER);
        addroomGrid.setHgap(10);
        addroomGrid.setVgap(10);
        addroomGrid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface man = new ManagerInterface();
            man.managerMode(window);
        });
        
        addroomGrid.add(btnLogOut, 3, 0);
        addroomGrid.add(btnBack, 2, 0);
        Scene addRoomScene = new Scene(addroomGrid, 640, 480);
        
        /*Label lblRoomCode = new Label("Enter Room Code");
        addroomGrid.add(lblRoomCode, 0, 1);*/
        
       /* TextField txtRoomCode = new TextField();
        addroomGrid.add(txtRoomCode, 1, 1);
        txtRoomCode.setPrefWidth(100);*/
        
        Label lblRoomType = new Label("Enter Room Type");
        addroomGrid.add(lblRoomType, 0, 2);
        
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Economy",
                "Deluxe",
                "Suite"
            );
        ComboBox typeBox = new ComboBox(options);
        
        addroomGrid.add(typeBox,1,2);
        typeBox.setPrefWidth(100);
       
        Label lblRoomPrice = new Label("Enter Room Price");
        addroomGrid.add(lblRoomPrice, 0, 3);
        
        ComboBox priceBox = new ComboBox();
        priceBox.getItems().addAll(95,150,195);
        addroomGrid.add(priceBox, 1, 3);
        priceBox.setPrefWidth(100);
        
        Label lblRoomDescript = new Label("Enter Room Description");
        addroomGrid.add(lblRoomDescript, 0, 4);
        
        TextField txtDescript = new TextField();
        addroomGrid.add(txtDescript, 1, 4);
        txtDescript.setPromptText("Description of Room");
        txtDescript.setPrefSize(200, 100);
        
        Label lblConfirm = new Label();
        addroomGrid.add(lblConfirm, 1, 6);
        
        Button btnSubmitForm = new Button("Submit");
        btnSubmitForm.setOnAction(e -> {
            addRoom(priceBox, typeBox, txtDescript, lblConfirm);
        });
        addroomGrid.add(btnSubmitForm, 1, 5);
        
        addRoomScene.getStylesheets().add(AddRoom.class.getResource("HMStyles.css").toExternalForm());
        
        window.setScene(addRoomScene);
        window.show();
    
    }
    
    void addRoom(ComboBox price, ComboBox type, TextField description, Label lblConfirm){
        try{
            stmt = conn.createStatement();
            String sql2 = "SELECT ROOM_CODE FROM room;";
            
            System.out.println("records into the table..." + sql2);
            
            
            ResultSet rs = null;
            int code = 0;
            rs = stmt.executeQuery(sql2);
             while(rs.next()){
             code = rs.getInt("ROOM_CODE");
            }
            code++;
             //String roomCode = code.getText();
             String roomType = type.getSelectionModel().getSelectedItem().toString();
             String priceRoom= price.getSelectionModel().getSelectedItem().toString();
             double roomPrice = Double.parseDouble(priceRoom);
             String roomDescript = description.getText();
             int roomAvail = 1;
             String sql = "INSERT INTO room "
                     + "VALUES('"+ code + "','" + roomType + "','" + roomPrice + "','" + roomDescript +"','" + roomAvail + "', '0');";
            
            
            System.out.println("records into the table..." + sql);
 
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table..." + sql);
            lblConfirm.setText("New Room R" + code + "Succesfully Added.");
            
        }
        catch(Exception room){
            System.out.println("Error In Adding Room");
            lblConfirm.setText("Error In Adding Room");
            
        }
    }
}
