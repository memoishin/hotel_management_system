
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class ManagerInterface extends DatabaseMethods{
    TableView table = new TableView();
    private ObservableList<Rooms> data;
    
    TableView table2 = new TableView();
    private ObservableList<Staff> data2;
    
    Scene roomShow = new Scene(new Group(), 800, 600);
    Scene staffShow = new Scene(new Group(), 800, 600);
    
    public void managerMode(Stage window){
        connectDatabase();
        
        GridPane layout = new GridPane();
        Scene managerScene = new Scene(layout, 640, 480);
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        layout.add(btnLogOut, 4, 0);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface man = new ManagerInterface();
            man.managerMode(window);
        });
        layout.add(btnBack, 3, 0);
        
        /*Label lblAddRoom = new Label("Select this to add a new Room");
        layout.add(lblAddRoom,0 ,1);*/
        
        
        Button btnAddRoom = new Button("Add New Room");
        layout.add(btnAddRoom, 1, 1);
        btnAddRoom.setPrefSize(150, 50);
        btnAddRoom.setOnAction(e -> {
            AddRoom addroom = new AddRoom();
            addroom.addRoomMode(window);
        });
        
        /*Label lblAddStaff = new Label("Select this to add a new Staff");
        layout.add(lblAddStaff,0 ,2);*/
        
        Button btnAddStaff = new Button("Add New Staff");
        layout.add(btnAddStaff, 1, 2);
        btnAddStaff.setPrefSize(150, 50);
        btnAddStaff.setOnAction(e -> {
            AddStaff addstaff = new AddStaff();
            addstaff.addStaffMode(window);
        });
        
        Button btnRemoveStaff = new Button("Remove Staff");
        layout.add(btnRemoveStaff, 0, 2);
        btnRemoveStaff.setPrefSize(150, 50);
        btnRemoveStaff.setOnAction(e -> {
            RemoveStaff removestaff = new RemoveStaff();
            removestaff.removeStaff(window);
        });
        
        Button btnRemoveRoom = new Button("Remove Room");
        layout.add(btnRemoveRoom, 0, 1);
        btnRemoveRoom.setPrefSize(150, 50);
        btnRemoveRoom.setOnAction(e -> {
            RemoveRoom removerRoom = new RemoveRoom();
            removerRoom.removeStaff(window);
        });
        
        Button btnPrev = new Button("Go Back");
        btnPrev.setOnAction(e -> {
            ManagerInterface allot = new ManagerInterface();
            allot.managerMode(window);
        });
        btnPrev.setTextFill(Color.CADETBLUE);
        btnPrev.setPadding(new Insets(5,5,5,5));
        
        table2.setEditable(false);
        TableColumn staffIDCol = new TableColumn("Staff ID");
        TableColumn staffNameCol = new TableColumn("Staff Name");
        TableColumn staffAddressCol = new TableColumn("Staff Address");
        //TableColumn roomDescCol = new TableColumn("Room Description");

        staffIDCol.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        staffNameCol.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        staffAddressCol.setCellValueFactory(new PropertyValueFactory<>("staffAddress"));
        //roomDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        table2.getColumns().addAll(staffIDCol, staffNameCol, staffAddressCol);
        table2.setPrefWidth(450);
        final VBox vbox2 = new VBox();
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 0, 0, 10));
        vbox2.getChildren().addAll(table2, btnBack);
 
        ((Group) staffShow.getRoot()).getChildren().addAll(vbox2);
        
        
        Button btnViewStaff = new Button("View Staff Details");
        layout.add(btnViewStaff, 1, 3);
        btnViewStaff.setPrefSize(150, 50);
        btnViewStaff.setOnAction(e -> 
        {
            window.setScene(staffShow);
            showStaff();
        });
        
        Button btnAddInventory = new Button("Add Inventory");
        layout.add(btnAddInventory, 0, 4);
        btnAddInventory.setPrefSize(150, 50);
        btnAddInventory.setOnAction(e -> {
            AddInventory addInv = new AddInventory();
            addInv.start(window);
        });
        
        Button deleteAddInventory = new Button("Delete Inventory");
        layout.add(deleteAddInventory, 0, 3);
        deleteAddInventory.setPrefSize(150, 50);
        deleteAddInventory.setOnAction(e -> {
            DeleteInventory delInv = new DeleteInventory();
            delInv.removeItem(window);
        });
        
        layout.setId("pane2");
        
        window.setScene(managerScene);
        managerScene.getStylesheets().add(ManagerInterface.class.getResource("HMStyles.css").toExternalForm());
        roomShow.getStylesheets().add(ManagerInterface.class.getResource("HMStyles.css").toExternalForm());
        window.show();
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
             while(roomDisplay.next()){
                 roomCodes[i] = roomDisplay.getString("ROOM_CODE");
                 roomTypes[i] = roomDisplay.getString("ROOM_TYPE");
                 roomPrice[i] = roomDisplay.getDouble("ROOM_PRICE");
                 roomDescript[i] = roomDisplay.getString("ROOM_DESCRIPT");
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
    
    void showStaff(){
        ResultSet staffDisplay = null;
        try{
            stmt = conn.createStatement();
             String sql = "SELECT * FROM staff;";
             
             String []staffID = new String[200];
             String []staffName = new String[200];
             String []staffAddress = new String[200];
             
             
             staffDisplay = stmt.executeQuery(sql);
             int i = 0;
             while(staffDisplay.next()){
                 staffID[i] = staffDisplay.getString("STAFF_ID");
                 staffName[i] = staffDisplay.getString("STAFF_FNAME");
                 staffAddress[i] = staffDisplay.getString("STAFF_ADDRESS");
                 System.out.println(staffID[i]);
                 i++;
             }
            
             List list2 = new ArrayList();
             
             for(int j = 0; j < i ; j++){
                list2.add(new Staff(staffID[j],staffName[j],staffAddress[j])); 
             }
             for(int k = 0; k < 1; k++){
                 System.out.println(staffID[k]+" "+staffName[k]+" "+staffAddress[k]);
             }
             data2 = FXCollections.observableArrayList(list2);
             table2.setItems(data2);
         }
         catch(Exception y){
             System.out.println("Error In Displaying");
         }   
    }
    
    
}
