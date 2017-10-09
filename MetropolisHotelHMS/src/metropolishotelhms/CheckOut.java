/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class CheckOut extends DatabaseMethods{
    public void start(Stage window){
        connectDatabase();
        GridPane grid = new GridPane();
        Scene checkScene = new Scene(grid, 800,600);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            MainMenu main = new MainMenu();
            main.start(window);
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
        
        Label lblCusCode = new Label("Enter Customer Code");
        grid.add(lblCusCode, 0, 1);
        
        Label lblName = new Label("Name : ");
        Label lblRoom = new Label("Room Number : ");
        Label lblAmount = new Label("Amount Due : ");
        Button btnPay  = new Button("Paid");
        Label lblConfirm = new Label("Viewing Details");
        Button btnCheckOut = new Button("Checkout Customer");
        btnCheckOut.setVisible(false);
        /*Label lblRoomCode = new Label("Enter Room Code");
        grid.add(lblRoomCode, 0, 2);*/
        
        TextField txtCusCode = new TextField();
        grid.add(txtCusCode, 1, 1);
        
        Button btnGetDetails = new Button("Get Customer Details");
        grid.add(btnGetDetails,2,1);
        btnGetDetails.setOnAction(e -> {
            getCustomerDetails(txtCusCode,lblName,lblRoom,lblAmount,lblConfirm);
        });
        
        btnPay.setOnAction(e -> {
            customerPayment(txtCusCode);
            btnCheckOut.setVisible(true);
        });
        
        btnCheckOut.setOnAction(e -> {
            checkout(txtCusCode, lblConfirm);
        });
                
        grid.add(lblName,0,2);
        grid.add(lblRoom,1,2);
        grid.add(lblAmount,0,3);
        grid.add(btnPay,1,3);
        grid.add(lblConfirm,1,5);
        grid.add(btnCheckOut,1,4);
        
        /*TextField txtRoomCode = new TextField();
        grid.add(txtRoomCode, 1, 2);*/
        
        checkScene.getStylesheets().add(CheckOut.class.getResource("HMStyles.css").toExternalForm());
        
        window.setScene(checkScene);
        window.show();
    }
    
    void getCustomerDetails(TextField cusCode, Label name, Label room, Label amount, Label confirm){
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        String fname = " ",lname = " ",croom = " ",c_amount = " ";
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM customer WHERE CUS_CODE = " + cusCode.getText() + ";";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                fname = rs.getString("CUS_FNAME");
                lname = rs.getString("CUS_LNAME");
            }
            name.setText("Name: " + fname + " " + lname);
            
            String sql2 = "SELECT SUM(TRANS_COST) FROM transaction WHERE CUS_CODE = " + cusCode.getText() + " AND TRANS_TYPE = 'NOT PAID';";
            System.out.println(sql2);
            rs2 = stmt.executeQuery(sql2);
            while(rs2.next()){
                c_amount = rs2.getString("SUM(TRANS_COST)");
            }
            System.out.println(c_amount);
            amount.setText("Amount Due: " + c_amount);
            
            String sql3 = "SELECT ROOM_CODE FROM room WHERE CUS_CODE = " + cusCode.getText() +";";
            System.out.println(sql3);
            rs3 = stmt.executeQuery(sql3);
            while(rs3.next()){
                croom = rs3.getString("ROOM_CODE");
            }
            System.out.println(croom);
            room.setText("Room Code : R" + croom);
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }
    
    void customerPayment(TextField cusCode){
        try{
            stmt = conn.createStatement();
            String sql = "UPDATE transaction SET TRANS_TYPE = 'PAID' WHERE CUS_CODE = " + cusCode.getText() + ";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        catch(Exception f){
            System.out.println("Error 2");
        }
    }
    
    void checkout(TextField cusCode, Label lblConfirm){
        try{
            stmt = conn.createStatement();
            String sql = "UPDATE room SET ROOM_AVAIL = 1 WHERE CUS_CODE = " + cusCode.getText() + ";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            lblConfirm.setText("Checkout Successfull");
        }
        catch(Exception g){
            System.out.println("Error 3");
            lblConfirm.setText("Checkout Unsuccessfull");
        }
    }
}
