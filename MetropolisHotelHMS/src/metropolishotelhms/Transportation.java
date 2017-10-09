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
public class Transportation extends DatabaseMethods{
    double cost = 0;
    public void transport(Stage window){
        connectDatabase();
        GridPane layout = new GridPane();
        Scene scene = new Scene(layout, 800, 600);
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            MainMenu main = new MainMenu();
            main.start(window);
        });
        layout.add(btnBack, 2, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        layout.add(btnLogOut, 3, 0);
        
        
        Label lblCusCode = new Label("Please Enter Customer Code");
        layout.add(lblCusCode, 0, 1);
        TextField txtCusCode = new TextField();
        layout.add(txtCusCode, 1, 1);
        
        Label lblNumTimes = new Label("Enter number of times customer \nused free transport");
        layout.add(lblNumTimes, 0, 2);
        TextField txtNumTimes = new TextField();
        layout.add(txtNumTimes, 1, 2);
        
        Label lblDistance = new Label("Please Enter Maximum Distance to Be travelled (KM)");
        layout.add(lblDistance, 0, 3);
        TextField txtDistance = new TextField();
        layout.add(txtDistance, 1, 3);
        
        Label lblShowCost = new Label();
        layout.add(lblShowCost, 0, 5);
        lblShowCost.setVisible(false);
        
        Label lblFinal = new Label();
        layout.add(lblFinal, 1, 9);
        
        Button btnAcceptCost = new Button("Customer Accept");
        layout.add(btnAcceptCost, 0, 7);
        btnAcceptCost.setVisible(false);
        btnAcceptCost.setOnAction(e -> {
                updateCostDB(txtCusCode, lblFinal);
                txtCusCode.clear();
                txtNumTimes.clear();
                txtDistance.clear(); 
        });
        
        Button btnDeclineCost = new Button("Customer Decline");
        layout.add(btnDeclineCost, 1, 7);
        btnDeclineCost.setVisible(false);
        btnDeclineCost.setOnAction(e -> {
            txtCusCode.clear();
            txtNumTimes.clear();
            txtDistance.clear();
            btnDeclineCost.setVisible(false);
            btnAcceptCost.setVisible(false);
            lblFinal.setText("Transaction Cancelled.");
        });
        
        Button BtnNew = new Button();
        BtnNew.setVisible(false);
        BtnNew.setText("New Transaction");
        BtnNew.setOnAction(e ->{
            BtnNew.setVisible(false);
            txtNumTimes.clear();
            txtDistance.clear();
            txtCusCode.clear();
        });
        layout.add(BtnNew,1,8);
        
        
        
        Button btnCost = new Button("See Travel Costs");
        layout.add(btnCost, 1, 4);
        btnCost.setOnAction(e -> {
        int cuscode = Integer.parseInt(txtCusCode.getText());    
            
                int times  = Integer.parseInt(txtNumTimes.getText());
                double dist = Double.parseDouble(txtDistance.getText());
                
            
                String sql2 = "SELECT CUS_CODE FROM customer;";
                ResultSet rs = null;
                int code = 0;
                try{
                    stmt=conn.createStatement();
                    rs = stmt.executeQuery(sql2);
                    while(rs.next()){
                        code = rs.getInt("CUS_CODE");
                    }
                    System.out.println(code);

                    if(times >= 0 && dist >= 0 && cuscode <= code && cuscode > 0){
                        cost = calcTravelCost(txtDistance, txtNumTimes);
                        lblShowCost.setVisible(true);
                        lblShowCost.setText("The cost of travel is : $" + cost + " .");
                        btnAcceptCost.setVisible(true);
                        btnDeclineCost.setVisible(true);
                        BtnNew.setVisible(true);
                    }
                else{
                    lblFinal.setText("Invalid Inputs. \nEnsure correct values are entered");
                }
            }
            catch(Exception m){
                //lblFinal.setText("Invalid Inputs. \nEnsure correct values are entered");
            }
            finally{
                cuscode = 0;
                
            }
            
        });
        
        
        
        
        
        
        
        
        window.setScene(scene);
        scene.getStylesheets().add(Transportation.class.getResource("HMStyles.css").toExternalForm());
    }
    
    public double calcTravelCost(TextField distance, TextField numTimes){
        double amount = 0;
        double dist = Double.parseDouble(distance.getText());
        int number = Integer.parseInt(numTimes.getText());
        double travelCost = 0;
        if(number == 0){
            if(dist <= 10){
                amount = 0;
            }
            else{
                amount = dist * 2.5;
            }
        }
        else{
            amount = dist * 2.5;
        }
        return amount;
    }
    
    public void updateCostDB(TextField txtcode, Label lblFinal){
        String cuscode = txtcode.getText();
        String sql = " ";
        int code = 0;
        try{
            stmt=conn.createStatement();
            sql = "INSERT INTO `transaction`(`CUS_CODE`, `TRANS_TYPE`, `TRANS_COST`) VALUES ('"+ cuscode +"','NOT PAID',"+ cost + ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        catch(Exception x){
            System.out.println("Error In Displaying");
        }
        
        try{
            //stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            lblFinal.setText("Transaction Updated");
        }
        catch(Exception m){
            System.out.println("Error Found");
            lblFinal.setText("Transaction Error");
        }
        System.out.println(sql);
    }
    
}


