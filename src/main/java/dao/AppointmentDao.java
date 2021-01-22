package dao;

import java.time.Instant;
import javafx.collections.ObservableList;
import model.Appointment;

/**
 * Dao interface for Appointment. 
 * @author Charles Plett
 */
public interface AppointmentDao {
    
    /**
     * Gets the Appointment associated with the given ID. 
     * @param id The id of the Appointment to retrieve. 
     * @return The Appointment requested or null if no Appointment is found.
     */
    public Appointment getAppointment(int id);

    /**
     * Gets the next upcoming appointment associated with the given userId. 
     * @param userId The userId to get the next appointment for. 
     * @return Returns the Appointment requested, or null if there are no appointments in the next 15 minutes.
     */
    public Appointment getUpcomingAppointment(int userId);

    /**
     * Gets all appointments associated with customer. 
     * @param id The id of the customer to get appointments for. 
     * @return Returns an ObservableList of Appointments for the given customer.
     */
    public ObservableList<Appointment> getCustomerAppointments(int id);

    /**
     * Gets all Appointments. 
     * @return Returns an ObservableList of all Appointments.
     */
    public ObservableList<Appointment> getAllAppointments();

    /**
     * Gets all appointments within 7 days of the current day. 
     * @return Returns an ObservableList of all Appointments requested.
     */
    public ObservableList<Appointment> getWeekAppointments();

    /**
     * Gets all appointments within 31 days of the current day. 
     * @return Returns an ObservableList of all Appointments requested.
     */
    public ObservableList<Appointment> getMonthAppointments();

    /**
     * Checks if the times would overlap with an existing appointment for this customer. 
     * @param customerId The id of the customer to check. 
     * @param start The start time of the prospective appointment. 
     * @param end The end time of the prospective appointment. 
     * @param appointmentId The id of the prospective appointment to check. 
     * @return Returns true if the appointment times overlaps. Else returns false.
     */
    public boolean isOverlappingAppointment(int customerId, Instant start, Instant end, int appointmentId);

    /**
     * Generates a report with the total number of customer appointments by type and month. 
     * @return Returns String with the report.
     */
    public String getAppointmentReportByTypeAndMonth();

    /**
     * Generates a report with Schedule of each contact.  
     * Includes appointmentId, Title, type, description, start date and time, end date and time and customerID.
     * @return Returns a String with the report. 
     */
    public String getAppointmentReportByContact();

    /**
     * Generates a report with the schedule of each customer. 
     * @return Returns a String with the report. 
     */
    public String getAppointmentReportByCustomer();

    /**
     * Saves an appointment to the database. 
     * @param appointment The appointment to save.
     */
    public void createAppointment(Appointment appointment);

    /**
     * Updates an appointment in the database. 
     * @param appointment The appointment to update.
     */
    public void updateAppointment(Appointment appointment);

    /**
     * Deletes an appointment from the database. 
     * @param appointment The appointment to delete.
     */
    public void deleteAppointment(Appointment appointment);
}
