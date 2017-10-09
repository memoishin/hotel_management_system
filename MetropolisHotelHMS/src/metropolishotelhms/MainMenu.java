/*

 */
package metropolishotelhms;

import java.sql.ResultSet;
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
public class MainMenu extends DatabaseMethods{
    public void start(Stage window){
        connectDatabase();
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
        
        Label lblName = new Label();
        getName(lblName);
        
        Text txtTitle = new Text();
        txtTitle.setText("MAIN MENU");
        txtTitle.setFont(Font.font("Arial Black", FontWeight.NORMAL,20));
        txtTitle.setFill(Color.HONEYDEW);
        topMenu.getChildren().add(txtTitle);
        grid.add(topMenu, 0, 0);
        
        DateTime date = new DateTime(); 
        Label lblDate = new Label("Today is : " + date.getDate());
        grid.add(lblDate, 1, 0);
        
        Label lblRoom = new Label("Access Room Booking");
        grid.add(lblRoom, 0, 1);
        
        Button btnRoom = new Button("Room Booking");
        btnRoom.setPrefSize(150, 50);
        grid.add(btnRoom, 1, 1);
        btnRoom.setOnAction(e -> {
            RoomBookingOptions room = new RoomBookingOptions();
            room.start(window);
        });
        
        Label lblCafe = new Label("Access Cafeteria");
        grid.add(lblCafe, 0, 2);
        lblCafe.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnCafe = new Button("Cafeteria");
        btnCafe.setPrefSize(150, 50);
        grid.add(btnCafe, 1, 2);
        btnCafe.setOnAction(e -> {
            CafeteriaToDB cafe = new CafeteriaToDB();
            cafe.start(window);
        });
        
        Label lblTransport = new Label("Access Transport");
        grid.add(lblTransport, 0, 3);
        lblTransport.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnTransport = new Button("Transport");
        btnTransport.setPrefSize(150, 50);
        grid.add(btnTransport, 1, 3);
        btnTransport.setOnAction(e -> {
            Transportation trans = new Transportation();
            trans.transport(window);
        });
        
        Label lblBookshop = new Label("Access Bookshop");
        grid.add(lblBookshop, 0, 4);
        lblBookshop.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        
        Button btnBookshop = new Button("Bookshop");
        btnBookshop.setPrefSize(150, 50);
        grid.add(btnBookshop, 1, 4);
        btnBookshop.setOnAction(e -> {
            BookShop book = new BookShop();
            book.BookShopAccess(window);
        });
        
        
        Label warn2 = new Label();
        grid.add(warn2, 0, 6);
        
        
        
        window.setScene(mainMenuScene);
        mainMenuScene.getStylesheets().add(MainMenu.class.getResource("HMStyles.css").toExternalForm());
        window.setScene(mainMenuScene);
        window.show(); 
    }
    
    void getName(Label name){
        ResultSet rs = null;
        String sql = "SELECT * FROM staff WHERE STAFF_ID = '" + " " + "';";
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                name.setText("Welcome " +  rs.getString("STAFF_FNAME"));
            }
        }
        catch(Exception q){
            
        }
        
    }
}
