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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class AllotRoom extends DatabaseMethods{
    

    TableView table = new TableView();
    private ObservableList<Rooms> data;
    Scene roomShow = new Scene(new Group(), 800, 600);
    
    public void start(Stage window){
        connectDatabase();
        GridPane layout = new GridPane(); 
        Scene roomCheck = new Scene(layout, 800, 600);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
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
        
        Button btnPrev = new Button("Go Back");
        btnPrev.setOnAction(e -> {
            AllotRoom allot = new AllotRoom();
            allot.start(window);
        });

        
        btnPrev.setTextFill(Color.CADETBLUE);
        btnPrev.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        layout.add(btnLogOut, 3, 0);
        
        Text Allot = new Text("Room Selection");
        Allot.setFont(Font.font("Arial", FontPosture.REGULAR, 20));
        layout.add(Allot, 0, 0);
        
        Label lblShow = new Label("List all the rooms \nthat are empty");
        layout.add(lblShow, 0, 1);
        
        
        table.setEditable(false);
        table.setPrefSize(600, 500);
        
        TableColumn roomCodeCol = new TableColumn("Room Code");
        TableColumn roomTypeCol = new TableColumn("Room Type");
        TableColumn roomPriceCol = new TableColumn("Room Price");
        TableColumn roomDescCol = new TableColumn("Room Description");

        roomCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        
        table.getColumns().addAll(roomCodeCol, roomTypeCol, roomPriceCol,roomDescCol);
        //table.setPrefWidth(450);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, btnPrev);
 
        ((Group) roomShow.getRoot()).getChildren().addAll(vbox);
        

        Button btnShow = new Button("Show Available Rooms");
        layout.add(btnShow,1,1);
        btnShow.setMinSize(150, 50);
        btnShow.setOnAction(e -> {
            showAvailRooms();
            window.setScene(roomShow);

        });
        
        Label lblSelect = new Label("Select A Room");
        layout.add(lblSelect, 0, 2);
        
        Button btnSelect = new Button("Select Room");
        layout.add(btnSelect,1,2);
        btnSelect.setMinSize(150, 50);
        btnSelect.setOnAction(e -> {
            SelectRoom select = new SelectRoom();
            select.selectRoomMode(window);
        });
                
        window.setScene(roomCheck);
        roomCheck.getStylesheets().add(AllotRoom.class.getResource("HMStyles.css").toExternalForm());       
        roomShow.getStylesheets().add(AllotRoom.class.getResource("HMStyles.css").toExternalForm());
    }
    
    void showAvailRooms(){
        ResultSet roomDisplay = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM room WHERE ROOM_AVAIL = 1;";
             
             String []roomCodes = new String[200];
             String []roomTypes = new String[200];
             double []roomPrice = new double[200];
             String []roomDescript = new String[200];
             
             roomDisplay = stmt.executeQuery(sql);
             int i = 0;
             String rcode;
             while(roomDisplay.next()){
                 rcode = roomDisplay.getString("ROOM_CODE");
                 roomTypes[i] = roomDisplay.getString("ROOM_TYPE");
                 roomPrice[i] = roomDisplay.getDouble("ROOM_PRICE");
                 roomDescript[i] = roomDisplay.getString("ROOM_DESCRIPTION");
                 roomCodes[i] = "R"+rcode;
                 i++;
            }
            
             List list = new ArrayList();
             
             for(int j = 0; j < i ; j++){
                list.add(new Rooms(roomCodes[j],roomTypes[j],roomPrice[j],roomDescript[j])); 
             }
             data = FXCollections.observableArrayList(list);
             table.setItems(data);
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }
        
    }    
}
