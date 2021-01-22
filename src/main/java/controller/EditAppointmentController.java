package controller;

import static com.charlesplett.companyscheduler.SharedUtility.TimeSpinnerValueFactory;
import static com.charlesplett.companyscheduler.SharedUtility.displayErrorAlert;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import model.Contact;
import model.Customer;
import model.User;
import static com.charlesplett.companyscheduler.SharedUtility.itemStringConverter;
import static com.charlesplett.companyscheduler.SharedUtility.myParseInt;
import model.Appointment;

/**
 * Controller class for EditAppointment. 
 * @author Charles Plett
 */
public class EditAppointmentController implements Initializable {

    private static boolean isNewAppointment = true;
    private static final ZoneId ET_ZONE_ID = ZoneId.of("America/New_York");
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    private static Appointment appointmentToChange;
    private dao.AppointmentDao appointmentDao = new dao.AppointmentDaoImpl();
    @FXML
    Label screenTitle;
    @FXML
    TextField appointmentId;
    @FXML
    TextField appointmentTitle;
    @FXML
    TextField appointmentDescription;
    @FXML
    TextField appointmentLocation;
    @FXML
    ComboBox appointmentContact;
    @FXML
    TextField appointmentType;
    @FXML
    DatePicker appointmentStartDate;
    @FXML
    DatePicker appointmentEndDate;
    @FXML
    Spinner<LocalTime> appointmentStartTime = new Spinner<>();
    @FXML
    Spinner<LocalTime> appointmentEndTime = new Spinner<>();
    @FXML
    ComboBox appointmentCustomer;
    @FXML
    ComboBox appointmentUser;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    
    
    /**
     * OnAction method to save appointment. 
     * Saves appointment after verification. Closes the window on success.
     */
    @FXML
    private void save(){
        // Convert LocalTime + LocalDate to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.of(appointmentStartDate.getValue(), appointmentStartTime.getValue());
        LocalDateTime endDateTime = LocalDateTime.of(appointmentEndDate.getValue(), appointmentEndTime.getValue());
        // Validate if time is during Business Hours
        if (!isDuringBusinessHours(startDateTime, endDateTime)){
            displayErrorAlert("Cannot save appointment", "Your appointment must be scheduled between 8:00AM and 10:00PM Eastern Time.");
            return;
        } 
        // Combine LocalDate and LocalTime to LocalDateTime and convert to Instant
        LocalDateTime startLocalDateTime = appointmentStartDate.getValue().atTime(appointmentStartTime.getValue());
        Instant startInstant = Instant.from(startLocalDateTime.atZone(DEFAULT_ZONE_ID));
        LocalDateTime endLocalDateTime = appointmentEndDate.getValue().atTime(appointmentEndTime.getValue());
        Instant endInstant = Instant.from(endLocalDateTime.atZone(DEFAULT_ZONE_ID));
        int customerId = ((Customer) appointmentCustomer.getValue()).getId();
        // Check if customer has overlapping appointments
        int appointmentIdInt = myParseInt(appointmentId.getText());
        if (appointmentDao.isOverlappingAppointment(customerId, startInstant, endInstant, appointmentIdInt)){
            displayErrorAlert("Cannot save appointment", "Customer may not have overlapping appointments.");
            return;
        }
        // Write to database
        updateAppointment();
        exit();
        
    }
    
    /**
     * OnAction method to close window. 
     * Does not save any data. Closes window after confirmation.
     */
    @FXML
    private void cancel(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setContentText("Are you sure you want to leave? All changes will be lost.");
        alert.showAndWait();
        if(alert.getResult().toString().contains("CANCEL_CLOSE")){
            return;
        }
        exit();
    }

    private boolean isDuringBusinessHours(LocalDateTime start, LocalDateTime end){
        // Convert LocalDateTime to ZonedDateTime Eastern Time
        ZonedDateTime etStartTime = start.atZone(DEFAULT_ZONE_ID).withZoneSameInstant(ET_ZONE_ID);
        ZonedDateTime etEndTime = end.atZone(DEFAULT_ZONE_ID).withZoneSameInstant(ET_ZONE_ID);
        // Return false if start is after end time, or if appointment spans multiple days.
        if (etStartTime.isAfter(etEndTime) || !etEndTime.truncatedTo(ChronoUnit.DAYS).isEqual(etStartTime.truncatedTo(ChronoUnit.DAYS))){
            return false;
        }
        int etStartHour = etStartTime.getHour();
        int etEndHour = etEndTime.getHour();
        int etEndMinute = etEndTime.getMinute();
        // Check time against Business hours 8:00ET - 22:00ET
        boolean result = (etStartHour >= 8 && etEndHour < 22 || etEndHour == 22 && etEndMinute <= 0);
        return result;
    }
    
