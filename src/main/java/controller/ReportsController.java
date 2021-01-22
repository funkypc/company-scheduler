package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller class for Reports. 
 * @author Charles Plett
 */
public class ReportsController implements Initializable {

    @FXML
    Label screenTitle;
    @FXML
    ComboBox reportComboBox;
    @FXML
    TextArea reportTextArea;
    @FXML
    Button exitButton;
    
    
    /**
     * OnAction method to generate report. 
     * Displays the data for the selected report in the TextArea.
     */
    @FXML
    private void generateReport(){
        String reportToDisplay = reportComboBox.getValue().toString();
        String report = "";
        dao.AppointmentDao appointmentDao = new dao.AppointmentDaoImpl();
        if (reportToDisplay.contains("Number of Appointments")){
            // Total number of customer appointments by type and month
            report = appointmentDao.getAppointmentReportByTypeAndMonth();
        } else if (reportToDisplay.contains("Contact Schedules")){
            // Schedule of each contact that includes:
            // appointmentId, Title, type, description, start date and time, end date and time, customerID
            report = appointmentDao.getAppointmentReportByContact();
        } else if (reportToDisplay.contains("Customer Schedules")){
            // Schedule of each customer
            report = appointmentDao.getAppointmentReportByCustomer();
        }
        reportTextArea.setText(reportToDisplay + " Report:" + "\n\n" + report);
    }
    
    /**
     * OnAction method to close window. 
     */
    @FXML
    private void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
        
    /**
     * Method called on class load. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
