package com.charlesplett.companyscheduler;

import dao.DBConnection;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** 
 * Base application Class. 
 * @author Charles Plett
 */
public class App extends Application {
    
    private static Scene scene;
    
    /** Creates the stage and scene of the Application. Invoked on App run after main. */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/views/Login"), 380, 250);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/CS256.png")));
        stage.setTitle("CS");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("rb");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), rb);
        return fxmlLoader.load();
    }
    
    /**
     * Main method to launch application. Invoked on App run.
     * @param args Command line arguments to pass to application.
     */
    public static void main(String[] args) {
        // Set Locale to France (French) for testing.
//        Locale.setDefault(Locale.FRANCE);
        // Open database here
        DBConnection.connect();
        launch(args);
        // Close database here
        DBConnection.close();
    }
}
