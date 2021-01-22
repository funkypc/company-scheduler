package controller;

import static com.charlesplett.companyscheduler.SharedUtility.showChildWindow;
import static com.charlesplett.companyscheduler.SharedUtility.toLocalDateTime;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.paint.Color.*;
import javafx.stage.Stage;
import model.Appointment;

/**
 * Controller class for MainScreen. 
 * @author Charles Plett
 */
public class MainScreenController implements Initializable {

    private dao.AppointmentDao appointmentDao;
    private ObservableList<Appointment> appointments;
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    @FXML
    Label appointmentAlert;
    @FXML
    RadioButton isWeek;
    @FXML
    RadioButton isMonth;
    @FXML
    TableView<Appointment> appointmentTableView;
    @FXML
    TableColumn appointmentId;
    @FXML
    TableColumn appointmentTitle;
    @FXML
    TableColumn appointmentDescription;
    @FXML
    TableColumn appointmentLocation;
    @FXML
    TableColumn appointmentContact;
    @FXML
    TableColumn appointmentType;
    @FXML
    TableColumn appointmentStart;
    @FXML
    TableColumn appointmentEnd;
    @FXML
    TableColumn appointmentCustomerId;
    @FXML
    TableColumn appointmentCustomerName;
    @FXML
    TableColumn appointmentUser;
    @FXML
    ToggleGroup filterGroup;
    @FXML
    Button appointmentAddButton;
    @FXML
    Button appointmentModifyButton;
    @FXML
    Button appointmentDeleteButton;
    
    
    /**
     * OnAction method to edit appointment. 
     * Opens the EditAppointment child window. 
     * In the case the Add Appointment button is clicked, the EditAppointment form is set to create a new appointment.
     * If the Edit Appointment button is clicked, the EditAppointment form is populated with the selected appointment.
     * Refreshes tables after returning.
     */
    @FXML
    private void editAppointment(ActionEvent event){
        String eventString = event.toString();
        String title = "Add Appointment";
        if (eventString.contains("appointmentAddButton")){
            EditAppointmentController.setIsNewAppointment(true);
            EditAppointmentController.setAppointmentToChange(null);
        } else if (eventString.contains("appointmentModifyButton")){
            title = "Edit Appointment";
            EditAppointmentController.setIsNewAppointment(false);
            // Pass Selected Appointment to Child Window
            Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
            if(selectedAppointment == null){
                return;
            } else {
                EditAppointmentController.setAppointmentToChange(selectedAppointment);
            }
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/views/EditAppointment.fxml"));
            showChildWindow(root, title, 365, 500, true);
            // Update TableView
            appointments = appointmentDao.getAllAppointments();
            appointmentTableView.getItems().setAll(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * OnAction method to delete the selected appointment. 
     * Requests confirmation before deleting the selected appointment. 
     * Does nothing if no appointment is selected.
     * Refreshes tables after deleting.
     */
    @FXML
    private void deleteAppointment(){
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        int aId = selectedAppointment.getAppointmentId();
        String aType = selectedAppointment.getType();
        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setContentText("Are you sure you want to delete Appointment - ID: " + aId + " - Type: " + aType + "?");
            alert.showAndWait();
            if(alert.getResult().toString().contains("CANCEL_CLOSE")){
                return;
            }
            appointmentDao.deleteAppointment(selectedAppointment);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Deleted");
            alert.setContentText("Appointment - ID: " + aId + " - Type: " + aType + " was successfully cancelled!");
            alert.show();
            // Update TableView
            appointments = appointmentDao.getAllAppointments();
            appointmentTableView.getItems().setAll(appointments);
        }
    }
    
    /**
     * OnAction method to edit customer. 
     * In the case a product is selected, the EditCustomer form is populated with data of the customer associated with the selected product.
     * If no product is selected, the EditCustomer form is opened and populated to create a new customer.
     * Refreshes tables after returning.
     */
    @FXML
    private void editCustomer(){
        String title = "Manage Customers";
        // Pass customerId associated with Selected Product to Child Window
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            // New Customer
            EditCustomerController.setSelectedCustomerId(-1);
        } else {
            EditCustomerController.setSelectedCustomerId(selectedAppointment.getCustomerId());
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/views/EditCustomer.fxml"));
            showChildWindow(root, title, 1466, 380, true);
            // Update TableView
            appointments = appointmentDao.getAllAppointments();
            appointmentTableView.getItems().setAll(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * OnAction method to open the reports window. Refreshes tables after returning.
     */
    @FXML
    private void showReports(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/views/Reports.fxml"));
            showChildWindow(root, "Reports", 1000, 380, false);
            // Update TableView
            appointments = appointmentDao.getAllAppointments();
            appointmentTableView.getItems().setAll(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adds listener to toggle group. 
     * <p><strong>A lambda expression is used in this method.</strong></p>
     * <p>
     * ChangeListener is a functional interface. A lambda expression is used in 
     * this method to simplify and cleanup the code.
     * </p>
     */
    private void addListener(){
        filterGroup.selectedToggleProperty().addListener((o) -> {
            RadioButton selectedRadioButton = (RadioButton) filterGroup.getSelectedToggle();
            String selectedRadio = selectedRadioButton.getText();
            if (selectedRadio.contains("Week")){
                appointments = appointmentDao.getWeekAppointments();
            } else if (selectedRadio.contains("Month")){
                appointments = appointmentDao.getMonthAppointments();
            } else {
                appointments = appointmentDao.getAllAppointments();
            }
            appointmentTableView.getItems().setAll(appointments);
        });
    }
    
    /**
     * Method called on class load. Initializes and populates the tables with data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get Appointments
        appointmentDao = new dao.AppointmentDaoImpl();
        appointments = appointmentDao.getAllAppointments();
        // Init Table
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        // Populate Tables
        appointmentTableView.getItems().setAll(appointments);
        // Add Listener to Radios to filter list
        this.addListener();
        toLocalDateTime(appointmentStart);
        toLocalDateTime(appointmentEnd);
        // Populate Upcoming Appointment Alert
        Appointment nextAppointment = appointmentDao.getUpcomingAppointment(LoginController.getAuthenticatedUserId());
        String appointmentAlertMessage;
        String userName = LoginController.getAuthenticatedUser();
        if(nextAppointment == null){
            // Set label message for no upcoming appointments
            appointmentAlertMessage = "There are no upcomming appointments for " + userName + ".";
            appointmentAlert.setTextFill(BLACK);
        } else {
            int id = nextAppointment.getId();
            String title = nextAppointment.getTitle();
            // Convert Instant (UTC) to Local Date and Local Time
            Instant start = nextAppointment.getStart();
            LocalDate localStartDate = LocalDate.from(start.atZone(DEFAULT_ZONE_ID));
            LocalTime localStartTime = LocalTime.from(start.atZone(DEFAULT_ZONE_ID));
            String parsedStart = localStartTime + " on " + localStartDate;
            // Set label message to next appointment
            String appointmentMessageShort = "ID: " + id + " - " + title + " at " + parsedStart;
            appointmentAlertMessage = "Upcomming Appointment for " + userName + " - ID: " + id + " - " + title + " at " + parsedStart;
            appointmentAlert.setTextFill(RED);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcomming Appointment");
            alert.setHeaderText("Upcomming Appointment");
            alert.setContentText(appointmentMessageShort);
            alert.show();
            // Keep alert on top
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);
            alertStage.toFront();
        }
        appointmentAlert.setText(appointmentAlertMessage);
    }
}
