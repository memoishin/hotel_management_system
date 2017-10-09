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
import javafx.scene.paint.Color;
import javafx.stage.Stage;



/**
 *
 * @author sk71139sk
 */
public class AddInventory extends DatabaseMethods{

 

    public void start(Stage primaryStage){
        connectDatabase();
        //GridPane Setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets (8,8,8,8));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface main = new ManagerInterface();
            main.managerMode(primaryStage);
        });
        grid.add(btnBack, 2, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(primaryStage);
        });
        grid.add(btnLogOut, 3, 0);
        
        Label lblItemCode = new Label("Enter Item Code: ");
        grid.add(lblItemCode, 0, 1);
        
        Label lblItemName = new Label("Enter Item Name: ");
        grid.add(lblItemName, 0, 2);
        
        Label lblItemPrice = new Label("Enter Item Price: ");
        grid.add(lblItemPrice, 0, 3);
        
        Label lblItemLocation = new Label("Enter Item Location: ");
        grid.add(lblItemLocation, 0, 4);
        
        TextField txtCode = new TextField();
        grid.add(txtCode, 1, 1);
        
        TextField txtName = new TextField();
        grid.add(txtName, 1, 2);

        TextField txtPrice = new TextField();
        grid.add(txtPrice, 1, 3);
        
        HBox group = new HBox();     
        final ToggleGroup shopType = new ToggleGroup();

        RadioButton bookshop = new RadioButton("BookShop");
        bookshop.setToggleGroup(shopType);
        bookshop.setSelected(false);
        bookshop.setPadding(new Insets(5,5,5,5));

        RadioButton cafe = new RadioButton("Cafeteria");
        cafe.setToggleGroup(shopType);
        cafe.setSelected(false);
        cafe.setPadding(new Insets(5,5,5,5));
        group.getChildren().addAll(bookshop, cafe);
        grid.add(group, 1, 4);
        
        Label confirm = new Label();
        grid.add(confirm, 1, 6);
        
        
        Button btnSubmit = new Button("Submit");
        grid.add(btnSubmit, 1, 5);
        btnSubmit.setOnAction(e -> {
            submitData(txtCode, txtName, txtPrice, bookshop,cafe, confirm);
        });
        
        
        
       
        //root.getChildren().addAll(TxtItem,TxtCost,TxtQty,LblTotal,BtnAdd, BtnSubmit);
        Scene scene1 = new Scene(grid,800,600);
        scene1.getStylesheets().add(CafeteriaToDB.class.getResource("HMStyles.css").toExternalForm());
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    void submitData(TextField code, TextField name, TextField price, RadioButton book, RadioButton cafe, Label confirm){
        char option;
        if(book.isSelected()){
            option = 'B';
        }
        else{
            option = 'C';
        }
            
        try{
            stmt = conn.createStatement();
            String sql = "INSERT INTO `inventory`(`ItemCODE`, `ItemNAME`, `ItemPRICE`, `B/C`) "
                    + "    VALUES ('" + code.getText() + "','" + name.getText() + "','" + Double.parseDouble(price.getText())+ "','" + option + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            confirm.setText("Succesfully Added");
            code.clear();
            name.clear();
            price.clear();
        }
        catch(Exception f){
            System.out.println("Error");
            confirm.setText("Not Added Succesfully");
        }
    }
}