    private void updateAppointment(){
        if(isNewAppointment){
            appointmentToChange = new Appointment();
        }
        appointmentToChange.setTitle(appointmentTitle.getText());
        appointmentToChange.setDescription(appointmentDescription.getText());
        appointmentToChange.setLocation(appointmentLocation.getText());
        appointmentToChange.setType(appointmentType.getText());
        // Combine LocalDate and LocalTime to LocalDateTime
        LocalDateTime startLocalDateTime = appointmentStartDate.getValue().atTime(appointmentStartTime.getValue());
        // Convert LocalDateTime to Instant
        Instant startInstant = Instant.from(startLocalDateTime.atZone(DEFAULT_ZONE_ID));
        appointmentToChange.setStart(startInstant);
        LocalDateTime endLocalDateTime = appointmentEndDate.getValue().atTime(appointmentEndTime.getValue());
        Instant endInstant = Instant.from(endLocalDateTime.atZone(DEFAULT_ZONE_ID));
        appointmentToChange.setEnd(endInstant);
        appointmentToChange.setLastUpdate(Instant.now());
        appointmentToChange.setLastUpdatedBy(LoginController.getAuthenticatedUser());
        Customer customer = (Customer) appointmentCustomer.getValue();
        appointmentToChange.setCustomerId(customer.getId());
        User user = (User) appointmentUser.getValue();
        appointmentToChange.setUserId(user.getId());
        Contact contact = (Contact) appointmentContact.getValue();
        appointmentToChange.setContactId(contact.getId());
        
        if(isNewAppointment){
            appointmentToChange.setCreateDate(Instant.now());
            appointmentToChange.setCreatedBy(LoginController.getAuthenticatedUser());
            appointmentDao.createAppointment(appointmentToChange);
        } else {
            appointmentDao.updateAppointment(appointmentToChange);
        }
    }
    
    private void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Setter to tell controller if this is a new appointment. 
     * The form will be updated accordingly.
     * @param isNewAppointment Boolean if this is a new appointment.
     */
    public static void setIsNewAppointment(boolean isNewAppointment) {
        EditAppointmentController.isNewAppointment = isNewAppointment;
    }
    
    /**
     * Setter to tell controller which appointment to change. 
     * This appointment is changed if isNewAppointment is set to false.
     * @param selectedAppointment The Appointment to change.
     */
    public static void setAppointmentToChange(Appointment selectedAppointment) {
        appointmentToChange = selectedAppointment;
    }
        
    /**
     * Method called on class load. Initializes all fields and instantiates data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
// Init LocalTime Spinners
        SpinnerValueFactory<LocalTime> startFactory = new TimeSpinnerValueFactory();
        SpinnerValueFactory<LocalTime> endFactory = new TimeSpinnerValueFactory();
        appointmentStartTime.setValueFactory(startFactory);
        appointmentEndTime.setValueFactory(endFactory);
        
// Init Customer ComboBox
        dao.CustomerDao customerDao = new dao.CustomerDaoImpl();
        ObservableList<Customer> customers = customerDao.getAllCustomers();
        appointmentCustomer.setItems(customers);
        StringConverter customerConverter = itemStringConverter(customers);
        appointmentCustomer.setConverter(customerConverter);

// Init User ComboBox        
        dao.UserDao userDao = new dao.UserDaoImpl();
        ObservableList<User> users = userDao.getAllUsers();
        appointmentUser.setItems(users);
        StringConverter userConverter = itemStringConverter(users);
        appointmentUser.setConverter(userConverter);
        
// Init Contact
        dao.ContactDao contactDao = new dao.ContactDaoImpl();
        ObservableList<Contact> contacts = contactDao.getAllContacts();
        appointmentContact.setItems(contacts);
        StringConverter contactConverter = itemStringConverter(contacts);
        appointmentContact.setConverter(contactConverter);

        if (isNewAppointment){
            appointmentId.setText("Automatically Assigned");
            // https://stackoverflow.com/questions/36417317/localdatetime-to-zoneddatetime
            // Get current UTC date/time with Instant
            Instant instant = Instant.now();
            // Convert Instant (UTC) to Local Time
            LocalDate localDateNow = LocalDate.from(instant.atZone(DEFAULT_ZONE_ID));
            LocalTime localStartTime = LocalTime.from(instant.atZone(DEFAULT_ZONE_ID).truncatedTo(ChronoUnit.HOURS).plusHours(1));
            screenTitle.setText("New Appointment");
            appointmentStartTime.getValueFactory().setValue(localStartTime);
            appointmentEndTime.getValueFactory().setValue(localStartTime.plusHours(1));
            appointmentStartDate.setValue(localDateNow);
            appointmentEndDate.setValue(localDateNow);
        } else {
            screenTitle.setText(appointmentToChange.getTitle());
            // Set Fields from Database
            appointmentId.setText(String.valueOf(appointmentToChange.getId()));
            appointmentTitle.setText(appointmentToChange.getTitle());
            appointmentDescription.setText(appointmentToChange.getDescription());
            appointmentLocation.setText(appointmentToChange.getLocation());
            Contact contact = contactDao.getContact(appointmentToChange.getContactId());
            appointmentContact.setValue(contact);
            appointmentType.setText(appointmentToChange.getType());
            appointmentStartDate.setValue(LocalDate.from(appointmentToChange.getStart().atZone(DEFAULT_ZONE_ID)));
            appointmentEndDate.setValue(LocalDate.from(appointmentToChange.getEnd().atZone(DEFAULT_ZONE_ID)));
            appointmentStartTime.getValueFactory().setValue(LocalTime.from(appointmentToChange.getStart().atZone(DEFAULT_ZONE_ID)));
            appointmentEndTime.getValueFactory().setValue(LocalTime.from(appointmentToChange.getEnd().atZone(DEFAULT_ZONE_ID)));
            Customer customer = customerDao.getCustomer(appointmentToChange.getCustomerId());
            appointmentCustomer.setValue(customer);
            User user = userDao.getUser(appointmentToChange.getUserId());
            appointmentUser.setValue(user);
        }
    }
}
