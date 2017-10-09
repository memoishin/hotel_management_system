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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class Login extends DatabaseMethods {
    
    
    public void start(Stage primaryStage) {
        connectDatabase();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(false);
        grid.setId("loginGrid");
        
        //Text sceneTitle = new Text("Metropolis Hotel");
        //sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
       // grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name : ");
        grid.add(userName, 0, 2);
        userName.setId("loginlabel");
        TextField userTField = new TextField();
        grid.add(userTField, 1, 2);
        userTField.setId("logintxt");
        
        Label pw = new Label("Password : ");
        grid.add(pw, 0, 3);
        
        pw.setId("loginlabel");
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        pwBox.setId("logintxt");
        
        Label access = new Label("Access Level : ");
        grid.add(access, 0, 4);
        access.setId("loginlabel");
        Label warn = new Label("Enter User name and Password");
        grid.add(warn, 1, 5);
        warn.setId("loginlabel");
        
        HBox group = new HBox(); 
        ToggleGroup accessLevel = new ToggleGroup();
        
        RadioButton manager = new RadioButton("Manager");
        RadioButton staff = new RadioButton("Staff");
        
        manager.setToggleGroup(accessLevel);
        manager.setSelected(false);
        manager.setPadding(new Insets(5,5,5,5));
        manager.setId("toggle1");
        
        
        staff.setToggleGroup(accessLevel);
        staff.setSelected(false);
        staff.setPadding(new Insets(5,5,5,5));
        group.getChildren().addAll(manager, staff);
        staff.setId("toggle1");
        grid.add(group, 1, 4);
        
        Button btn = new Button("Log in");
        btn.setId("loginbutton");
        btn.setOnAction(e -> {
            verifyDetails(userTField, pwBox,primaryStage,warn, staff, manager);

        });
        ImageView logo = new ImageView(getClass().getResource("logo.png").toExternalForm());
         logo.setFitHeight(250);
         logo.setFitWidth(250);
        grid.add(logo, 1, 0);
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 2, 4);
        HBox hbSignBtn = new HBox(10);

        grid.add(hbSignBtn, 0, 4);
        scene.getStylesheets().add(MetropolisHotelHMS.class.getResource("HMStyles.css").toExternalForm());
    }
    
    public void verifyDetails(TextField userName, PasswordField password, Stage primaryStage, Label warning, RadioButton staff, RadioButton manager) {
        ResultSet rs = null;
        String sql = " ";
        String pass = " ";
        if(staff.isSelected()){
            sql = "SELECT STAFF_PASSWORD FROM staff WHERE STAFF_ID = '"+ userName.getText() + "';";   
            System.out.println(sql);

            try{
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    pass = rs.getString("STAFF_PASSWORD");
                }
                System.out.println(pass);
                if((password.getText()).equals(pass)){
                    MainMenu main = new MainMenu();
                    main.start(primaryStage);
                }
                else{
                    warning.setText("Invalid Username or Password");
                    userName.clear();
                    password.clear();
                }
            }
            catch(Exception n){
                System.out.println("Invalid Details");
                warning.setText("Enter Valid Username or Password");
            }            
        }else if(manager.isSelected()){
            sql = "SELECT MAN_PASSWORD FROM manager WHERE MAN_ID = '"+ userName.getText() + "';";
            System.out.println(sql);

            try{
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    pass = rs.getString("MAN_PASSWORD");
                }
                System.out.println(pass);
                if((password.getText()).equals(pass)){
                    ManagerInterface main2 = new ManagerInterface();
                    main2.managerMode(primaryStage);
                }
                else{
                    warning.setText("Invalid Username or Password");
                    userName.clear();
                    password.clear();
                }
            }
            catch(Exception n){
                System.out.println("Invalid Details");
                warning.setText("Enter Valid Username or Password");
            }            
        }
        else{
            warning.setText("Please enter correct Details");
        }

        
    }

}
