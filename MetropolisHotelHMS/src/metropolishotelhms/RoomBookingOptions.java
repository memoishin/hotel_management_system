/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class RoomBookingOptions {
    public void start(Stage window){
        
        HBox topMenu = new HBox();
        window.setMaximized(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        Scene mainMenuScene = new Scene(grid, 800, 600);
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        grid.add(btnLogOut, 3, 0);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            MainMenu main = new MainMenu();
            main.start(window);
        });
        grid.add(btnBack, 2, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Text txtTitle = new Text();
        txtTitle.setText("Room Booking Options");
        txtTitle.setFont(Font.font("Arial Black", FontWeight.NORMAL,20));
        txtTitle.setFill(Color.HONEYDEW);
        topMenu.getChildren().add(txtTitle);
        grid.add(topMenu, 0, 0);
        
        DateTime date = new DateTime(); 
        Label lblDate = new Label("Today is : " + date.getDate());
        grid.add(lblDate, 1, 0);

        
        Label lblReg = new Label();
        lblReg.setText("Select this option to register a new customer");
        grid.add(lblReg, 0, 1);
        lblReg.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnRegCus = new Button("Register Customer");
        btnRegCus.setPrefSize(150, 50);
        grid.add(btnRegCus, 1, 1);
        btnRegCus.setOnAction(e -> {
            CustomerRegistration cusreg = new CustomerRegistration();
            cusreg.CustomerRegister(window);
        });
        
        
        Label lblAllot = new Label();
        lblAllot.setText("Select this look for available rooms\n and book the customer a room");
        grid.add(lblAllot, 0, 2);
        lblAllot.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnAllotRoom = new Button("Allot Room");
        btnAllotRoom.setPrefSize(150, 50);
        grid.add(btnAllotRoom, 1, 2);
        btnAllotRoom.setOnAction(e -> {
            AllotRoom room = new AllotRoom();
            room.start(window);
        });
        
        Label lblCO = new Label();
        lblCO.setText("Select this option to proceed with customer checkout");
        grid.add(lblCO, 0, 3);
        lblCO.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnCheckOut = new Button("Checkout");
        btnCheckOut.setPrefSize(150, 50);
        grid.add(btnCheckOut, 1, 3);
        btnCheckOut.setOnAction(e -> {
            CheckOut check = new CheckOut();
            check.start(window);
        });
        
        window.setScene(mainMenuScene);
 
        mainMenuScene.getStylesheets().add(RoomBookingOptions.class.getResource("HMStyles.css").toExternalForm());
        window.setScene(mainMenuScene);
        window.show();
        
    }
    
    
}
