package controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static com.charlesplett.companyscheduler.SharedUtility.*;
import model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Controller class for Login. 
 * @author Charles Plett
 */
public class LoginController implements Initializable {
    
    private String invalidLogin;
    private static String authenticatedUser;
    private static int authenticatedUserId;
    @FXML
    Label loginLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Label userCountry;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Button cancelButton;
    
    
    /**
     * OnAction method to login. 
     * Validates login credentials. Opens MainScreen on success. 
     * Displays error on failure. All login attempts are logged to text file.
     */
    @FXML
    private void login(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        dao.UserDao userDao = new dao.UserDaoImpl();
        User user = userDao.authenticateUser(username, password);
        if (user != null){
            // Login Success. Update log.
            logLoginAttempt(username, true);
            authenticatedUser = user.getName();
            authenticatedUserId = user.getId();
            
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
                showChildWindow(root, "CS - Company Scheduler", 1420, 500, false);
                exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        // Login Failed. Update log.
        logLoginAttempt(username, false);
        loginFailed();
    }
    
    private void logLoginAttempt(String user, boolean isValid){
        String msg = " gave invalid log-in at ";
        if (isValid){
            msg = " successfully logged in at ";
        }
        // Time in UTC
        String now = Instant.now().toString();
        String result = "User " + user + msg + now + " UTC\n";
        // Update log file
        writeLogFile(result);
    }
    
    private void writeLogFile(String str){
        try{
            String fileName = "login_activity.txt";
            Path path = Paths.get(fileName);
            byte[] strToBytes = str.getBytes();
            Files.write(path, strToBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * OnAction method to close window. 
     * Closes window immediately. 
     */
    @FXML
    private void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    private void setLanguage(ResourceBundle rb){
        loginLabel.setText(rb.getString("login"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        userCountry.setText(rb.getString("usercountry") + ": " + Locale.getDefault().getCountry() + " (" + ZoneId.systemDefault() + ")");
        invalidLogin = rb.getString("invalidlogin");
        loginButton.setText(rb.getString("login"));
        cancelButton.setText(rb.getString("cancel"));
        String country = Locale.getDefault().getCountry();
    }
    
    private void loginFailed(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(invalidLogin);
        alert.setContentText(invalidLogin);
        alert.showAndWait();
    }

    /**
     * Getter to return authenticated user. 
     * @return Returns authenticated username as String.
     */
    public static String getAuthenticatedUser() {
        return authenticatedUser;
    }
    
    /**
     * Getter to return authenticated user ID.
     * @return Returns authenticated user ID as int.
     */
    public static int getAuthenticatedUserId() {
        return authenticatedUserId;
    }
    
    /**
     * Method called on class load. Sets the language of the login controller to either English or French depending on system locale.
     * @param rb The ResourceBundle containing translated strings.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(rb);
    }
}
