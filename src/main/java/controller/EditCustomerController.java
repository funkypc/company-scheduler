package controller;

import static com.charlesplett.companyscheduler.SharedUtility.itemStringConverter;
import static com.charlesplett.companyscheduler.SharedUtility.showChildWindow;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

/**
 * Controller class for EditCustomer. 
 * @author Charles Plett
 */
public class EditCustomerController implements Initializable {

    private static int selectedCustomerId = -1;
    private static final ZoneId ET_ZONE_ID = ZoneId.of("America/New_York");
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    private static final Customer newCustomer = new Customer();
    private static dao.CustomerDao customerDao = new dao.CustomerDaoImpl();
    private static dao.CountryDao countryDao = new dao.CountryDaoImpl();
    private static dao.FirstLevelDivisionDao divisionDao = new dao.FirstLevelDivisionDaoImpl();
    private static dao.AppointmentDao appointmentDao = new dao.AppointmentDaoImpl();
    private static ObservableList<Appointment> appointmentOList = FXCollections.observableArrayList();
    
    @FXML
    Label screenTitle;
    @FXML
    ComboBox customerList;
    @FXML
    TextField customerId;
    @FXML
    TextField customerName;
    @FXML
    TextField customerAddress;
    @FXML
    TextField customerPostalCode;
    @FXML
    ComboBox customerCountry;
    @FXML
    Label customerDivisionLabel;
    @FXML
    ComboBox customerDivision;
    @FXML
    TextField customerPhoneNumber;
    @FXML
    ComboBox customerAppointmentList;
    @FXML
    Button addAppointmentButton;
    @FXML
    Button editAppointmentButton;
    @FXML
    Button deleteAppointmentButton;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    @FXML
    TableView<Customer> customerTableView;
    @FXML
    TableColumn tableCustomerId;
    @FXML
    TableColumn tableCustomerName;
    @FXML
    TableColumn tableCustomerAddress;
    @FXML
    TableColumn tableCustomerPostalCode;
    @FXML
    TableColumn tableCustomerCountry;
    @FXML
    TableColumn tableCustomerDivision;
    @FXML
    TableColumn tableCustomerPhoneNumber;
    
    
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
        if (eventString.contains("addAppointmentButton")){
            EditAppointmentController.setIsNewAppointment(true);
        } else if (eventString.contains("editAppointmentButton")){
            EditAppointmentController.setIsNewAppointment(false);
            // Pass selected Appointment to EditAppointmentController
            if (customerAppointmentList.getValue() == null){
                return;
            }
            EditAppointmentController.setAppointmentToChange((Appointment) customerAppointmentList.getValue());
            title = "Edit Appointment";
        } else if (eventString.contains("deleteAppointmentButton")){
            if (customerAppointmentList.getValue() == null){
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setContentText("Are you sure you want to delete this appointment?");
            alert.showAndWait();
            if(alert.getResult().toString().contains("CANCEL_CLOSE")){
                return;
            }
            appointmentDao.deleteAppointment((Appointment) customerAppointmentList.getValue());
            // Refresh
            customerAppointmentList.setItems(appointmentDao.getCustomerAppointments(selectedCustomerId));
            return;
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/views/EditAppointment.fxml"));
            showChildWindow(root, title, 365, 500, true);
            // Update appointment list
            if(selectedCustomerId == -1){
                customerAppointmentList.setItems(appointmentOList);
            } else {
                customerAppointmentList.setItems(appointmentDao.getCustomerAppointments(selectedCustomerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * OnAction method to save customer. 
     * Updates customer with data from fields.
     */
    @FXML
    private void save(){
        updateCustomer();
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
    
    /**
     * OnAction method to create new customer. 
     * Populates form with empty data to create new customer.
     */
    @FXML
    private void newCustomer(){
        // Set form for New Customer
        selectedCustomerId = -1;
        setNewCustomer();
        // Set focus on customerName TextField
        customerName.requestFocus();
    }
    
    /**
     * OnAction method to edit selected customer. 
     * Populates form with data from selected customer.
     */
    @FXML
    private void editCustomer(){
        // Get selected customer
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        // Update ComboBox to match selected customer
        customerList.setValue(selectedCustomer);
        // Set focus on customerName TextField
        customerName.requestFocus();
    }
    
    /**
     * OnAction method to delete customer. 
     * Deletes the selected customer after confirmation.
     */
    @FXML
    private void deleteCustomer(){
        // Get selected customer
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        String cName = selectedCustomer.getName();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete the customer " + cName + "?");
        alert.showAndWait();
        if(alert.getResult().toString().contains("CANCEL_CLOSE")){
            return;
        }
        if(appointmentDao.getCustomerAppointments(selectedCustomer.getId()).isEmpty()){
            // Perform delete
            customerDao.deleteCustomer(selectedCustomer);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Deleted");
            alert.setContentText("Customer " + cName + " was successfully deleted.");
            alert.show();
            // Refresh table and combobox
            refresh();
            return;
        }
        // Alert: Customer has appointments and cant be deleted!
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot Delete");
        alert.setContentText("Cannot delete a customer with appointments. Before deleting, you must first remove any appointments from this customer.");
        alert.show();
    }
    
    private void updateCustomer(){
        Customer customer;
        if(selectedCustomerId == -1 || customerList.getValue() == null){
            // New Customer
            customer = new Customer();
        } else {
            customer = (Customer) customerList.getValue();
        }
        customer.setCustomerName(customerName.getText());
        customer.setAddress(customerAddress.getText());
        customer.setPostalCode(customerPostalCode.getText());
        customer.setPhone(customerPhoneNumber.getText());
        customer.setLastUpdate(Instant.now());
        customer.setLastUpdatedBy(LoginController.getAuthenticatedUser());
        FirstLevelDivision division = (FirstLevelDivision) customerDivision.getValue();
        customer.setDivisionId(division.getId());
        if(selectedCustomerId == -1 || customerList.getValue() == null){
            customer.setCreateDate(Instant.now());
            customer.setCreatedBy(LoginController.getAuthenticatedUser());
            customerDao.createCustomer(customer);
        } else {
            customerDao.updateCustomer(customer);
        }
        refresh();
    }
    
    private void refresh(){
        customerTableView.getItems().setAll(customerDao.getAllCustomers());
        ObservableList<Customer> customerOList = customerDao.getAllCustomers();
        newCustomer.setCustomerId(-1);
        newCustomer.setCustomerName("New Customer");
        customerOList.add(newCustomer);
        customerList.setItems(customerOList);
    }
    
    private void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Setter to tell controller which customer to edit. 
     * @param selectedCustomerId
     */
    public static void setSelectedCustomerId(int selectedCustomerId) {
        EditCustomerController.selectedCustomerId = selectedCustomerId;
    }
    
    private void setNewCustomer(){
        customerList.setValue(newCustomer);
        customerId.setText("Automatically Assigned");
        customerName.setText("");
        customerAddress.setText("");
        customerPostalCode.setText("");
        customerCountry.setValue(countryDao.getCountry(1));
        customerDivision.valueProperty().set(null);
        customerPhoneNumber.setText("");
        appointmentOList = FXCollections.observableArrayList();
        customerAppointmentList.setItems(appointmentOList);
    }
    
    private void setSelectedCustomer(){
        customerId.setText(String.valueOf(customerDao.getCustomer(selectedCustomerId).getId()));
        customerName.setText(customerDao.getCustomer(selectedCustomerId).getName());
        customerAddress.setText(customerDao.getCustomer(selectedCustomerId).getAddress());
        customerPostalCode.setText(customerDao.getCustomer(selectedCustomerId).getPostalCode());
        int countryId = customerDao.getCustomer(selectedCustomerId).getCountryId();
        customerCountry.setValue(countryDao.getCountry(countryId));
        int divisionId = customerDao.getCustomer(selectedCustomerId).getDivisionId();
        customerDivision.setValue(divisionDao.getFirstLevelDivision(divisionId));
        customerPhoneNumber.setText(customerDao.getCustomer(selectedCustomerId).getPhone());
        // INIT all appointments associated with customer.
        customerAppointmentList.setItems(appointmentDao.getCustomerAppointments(selectedCustomerId));
    }
    
    /**
     * Adds listeners to ComboBox's. 
     * <p><strong>Lambda expressions are used in this method.</strong></p>
     * <p>
     * ChangeListener is a functional interface. Lambda expressions are used in 
     * this method to simplify and cleanup the code.
     * </p>
     */
    private void addListeners(){
        // Set Country ComboBox Listener to setup Division ComboBox
        customerCountry.getSelectionModel().selectedItemProperty().addListener((o, ol, nw) -> {
            int selectedCountryId = ((Country) customerCountry.getValue()).getId();
            customerDivision.setItems(divisionDao.getCountryFirstLevelDivisions(selectedCountryId));
        });
        // Selected Customer ComboBox Listener
        customerList.getSelectionModel().selectedItemProperty().addListener((o, ol, nw) -> {
            int customerId = -1;
            if (customerList.getValue() != null){
                customerId = ((Customer) customerList.getValue()).getId();
            }
            this.selectedCustomerId = customerId;
            if (this.selectedCustomerId == -1){
                // New Customer
                setNewCustomer();
            } else {
                setSelectedCustomer();
            }
        });
    }
    
    /**
     * Method called on class load. Initializes all fields and instantiates data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get Customers
        ObservableList<Customer> customers = customerDao.getAllCustomers();
        // Init Table
        tableCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        tableCustomerDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        tableCustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        // Populate Tables
        customerTableView.getItems().setAll(customers);
        // Init Empty Appointment ComboBox
        StringConverter appointmentConverter = itemStringConverter(appointmentOList);
        customerAppointmentList.setConverter(appointmentConverter);
        // Init Customer ComboBox
        ObservableList<Customer> customerOList = customerDao.getAllCustomers();
        newCustomer.setCustomerId(-1);
        newCustomer.setCustomerName("New Customer");
        customerOList.add(newCustomer);
        customerList.setItems(customerOList);
        StringConverter customerConverter = itemStringConverter(customerOList);
        customerList.setConverter(customerConverter);
        // Init Country ComboBox
        ObservableList<Country> countryOList = countryDao.getAllCountries();
        customerCountry.setItems(countryOList);
        StringConverter countryConverter = itemStringConverter(countryOList);
        customerCountry.setConverter(countryConverter);
        customerCountry.setValue(countryDao.getCountry(1));
        // Init Division ComboBox
        ObservableList<FirstLevelDivision> divisionOList = divisionDao.getCountryFirstLevelDivisions(1);
        customerDivision.setItems(divisionOList);
        StringConverter divisionConverter = itemStringConverter(divisionOList);
        customerDivision.setConverter(divisionConverter);
        this.addListeners();
        if (this.selectedCustomerId == -1){
            // New Customer
            setNewCustomer();
        } else {
            customerList.setValue(customerDao.getCustomer(selectedCustomerId));
            setSelectedCustomer();
        }
    }
}
