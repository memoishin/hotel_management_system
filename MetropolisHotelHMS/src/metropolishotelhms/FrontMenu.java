/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

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
public class FrontMenu {
    
    public void FrontMenu(Stage window){
        
        HBox topMenu = new HBox();
        window.setMaximized(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        Scene mainMenuScene = new Scene(grid, 640, 480);
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        grid.add(btnLogOut, 3, 0);
        
        Text txtTitle = new Text();
        txtTitle.setText("Welcome User");
        txtTitle.setFont(Font.font("Arial Black", FontWeight.NORMAL,20));
        txtTitle.setFill(Color.HONEYDEW);
        topMenu.getChildren().add(txtTitle);
        grid.add(topMenu, 0, 0);
        
        DateTime date = new DateTime(); 
        Label lblDate = new Label("Today is : " + date.getDate());
        grid.add(lblDate, 1, 0);
    }
    
}
