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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



/**
 *
 * @author sk71139sk
 */
public class CafeteriaToDB extends DatabaseMethods{

    public ObservableList<String> data;
    double cost=0; 
    double sum=0;
    double price=0; 
    double quan =0;

    public void start(Stage primaryStage){
        connectDatabase();
        //GridPane Setup
        GridPane root = new GridPane();
        root.setPadding(new Insets (8,8,8,8));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            MainMenu main = new MainMenu();
            main.start(primaryStage);
        });
        root.add(btnBack, 4, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(primaryStage);
        });
        root.add(btnLogOut, 5, 0);
        getItemDetails();
        //TextField for entering item name
        ComboBox purchaseItem = new ComboBox(data);
        root.add(purchaseItem, 1, 1);
        purchaseItem.setPromptText("Select Item Requested");
        purchaseItem.setPrefSize(250, 50);
        purchaseItem.setOnAction(e->{
            price = getPrice(purchaseItem.getValue().toString());
            System.out.println(price);
        });
        //TextField for entering item cost
        
        TextField cus = new TextField();
        cus.setPromptText("Enter Customer Code");
        cus.setPrefSize(250, 50);
        root.add(cus,1,2);
        
        Label confirm = new Label();
        root.add(confirm,1, 6);
        
        //TextField for entering quantity
        TextField TxtQty = new TextField();
        TxtQty.setPromptText("Quantity - Enter value from 1 - 5");
        TxtQty.setPrefSize(250, 50); 
        root.add(TxtQty,1,3);
        
        Label LblTotal = new Label();
        LblTotal.setText("Current Total:\n" + sum);
        LblTotal.setId("totalCafe");
        
        //Button for adding values and clearing TextFields
        Button BtnAdd = new Button();
        BtnAdd.setText("Add");
        BtnAdd.setOnAction(e ->{
            quan = Double.parseDouble(TxtQty.getText());
            int cuscode = Integer.parseInt(cus.getText());
            
            String sql2 = "SELECT CUS_CODE FROM customer;";
            ResultSet rs = null;
            int code = 0;
            try{
                stmt=conn.createStatement();
                rs = stmt.executeQuery(sql2);
                 while(rs.next()){
                 code = rs.getInt("CUS_CODE");
                 System.out.println(code);
                }
                System.out.print(code);
                 
                if(quan > 0 && quan <= 5 && cuscode <= code){
                    cost = price * quan;
                    TxtQty.clear();
                    sum = sum + cost;
                    LblTotal.setText("Current Total: " + sum);
                }
                else{
                    LblTotal.setText("INVALID INPUT");
                }
            
            }
            catch(Exception p){
                LblTotal.setText("INVALID INPUT");
            }
            
            

            //TxtItem.clear();
        });
        root.add(BtnAdd,1,5);
        BtnAdd.setPrefSize(250, 50);
        
        //Label for displaying the total accumulation
        
        root.add(LblTotal,2,2);
        
        Button BtnNew = new Button();
        BtnNew.setVisible(false);
        BtnNew.setText("New Transaction");
        BtnNew.setOnAction(e ->{
            BtnNew.setVisible(false);
            cost=0; 
            sum=0;
            price=0; 
            quan =0;
            confirm.setText(" ");
            LblTotal.setText("0");
            cus.clear();
        });
        root.add(BtnNew,2,6);
        BtnNew.setPrefSize(100, 40);
        
        //Button to Submit to Database
        Button BtnSubmit = new Button();
        BtnSubmit.setText("Submit");
        BtnSubmit.setOnAction(e ->{
            submitCost(cus,confirm);
            TxtQty.clear();
            BtnNew.setVisible(true);
        });
        root.add(BtnSubmit,2,5);
        BtnSubmit.setPrefSize(100, 50);
        //root.getChildren().addAll(TxtItem,TxtCost,TxtQty,LblTotal,BtnAdd, BtnSubmit);
        Scene scene1 = new Scene(root,800,600);
        scene1.getStylesheets().add(CafeteriaToDB.class.getResource("HMStyles.css").toExternalForm());
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
     void getItemDetails(){
        ResultSet cafeItems = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM inventory WHERE `B/C` = 'C';";
             
             String []roomCodes = new String[200];
             List list = new ArrayList();
             cafeItems = stmt.executeQuery(sql);
             int i = 0;
             String name;
             while(cafeItems.next()){
                 name = cafeItems.getString("ItemNAME");
                 list.add(name);
                 System.out.println(list.get(i));
                 i++;
             }

             data = FXCollections.observableArrayList(list);
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }
        
    }
     
     double getPrice(String itemname){
        ResultSet cafeItems = null;
        double Itemprice = 0;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT ItemPRICE FROM inventory WHERE `ItemNAME` = '"+ itemname + "';";
             System.out.println(sql);
             cafeItems = stmt.executeQuery(sql);
             int i = 0;
             
             while(cafeItems.next()){
                 Itemprice = cafeItems.getDouble("ItemPRICE");
             }
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }
        return Itemprice;
        
    }
     
     void submitCost(TextField code, Label confirm){
        try{
            stmt=conn.createStatement();
            String sql = "INSERT INTO `transaction`(`CUS_CODE`, `TRANS_TYPE`, `TRANS_COST`) VALUES ('"+ code.getText() +"','NOT PAID',"+ cost + ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            confirm.setText("Succesfull Transaction");
        }
        catch(Exception x){
            System.out.println("Error In Displaying");
            confirm.setText("Unsuccesfull Transaction");
        }
     }

}
