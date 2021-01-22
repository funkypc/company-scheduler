package dao;

import static com.charlesplett.companyscheduler.SharedUtility.instantToTimestamp;
import static com.charlesplett.companyscheduler.SharedUtility.timestampToInstant;
import static dao.Query.makeQuery;
import static java.lang.Integer.parseInt;
import model.Appointment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.DateFormatSymbols;

/**
 * Dao class for Appointment. 
 * @author Charles Plett
 */
public class AppointmentDaoImpl implements AppointmentDao{
    
    
    @Override
    public Appointment getAppointment(int id){
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE Appointment_ID = '" + id + "' LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                return appointmentResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
        
    @Override
    public Appointment getUpcomingAppointment(int userId){
        Instant instantNow = Instant.now();
        Instant instantInFifteen = instantNow.plus(15, ChronoUnit.MINUTES);
        Timestamp tsNow = instantToTimestamp(instantNow);
        Timestamp tsInFifteen = instantToTimestamp(instantInFifteen);
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE ((User_ID = '" + userId + "') && (Start >= '" + tsNow + "' AND Start <= '" + tsInFifteen + "')) LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                return appointmentResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
        
    @Override
    public ObservableList<Appointment> getCustomerAppointments(int id){
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE appointments.Customer_ID = '" + id + "'";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                customerAppointments.add(appointmentResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return customerAppointments;
    }
        
    @Override
    public ObservableList<Appointment> getWeekAppointments(){
        Instant instantNow = Instant.now();
        Instant instantInSevenDays = instantNow.plus(7, ChronoUnit.DAYS);
        Timestamp tsNow = instantToTimestamp(instantNow);
        Timestamp tsInSeven = instantToTimestamp(instantInSevenDays);
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE ((Start >= '" + tsNow + "' AND Start <= '" + tsInSeven + "'))";
        ResultSet result = makeQuery(sqlStatement);
                ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                weekAppointments.add(appointmentResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return weekAppointments;
    }
        
    @Override
    public ObservableList<Appointment> getMonthAppointments(){
        Instant instantNow = Instant.now();
        Instant instantInOneMonth = instantNow.plus(31, ChronoUnit.DAYS);
        Timestamp tsNow = instantToTimestamp(instantNow);
        Timestamp tsInMonth = instantToTimestamp(instantInOneMonth);
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE ((Start >= '" + tsNow + "' AND Start <= '" + tsInMonth + "'))";
        ResultSet result = makeQuery(sqlStatement);
                ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                monthAppointments.add(appointmentResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return monthAppointments;
    }
        
    @Override
    public  ObservableList<Appointment> getAllAppointments(){
        String sqlStatement = "SELECT appointments.*, customers.Customer_Name, contacts.Contact_Name FROM appointments "
                + "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Appointment appointmentResult = parseResult(result);
                allAppointments.add(appointmentResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allAppointments;
    }
        
    @Override
    public void createAppointment(Appointment appointment){
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        Timestamp start = instantToTimestamp(appointment.getStart());
        Timestamp end = instantToTimestamp(appointment.getEnd());
        Timestamp createDate = instantToTimestamp(appointment.getCreateDate());
        String createdBy = appointment.getCreatedBy();
        Timestamp lastUpdate = instantToTimestamp(appointment.getLastUpdate());
        String lastUpdatedBy = appointment.getLastUpdatedBy();
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();
        String sqlStatement = "INSERT INTO appointments"
                + "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, "
                + "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                + "VALUES('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" 
                + start + "', '" + end + "', '" + createDate + "', '" + createdBy + "', '" + lastUpdate 
                + "', '" + lastUpdatedBy + "'," + customerId + ", " + userId + "," + contactId + ")";
        makeQuery(sqlStatement);
    }
        
    @Override
    public void updateAppointment(Appointment appointment){
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        Timestamp start = instantToTimestamp(appointment.getStart());
        Timestamp end = instantToTimestamp(appointment.getEnd());
        Timestamp lastUpdate = instantToTimestamp(appointment.getLastUpdate());
        String lastUpdatedBy = appointment.getLastUpdatedBy();
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();
        int appointmentId = appointment.getId();
        String sqlStatement = "UPDATE appointments SET " +
            "Title = '" + title + "'," +
            "Description = '" + description + "'," +
            "Location = '" + location + "'," +
            "Type = '" + type + "'," +
            "Start = '" + start + "'," +
            "End = '" + end + "'," +
            "Last_Update = '" + lastUpdate + "'," +
            "Last_Updated_By = '" + lastUpdatedBy + "'," +
            "Customer_ID = " + customerId + "," +
            "User_ID = " + userId + "," +
            "Contact_ID = " + contactId + " " +
            "WHERE Appointment_ID = " + appointmentId ;
        makeQuery(sqlStatement);
    }
        
    @Override
    public void deleteAppointment(Appointment appointment){
        int appointmentId = appointment.getAppointmentId();
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentId;
        makeQuery(sqlStatement);
    }
        
    @Override
    public boolean isOverlappingAppointment(int customerId, Instant start, Instant end, int appointmentId){
        Timestamp startDate = instantToTimestamp(start);
        Timestamp endDate = instantToTimestamp(end);
        String sqlStatement = "SELECT * from appointments WHERE ('" + 
                startDate + "' < End AND '" + endDate + 
                "' > Start) && (Customer_ID = " + customerId + ") && (Appointment_ID != " + appointmentId + ")";
        ResultSet result = makeQuery(sqlStatement);
        try{
            // If this is not an overlapping appointment
            if (result.next() == false) {
                return false;
            }
            // This is an overlapping appointment
            return true;
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public String getAppointmentReportByTypeAndMonth(){
        String sqlStatement = "select Type, count(Appointment_ID) as NumberOfAppointments from appointments group by Type";
        String sqlStatement2 = "select month(Start) as Month, count(Appointment_ID) as NumberOfAppointments from appointments group by month(Start)";
        ResultSet result = makeQuery(sqlStatement);
        String report = "Number of Customer Appointments by Type:\n";
        try{
            while(result.next()){
                String type = result.getString("Type");
                String numberOfAppointments = result.getString("NumberOfAppointments");
                report = report.concat(type + ": " + numberOfAppointments + "\n");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        report = report.concat("\n" + "Number of Customer Appointments by Month:\n");
        result = makeQuery(sqlStatement2);
        try{
            while(result.next()){
                String month = result.getString("Month");
                String monthName = new DateFormatSymbols().getMonths()[parseInt(month)-1];
                String numberOfAppointments = result.getString("NumberOfAppointments");
                report = report.concat(monthName + ": " + numberOfAppointments + "\n");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return report;
    }
        
    @Override
    public String getAppointmentReportByContact(){
        String sqlStatement = "select Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID "
                + "from appointments inner join contacts on contacts.Contact_ID "
                + "where appointments.Contact_ID = contacts.Contact_ID";
        ResultSet result = makeQuery(sqlStatement);
        String report = "Appointments by Contact:\n";
        try{
            while(result.next()){
                String contactName = result.getString("Contact_Name");
                String appointmentId = result.getString("Appointment_ID");
                String title = result.getString("Title");
                String type = result.getString("Type");
                String description = result.getString("Description");
                Instant instantStart = timestampToInstant(result.getObject("Start", java.sql.Timestamp.class));
                String start = instantStart.truncatedTo(ChronoUnit.MINUTES).toString().replaceAll("[TZ]", " ");
                Instant instantEnd = timestampToInstant(result.getObject("End", java.sql.Timestamp.class));
                String end = instantEnd.truncatedTo(ChronoUnit.MINUTES).toString().replaceAll("[TZ]", " ");
                String customerId = result.getString("Customer_ID");
                report = report.concat(contactName + " - Appointment ID: " + appointmentId + " - Title: "
                        + title + " - Type: " + type + " - Description: " + description + " - Start: " + start
                        + "UTC - End: " + end + "UTC - Customer ID: " + customerId + "\n");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return report;
    }
        
    @Override
    public String getAppointmentReportByCustomer(){
        String sqlStatement = "select Customer_Name, Appointment_ID, Title, Type, Description, Start, End, Contact_ID "
                + "from appointments inner join customers on customers.Customer_ID "
                + "where appointments.Customer_ID = customers.Customer_ID";
        ResultSet result = makeQuery(sqlStatement);
        String report = "Appointments by Customer:\n";
        try{
            while(result.next()){
                String customerName = result.getString("Customer_Name");
                String appointmentId = result.getString("Appointment_ID");
                String title = result.getString("Title");
                String type = result.getString("Type");
                String description = result.getString("Description");
                Instant instantStart = timestampToInstant(result.getObject("Start", java.sql.Timestamp.class));
                String start = instantStart.truncatedTo(ChronoUnit.MINUTES).toString().replaceAll("[TZ]", " ");
                Instant instantEnd = timestampToInstant(result.getObject("End", java.sql.Timestamp.class));
                String end = instantEnd.truncatedTo(ChronoUnit.MINUTES).toString().replaceAll("[TZ]", " ");
                String contactId = result.getString("Contact_ID");
                report = report.concat(customerName + " - Appointment ID: " + appointmentId + " - Title: "
                        + title + " - Type: " + type + " - Description: " + description + " - Start: " + start
                        + "UTC - End: " + end + "UTC - Contact ID: " + contactId + "\n");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return report;
    }
    
    /** 
     * Parses the ResultSet into a new Appointment. 
     * @param result The ResultSet to parse. 
     * @return Returns Appointment created with the parsed data.
     * @throws SQLException If there is a problem parsing the ResultSet.
     */
    private Appointment parseResult(ResultSet result) throws SQLException{
        int appointmentId = result.getInt("Appointment_ID");
        String title = result.getString("Title");
        String description = result.getString("Description");
        String location = result.getString("Location");
        String type = result.getString("Type");
        Instant start = timestampToInstant(result.getObject("Start", java.sql.Timestamp.class));
        Instant end = timestampToInstant(result.getObject("End", java.sql.Timestamp.class));
        Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
        String createdBy = result.getString("Created_By");
        Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
        String lastUpdatedBy = result.getString("Last_Updated_By");
        int customerId = result.getInt("Customer_ID");
        int userId = result.getInt("User_ID");
        int contactId = result.getInt("Contact_ID");
        String contactName = result.getString("Contact_Name");
        String customerName = result.getString("Customer_Name");
        Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type, 
        start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName, customerName);
        return appointmentResult;
    }
}
