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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class SelectRoom extends DatabaseMethods{
    private ObservableList<Rooms> data;
    private ObservableList<Rooms> data2;
    public void selectRoomMode(Stage window){
        connectDatabase();
        GridPane grid = new GridPane();
        Scene roomSelect = new Scene(grid, 640, 480);
        
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Button btnPrev = new Button("Go Back");
        btnPrev.setOnAction(e -> {
            AllotRoom allot = new AllotRoom();
            allot.start(window);
        });
        
        Label lblRoomCode = new Label("Enter Room Code");
        Label lblCusCode = new Label("Enter Customer Code");
        Label lblNumDays = new Label("Enter Number of Stay Days");
        
        getAvailRooms();
        ComboBox txtRoomCode = new ComboBox(data);
        
        getAvailCustomers();
        ComboBox cus = new ComboBox(data2);
        grid.add(cus, 1, 2);

        TextField txtNumDays = new TextField();
        txtNumDays.setPromptText("Enter value from 1 - 100");
        Button btnAllot = new Button("Select Room");
        
        HBox group = new HBox();     
        final ToggleGroup typeGroup = new ToggleGroup();

        RadioButton economy = new RadioButton("Economy");
        economy.setToggleGroup(typeGroup);
        economy.setSelected(false);
        economy.setPadding(new Insets(5,5,5,5));

        RadioButton deluxe = new RadioButton("Deluxe");
        deluxe.setToggleGroup(typeGroup);
        deluxe.setSelected(false);
        deluxe.setPadding(new Insets(5,5,5,5));
        
        RadioButton premium = new RadioButton("Premium");
        premium.setToggleGroup(typeGroup);
        premium.setSelected(false);
        premium.setPadding(new Insets(5,5,5,5));
        group.getChildren().addAll(economy, deluxe, premium);
        grid.add(group, 1, 4);

        Label lbltype = new Label("Select Room Type");
        grid.add(lbltype, 0, 4);
        
        Label lblFinal = new Label("Please Enter Required Fields");
        grid.add(lblFinal, 1, 7);
        
        btnAllot.setOnAction(e -> {
            if(Integer.parseInt(txtNumDays.getText()) > 0 && Integer.parseInt(txtNumDays.getText()) < 101){
                assignRoom(cus,txtRoomCode, txtNumDays, economy, deluxe, premium, lblFinal);
            }
            else{
                lblFinal.setText("Invalid Fields Entered. Please Check");
            }
            
        });
        
        grid.add(btnPrev, 3, 0);
        grid.add(lblRoomCode, 0, 1);
        grid.add(lblCusCode, 0, 2);
        grid.add(lblNumDays, 0, 3);
        grid.add(txtRoomCode, 1, 1);
        grid.add(txtNumDays, 1, 3);
        grid.add(btnAllot, 1, 6);
        
        window.setScene(roomSelect);
        window.show();
        
        roomSelect.getStylesheets().add(SelectRoom.class.getResource("HMStyles.css").toExternalForm());
        
    }
    
    public void assignRoom(ComboBox cuscode, ComboBox roomcode, TextField days, RadioButton economy, RadioButton deluxe, RadioButton premium, Label lblFinal){
        String ccode = cuscode.getValue().toString();
        String rcode = roomcode.getValue().toString();
        rcode = rcode.substring(1);
        int numDays = Integer.parseInt(days.getText());
        
        System.out.println(rcode);
        
        DateTime date = new DateTime();
        //String dateCheckIn = date.getDateTime();
        //System.out.println(dateCheckIn);
        double cost = 0;
        
        if(economy.isSelected()){
            cost = numDays * 95;
        }
        else if(deluxe.isSelected()){
            cost = numDays * 150;
        }
        else if(premium.isSelected()){
            cost = numDays * 200;
        }
        else{
            System.out.println("Nothing Selected");
        }
        
        System.out.println(cost);
        
        try{
            stmt = conn.createStatement();

            ResultSet rs = null;
            String sql1 = "UPDATE `room` "
                    + "SET `ROOM_AVAIL` = 0, CUS_CODE  = " + ccode 
                    + " WHERE ROOM_CODE = '" + rcode + "';";

            System.out.println(sql1);
            String book = "SELECT BK_ID FROM booking;";        
            int bkID = 0;

            try{
                rs = stmt.executeQuery(book);
                while(rs.next()){
                    bkID = rs.getInt("BK_ID");
                    System.out.println(bkID);
                }
                
            }
            catch(Exception i){
                 System.out.println("Step 1");
            }   
           bkID++;
           System.out.println(bkID);
            String sql2 = "INSERT INTO booking " +
                          "VALUES (" + bkID + ", " + ccode + ",'" + date.getDate2() + "','" + "active" + "','" + "0" + "','" + cost + "','" + roomcode.getValue().toString() + "');";

            System.out.println(sql2);
            stmt.executeUpdate(sql1);
            System.out.println("Inserted records into the table..." + sql1);

            stmt.executeUpdate(sql2);
            System.out.println("Inserted records into the table..." + sql2);
            lblFinal.setText("Booking is succesfull");
               
        
            String sql3 = "INSERT INTO `transaction`(`CUS_CODE`, `TRANS_TYPE`, `TRANS_COST`) VALUES ('"+ ccode +"','NOT PAID',"+ cost + ");";
            stmt.executeUpdate(sql3);
            System.out.println("Inserted records into the table..." + sql3);
               }
            catch(Exception assign){
                System.out.println("Error in booking");
                lblFinal.setText("Booking Unsuccesful");
            }
    
    }
    
        void getAvailRooms(){
        ResultSet roomDisplay = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM room WHERE ROOM_AVAIL = 1;";
             
             String []roomCodes = new String[200];
             List list = new ArrayList();
             roomDisplay = stmt.executeQuery(sql);
             int i = 0;
             String rcode;
             while(roomDisplay.next()){
                 rcode = roomDisplay.getString("ROOM_CODE");
                 roomCodes[i] = "R"+rcode;
                 list.add(roomCodes[i]);
                 System.out.println(list.get(i));
                 i++;
                 
             }

             data = FXCollections.observableArrayList(list);
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }
        
    }
        
        void getAvailCustomers(){
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM customer;";

             List list = new ArrayList();
             rs = stmt.executeQuery(sql);

             String rcode;
             while(rs.next()){
                 rcode = rs.getString("CUS_CODE");
                 list.add(rcode);
             }

             data2 = FXCollections.observableArrayList(list);
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }
        
    }
        
}
