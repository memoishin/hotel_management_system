/*
    Customer Registration Module : Author Mohammed Moishin for Super-Coderz
 */
package metropolishotelhms;

import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * @author Mohammed Moishin
 */
public class CustomerRegistration extends DatabaseMethods{
    //Connection con = null;;
    //Statement stm = null;
    String dob;
    ObservableList<String> countries;
    public void CustomerRegister(Stage window){
       
        connectDatabase();
        GridPane RegForm = new GridPane();
        Scene register = new Scene(RegForm,800,600);
        window.setMaximized(false);
        RegForm.setAlignment(Pos.CENTER);
        RegForm.setHgap(10);
        RegForm.setVgap(10);
        RegForm.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));

        
        RegForm.setId("pane");
        Button btnBack = new Button("Go Back");
        btnBack.setOnAction(e -> {
            MainMenu main = new MainMenu();
            main.start(window);
        });
        RegForm.add(btnBack, 2, 0);
        btnBack.setTextFill(Color.CADETBLUE);
        btnBack.setPadding(new Insets(5,5,5,5));
        
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e -> {
            Login loginPage = new Login();
            loginPage.start(window);
        });
        RegForm.add(btnLogOut, 3, 0);
        btnLogOut.setTextFill(Color.TOMATO);
        btnLogOut.setPadding(new Insets(5,5,5,5));
        

        
        Label lblPrompt = new Label();
        lblPrompt.setText("Please Enter the following Details about the customer");
        RegForm.add(lblPrompt, 1, 1);
        lblPrompt.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        lblPrompt.setPadding(new Insets(10,10,10,10));
        
        Label lblFName = new Label("First Name: ");
        RegForm.add(lblFName, 0, 2);
        lblFName.setPadding(new Insets(10,10,10,10));
        
        TextField txtFName = new TextField();
        txtFName.setMinWidth(150);
        txtFName.setMaxWidth(250);
        RegForm.add(txtFName, 1, 2);
        
        TextField txtRcode = new TextField();
        txtRcode.setMinWidth(150);
        txtRcode.setMaxWidth(250);
        RegForm.add(txtRcode, 2, 2);
        txtRcode.setPromptText("Registration Code");
        
        Button btnreg = new Button("Get Details");
        RegForm.add(btnreg, 3, 2);
        
        
        Label lblLName = new Label("Last Name: ");
        lblLName.setPadding(new Insets(10,10,10,10));
        RegForm.add(lblLName, 0, 3);
        
        TextField txtLName = new TextField();
        txtLName.setMinWidth(150);
        txtLName.setMaxWidth(250);
        RegForm.add(txtLName, 1, 3);
        
        Label lblPhone = new Label("Phone: ");
        RegForm.add(lblPhone, 0, 4);
        lblPhone.setPadding(new Insets(10,10,10,10));

        
        TextField txtPhone = new TextField();
        txtPhone.setMinWidth(150);
        txtPhone.setMaxWidth(250);
        RegForm.add(txtPhone, 1, 4);
        
        Label lblEmail = new Label("Email: ");
        RegForm.add(lblEmail, 0, 5);
        lblEmail.setPadding(new Insets(10,10,10,10));
        
        TextField txtEmail = new TextField();
        txtEmail.setMinWidth(150);
        txtEmail.setMaxWidth(250);
        RegForm.add(txtEmail, 1, 5);
        
        Label lblCountry = new Label("Country: ");
        RegForm.add(lblCountry, 0, 6);
        lblCountry.setPadding(new Insets(10,10,10,10));
        
        TextField txtCountry = new TextField();
        txtCountry.setMinWidth(150);
        txtCountry.setMaxWidth(250);
        
        countryList();
        final ComboBox countryList = new ComboBox(countries);
        countryList.setPromptText("Select Your Country");
        RegForm.add(countryList, 1, 6);
        
        Label lblAddress = new Label("Address: ");
        RegForm.add(lblAddress, 0, 7);
        lblAddress.setPadding(new Insets(10,10,10,10));
        
        TextArea txtAddress = new TextArea();
        txtAddress.setMinWidth(150);
        txtAddress.setMaxWidth(250);
        RegForm.add(txtAddress, 1, 7);
        
        Label lblGender = new Label("Gender: ");
        RegForm.add(lblGender, 0, 8);
        lblGender.setPadding(new Insets(10,10,10,10));
        
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
        RegForm.add(group, 1, 8);

        Label lblDOB = new Label("Date of Birth: ");
        RegForm.add(lblDOB, 0, 9);
        lblDOB.setPadding(new Insets(10,10,10,10));
        
        DatePicker dobPick = new DatePicker();
        
        dobPick.setOnAction(event -> {
             LocalDate date = dobPick.getValue();
            dob = date.toString();
            System.out.println("Selected date: " + dob);
        });
        RegForm.add(dobPick, 1, 9);
        
        Label lblPassword = new Label("Password: ");
        RegForm.add(lblPassword, 0, 10);
        lblPassword.setPadding(new Insets(10,10,10,10));
        
        PasswordField userPass = new PasswordField();
        userPass.setMinWidth(150);
        userPass.setMaxWidth(150);
        RegForm.add(userPass, 1, 10);
        
        Label lblConfirm = new Label();
        RegForm.add(lblConfirm, 1, 11);
        
        Button btnReset = new Button("Reset");
        btnReset.setOnAction(e -> {
            txtFName.clear();
            txtLName.clear();
            txtPhone.clear();
            txtEmail.clear();
            txtAddress.clear();
        });
        RegForm.add(btnReset, 3, 11);
        
        btnreg.setOnAction(e -> {
            getRegDetails(txtRcode, txtFName, txtLName, txtEmail, userPass,txtPhone,txtAddress);     
        });
       
        Button btnSubmit = new Button("Submit");
        RegForm.add(btnSubmit, 2, 11);
        btnSubmit.setOnAction(e -> {
            int phone = Integer.parseInt(txtPhone.getText());
            if(phone < 0){
                lblConfirm.setText("Invalid Data Entry - Phone Number");
            }
            else{
                submitClicked(txtFName, txtLName, txtPhone, txtEmail, male, female, userPass, txtCountry, txtAddress, txtCountry, lblConfirm, countryList);                
            }
            
        });

        window.setScene(register);
        register.getStylesheets().add(CustomerRegistration.class.getResource("HMStyles.css").toExternalForm());
    }
    
    public void submitClicked(TextField txtFName,TextField txtLName,TextField txtPhone, TextField txtEmail, RadioButton male,
                                RadioButton female, PasswordField pass, TextField country, TextArea txtAddress,
                                TextField cntry, Label confirm, ComboBox countryList){
    try{
        stmt = conn.createStatement();
        String fname = txtFName.getText();
        String lname = txtLName.getText();
        String phone = txtPhone.getText();
        String gender = " ";
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String password = pass.getText();
        String Country = countryList.getValue().toString();
        
        System.out.println(Country);
        
        String sql2 = "SELECT CUS_CODE FROM customer;";
        ResultSet rs = null;
        int code = 0;
        rs = stmt.executeQuery(sql2);
             while(rs.next()){
             code = rs.getInt("CUS_CODE");
             System.out.println(code);
        }
        code++;
        System.out.print(code);
        if(male.isSelected()){
            gender = "M";
        }
        else if(female.isSelected()){
            gender  = "F";
        }
        else{
            System.out.println("None Selected");
        }
        
        String sqlInsert;
        sqlInsert = "INSERT INTO customer " 
                 + "VALUES('" + code + "','" + "0" + "','" + fname +"','" + lname + "','"+ dob + "','" + gender + "','" + address +"','"+ email + "','" + password +"','"+ phone + "','" + Country + "');";

        System.out.println(sqlInsert);
        stmt.executeUpdate(sqlInsert);
        System.out.println("Inserted records into the table..." + sqlInsert);
        confirm.setText("Your customer code is : cus" + code + ". \n Registration Complete.");
        }
        catch(Exception z){
            System.out.println("Error 3");
            confirm.setText("Error in Form Submission:\n Please Try Again");
        }
        finally{
            try{
                if(stmt!=null)
                   conn.close();
             }catch(SQLException se){
             }// do nothing
             try{
                if(conn!=null)
                   conn.close();
             }catch(SQLException se){
             }//end finally try
             txtFName.clear();
             txtLName.clear();
             txtPhone.clear();
             txtEmail.clear();
             txtAddress.clear();
             pass.clear();
             cntry.clear();
             
          }//end try
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
    
    void getRegDetails(TextField code, TextField fname,  TextField lname, TextField email, PasswordField pass,
            TextField phone, TextArea address){
        ResultSet rs = null;
        String sql4 = "SELECT * FROM registration WHERE REG_CODE = " + Integer.parseInt(code.getText()) + ";";
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql4);
            
            while(rs.next()){
                fname.setText(rs.getString("firstname"));
                lname.setText(rs.getString("lastname"));
                pass.setText(rs.getString("password"));
                email.setText(rs.getString("email"));
                phone.setText(rs.getString("phone"));
                address.setText(rs.getString("address"));
            }
        }
        catch(Exception r){
            System.out.println("Error in getting data.");
        }
    }
}



