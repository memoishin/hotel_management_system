
package metropolishotelhms;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Moishin
 */
public class AddStaff extends DatabaseMethods {
    
    String dob;
    ObservableList<String> countries;
    public void addStaffMode(Stage window){
        connectDatabase();
        GridPane grid = new GridPane(); 
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));
        
        Scene addStaffScene = new Scene(grid, 800, 600);
         
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        grid.add(btnLogOut, 3, 0);
        
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            ManagerInterface man = new ManagerInterface();
            man.managerMode(window);
        });
        grid.add(btnBack, 2, 0);

        Label lblUsername = new Label("Username");
        grid.add(lblUsername, 0, 0);
        
        Label lblFName = new Label("First Name");
        grid.add(lblFName, 0, 1);
        
        Label lblLName = new Label("Last Name");
        grid.add(lblLName, 0, 2);
        
        Label lblGender = new Label("Gender");
        grid.add(lblGender, 0, 3);
        
        Label lblPassword = new Label("Password");
        grid.add(lblPassword, 0, 4);
        
        Label lblAddress = new Label("Address");
        grid.add(lblAddress, 0, 5);
        
        Label lblCountry = new Label("Country");
        grid.add(lblCountry, 0, 6);
        
        Label lblStaffType = new Label("Staff Type");
        grid.add(lblStaffType, 0, 7);
        
        Label lblStaffDept = new Label("Staff Department");
        grid.add(lblStaffDept, 0, 8);
        
        Label lblStaffDOB = new Label("Staff Date of Birth");
        grid.add(lblStaffDOB, 0, 9);
        
        TextField txtFName = new TextField();
        grid.add(txtFName, 1, 1);
        
        TextField txtLName = new TextField();
        grid.add(txtLName, 1, 2);
        
        TextField txtUserName = new TextField();
        grid.add(txtUserName, 1, 0);
        txtUserName.setPromptText("Enter Staff Username");
  
        HBox group = new HBox();     
        final ToggleGroup genderGroup = new ToggleGroup();

        RadioButton male = new RadioButton("Male");
        male.setToggleGroup(genderGroup);
        male.setSelected(false);
        male.setPadding(new Insets(5,5,5,5));

        RadioButton female = new RadioButton("Female");
        female.setToggleGroup(genderGroup);
        female.setSelected(false);
        female.setPadding(new Insets(5,5,5,5));
        group.getChildren().addAll(male, female);
        grid.add(group, 1, 3);
        
        HBox group2 = new HBox();     
        final ToggleGroup TypeGroup = new ToggleGroup();

        RadioButton staff = new RadioButton("Staff");
        staff.setToggleGroup(TypeGroup);
        staff.setSelected(false);
        staff.setPadding(new Insets(5,5,5,5));

        RadioButton manager = new RadioButton("Manager");
        manager.setToggleGroup(TypeGroup);
        manager.setSelected(false);
        manager.setPadding(new Insets(5,5,5,5));
        
        group2.getChildren().addAll(staff, manager);
        grid.add(group2, 1, 7);
        
        PasswordField pass = new PasswordField();
        grid.add(pass,1 , 4);
        
        TextArea txtAddress = new TextArea();
        grid.add(txtAddress, 1, 5);
        txtAddress.setPrefSize(200, 100);

        countryList();
        final ComboBox countryList = new ComboBox(countries);
        countryList.setPromptText("Select Your Country");
        grid.add(countryList, 1, 6);
        Label confirm = new Label();
        ObservableList<String> departments = FXCollections.observableArrayList(
                "Room Booking",
                "Cafeteria",
                "Book Shop",
                "Transport",
                "Internet Access"
        );
        final ComboBox staffDept = new ComboBox(departments);
        staffDept.setPromptText("Select Your Department");
        grid.add(staffDept, 1, 8);
        
        DatePicker dobPick = new DatePicker();
        
        dobPick.setOnAction(e -> {
             LocalDate date = dobPick.getValue();
            dob = date.toString();
            System.out.println("Selected date: " + dob);
        });
        grid.add(dobPick, 1, 9);
        
        Button btnSubmit = new Button("Submit Details");
        btnSubmit.setOnAction(e -> {
            addStaff(txtUserName, txtFName, txtLName, pass, 
            male, female, staff, manager, countryList,
            staffDept, confirm, txtAddress);
        });
        grid.add(btnSubmit, 2 , 11);
        grid.add(confirm, 2, 12);
        
        
        addStaffScene.getStylesheets().add(AddStaff.class.getResource("HMStyles.css").toExternalForm());
        window.setScene(addStaffScene);
        window.show();
    
    }
    
    void addStaff(TextField code, TextField fname , TextField lname, PasswordField pass, 
            RadioButton male, RadioButton female, RadioButton staff, RadioButton manager, ComboBox country,
            ComboBox department, Label confirm, TextArea txtAddress){
        try{
            
             String stafID = code.getText();
             String staffFName = fname.getText();
             String staffLName = lname.getText();
             String staffPass = pass.getText();
             String staffGender = " ";
             
             if(male.isSelected()){
                    staffGender = "M";
                }
                else if(female.isSelected()){
                    staffGender  = "F";
                }
                else{
                    System.out.println("None Selected");
            }
            String sql = " ";
            String sql2 = " ";
            sql = "INSERT INTO staff "
                 + "VALUES('"+ stafID + "','" + staffFName + "','" + staffLName + "','"+ dob +"','"+ staffGender +"','" + 
                    staffPass +"','"+department.getValue().toString() +"','" + txtAddress.getText()+ "','" + country.getValue().toString() + "');";


            sql2 = "INSERT INTO manager "
                 + "VALUES('"+ stafID + "','" + "No Pic "+ "','" + staffFName + "','" + staffLName + "','"+ dob +"','"+ staffGender +"','" + 
                    staffPass +"','" + department.getValue().toString() +"','" + txtAddress.getText()+ "','" + country.getValue().toString() + "');";
  
            System.out.println(sql);
            System.out.println(sql2);
            
            stmt = conn.createStatement(); 
            //stmt2 = conn2.createStatement(); 
            if(staff.isSelected()){
                stmt.executeUpdate(sql);
            }
            else{
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql2);
            }
            
            System.out.println("Inserted records into the table..." + sql);
            confirm.setText("Succesfully Added");
        }
        catch(Exception s){
            System.out.println("Error In Adding Staff");
            confirm.setText("Error In Adding Staff");
        }
    }
    
    public void countryList(){
       countries = FXCollections.observableArrayList(
                        "Afghanistan",
                        "Albania",
                        "Algeria",
                        "Andorra",
                        "Angola",
                        "Antigua and Barbuda",
                        "Argentina",
                        "Armenia",
                        "Australia",
                        "Austria",
                        "Azerbaijan",
                        "Bahamas",
                        "Bahrain",
                        "Bangladesh",
                        "Barbados ",
                        "Belarus ",
                        "Belgium ",
                        "Belize ",
                        "Benin ",
                        "Bhutan ",
                        "Bolivia ",
                        "Bosnia and Herzegovina ",
                        "Botswana",
                        "Brazil ",
                        "Brunei ",
                        "Bulgaria ",
                        "Burkina Faso ",
                        "Burundi ",
                        "Cabo Verde ",
                        "Cambodia ",
                        "Cameroon ",
                        "Canada ",
                        "Central African Republic (CAR) ",
                        "Chad ",
                        "Chile ",
                        "China ",
                        "Colombia ",
                        "Comoros ",
                        "Democratic Republic of the Congo ",
                        "Republic of the Congo ",
                        "Costa Rica ",
                        "Cote d'Ivoire ",
                        "Croatia ",
                        "Cuba ",
                        "Cyprus ",
                        "Czech Republic ",
                        "Denmark ",
                        "Djibouti ",
                        "Dominica ",
                        "Dominican Republic ",
                        "Ecuador ",
                        "Egypt ",
                        "El Salvador ",
                        "Equatorial Guinea ",
                        "Eritrea ",
                        "Estonia ",
                        "Ethiopia ",
                        "Fiji ",
                        "Finland ",
                        "France ",
                        "Gabon ",
                        "Gambia ",
                        "Georgia ",
                        "Germany ",
                        "Ghana ",
                        "Greece ",
                        "Grenada ",
                        "Guatemala ",
                        "Guinea ",
                        "Guinea-Bissau ",
                        "Guyana ",
                        "Haiti ",
                        "Honduras ",
                        "Hungary ",
                        "Iceland ",
                        "India ",
                        "Indonesia ",
                        "Iran ",
                        "Iraq ",
                        "Ireland ",
                        "Israel ",
                        "Italy ",
                        "Jamaica ",
                        "Japan ",
                        "Jordan ",
                        "Kazakhstan ",
                        "Kenya ",
                        "Kiribati ",
                        "Kosovo ",
                        "Kuwait ",
                        "Kyrgyzstan ",
                        "Laos ",
                        "Latvia ",
                        "Lebanon ",
                        "Lesotho ",
                        "Liberia ",
                        "Libya ",
                        "Liechtenstein ",
                        "Lithuania ",
                        "Luxembourg ",
                        "Macedonia ",
                        "Madagascar ",
                        "Malawi ",
                        "Malaysia ",
                        "Maldives ",
                        "Mali ",
                        "Malta ",
                        "Marshall Islands ",
                        "Mauritania ",
                        "Mauritius ",
                        "Mexico ",
                        "Micronesia ",
                        "Moldova ",
                        "Monaco ",
                        "Mongolia ",
                        "Montenegro ",
                        "Morocco ",
                        "Mozambique ",
                        "Myanmar (Burma) ",
                        "Namibia ",
                        "Nauru ",
                        "Nepal ",
                        "Netherlands ",
                        "New Zealand ",
                        "Nicaragua ",
                        "Niger ",
                        "Nigeria ",
                        "North Korea ",
                        "Norway ",
                        "Oman ",
                        "Pakistan ",
                        "Palau ",
                        "Palestine ",
                        "Panama ",
                        "Papua New Guinea ",
                        "Paraguay ",
                        "Peru ",
                        "Philippines ",
                        "Poland ",
                        "Portugal ",
                        "Qatar ",
                        "Romania ",
                        "Russia ",
                        "Rwanda ",
                        "Saint Kitts and Nevis ",
                        "Saint Lucia ",
                        "Saint Vincent and the Grenadines ",
                        "Samoa ",
                        "San Marino ",
                        "Sao Tome and Principe ",
                        "Saudi Arabia ",
                        "Senegal ",
                        "Serbia ",
                        "Seychelles ",
                        "Sierra Leone ",
                        "Singapore ",
                        "Slovakia ",
                        "Slovenia ",
                        "Solomon Islands ",
                        "Somalia ",
                        "South Africa ",
                        "South Korea ",
                        "South Sudan ",
                        "Spain ",
                        "Sri Lanka ",
                        "Sudan ",
                        "Suriname ",
                        "Swaziland ",
                        "Sweden ",
                        "Switzerland ",
                        "Syria ",
                        "Taiwan ",
                        "Tajikistan ",
                        "Tanzania ",
                        "Thailand ",
                        "Timor-Leste ",
                        "Togo ",
                        "Tonga ",
                        "Trinidad and Tobago ",
                        "Tunisia ",
                        "Turkey ",
                        "Turkmenistan ",
                        "Tuvalu ",
                        "Uganda ",
                        "Ukraine ",
                        "United Arab Emirates (UAE)",
                        "United Kingdom (UK)",
                        "United States of America (USA)",
                        "Uruguay ",
                        "Uzbekistan ",
                        "Vanuatu ",
                        "Vatican City (Holy See) ",
                        "Venezuela ",
                        "Vietnam ",
                        "Yemen ",
                        "Zambia ",
                        "Zimbabwe "
                );
    }
}
