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
 * @author Moishin
 */
public class BookShop extends DatabaseMethods{
    
    double cost=0; 
    double sum=0;
    double price=0; 
    double quan =0;
    public ObservableList<String> data;
     public void BookShopAccess(Stage window){
        connectDatabase();
        GridPane grid = new GridPane();
        Scene bookShopScene = new Scene(grid,800,600);
        window.setMaximized(false);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
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
        btnLogOut.setTextFill(Color.TOMATO);
        btnLogOut.setPadding(new Insets(5,5,5,5));
        getItemDetails();
        ComboBox purchaseItem = new ComboBox(data);
        purchaseItem.setPromptText("Select Item Requested");
        purchaseItem.setPrefSize(250, 50);
        grid.add(purchaseItem, 0, 1);
        purchaseItem.setOnAction(e->{
            price = getPrice(purchaseItem.getValue().toString());
            System.out.println(price);
        });
        
        Label confirm = new Label();
        grid.add(confirm,0, 5);
        
        TextField cusCode = new TextField();
        cusCode.setPromptText("Customer Code");
        cusCode.setPrefSize(250, 50);
        grid.add(cusCode,0,2);
        //TextField for entering quantity
        TextField TxtQty = new TextField();
        TxtQty.setPromptText("Quantity - Enter value 1 - 5");
        TxtQty.setPrefSize(250, 50); 
        grid.add(TxtQty,0,3);
        
        Label LblTotal = new Label();
        LblTotal.setText("Current Total:\n" + sum);
        LblTotal.setId("totalCafe");
        
        //Button for adding values and clearing TextFields
        Button BtnAdd = new Button();
        BtnAdd.setText("Add");
        BtnAdd.setOnAction(e ->{
            quan = Double.parseDouble(TxtQty.getText());
            int cuscode = Integer.parseInt(cusCode.getText());
            
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
        grid.add(BtnAdd,0,4 );
        BtnAdd.setPrefSize(250, 50);
        
        //Label for displaying the total accumulation
        
        grid.add(LblTotal,5,1);
    
        
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
            cusCode.clear();
        });
        grid.add(BtnNew,5,5);
        BtnNew.setPrefSize(100, 40);
            
        //Button to Submit to Database
        Button BtnSubmit = new Button();
        BtnSubmit.setText("Submit");
        BtnSubmit.setOnAction(e ->{
            //TxtItem.clear();
            submitCost(cusCode,confirm);
            TxtQty.clear();
            BtnNew.setVisible(true);
        });
        
        grid.add(BtnSubmit,5,4);
        BtnSubmit.setPrefSize(100, 40);
        
        bookShopScene.getStylesheets().add(BookShop.class.getResource("HMStyles.css").toExternalForm());
        
        window.show();
        window.setScene(bookShopScene);
     }
     
     void getItemDetails(){
        ResultSet bkItems = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM inventory WHERE `B/C` = 'B';";
             
             String []roomCodes = new String[200];
             List list = new ArrayList();
             bkItems = stmt.executeQuery(sql);
             int i = 0;
             String name;
             while(bkItems.next()){
                 name = bkItems.getString("ItemNAME");
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
            String sql = "INSERT INTO `transaction`(`CUS_CODE`, `TRANS_TYPE`, `TRANS_COST`) VALUES ('"+ code.getText() +"','PAID',"+ cost + ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            confirm.setText("Succesfull Transaction");
        }
        catch(Exception x){
            System.out.println("Error In Displaying");
            confirm.setText("Unsuccessful Transaction");
        }
     }

}
