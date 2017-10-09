/*
    CS241 Assignment 2
    Author: Mohammed Moishin for Super-Coderz 
 */
package metropolishotelhms;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MetropolisHotelHMS extends Application {
    Stage window = new Stage();
    @Override
    public void start(Stage primaryStage) { 
        window = primaryStage;
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        window.getIcons().add(icon);
        window.setTitle("Metropolis Hotel");
        window.setMaximized(false);  
        
        //Login class called
        Login login = new Login();
        login.start(window);
        window.show();
    }
    /**
     * @param args the command line arguments
    */
    public static void main(String[] args) {
        launch(args);
    }
    

    
}
