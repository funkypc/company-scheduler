package model;

import java.time.Instant;

/**
 * Base class for Appointment objects. Extends Item. 
 * @author Charles Plett
 */
public class Appointment extends Item{
    
    /**
     * id of Appointment.  
     */
    private int appointmentId;
    
    /**
     * title of Appointment.  
     */
    private String title;
    
    /**
     * description of Appointment.  
     */
    private String description;
    
    /**
     * location of Appointment.  
     */
    private String location;
    
    /**
     * type of Appointment.  
     */
    private String type;
    
    /**
     * start time of Appointment.  
     */
    private Instant start;
    
    /**
     * end time of Appointment.  
     */
    private Instant end;
    
    /**
     * create date of Appointment.  
     */
    private Instant createDate;
    
    /**
     * createdBy of Appointment.  
     */
    private String createdBy;
    
    /**
     * last update time of Appointment.  
     */
    private Instant lastUpdate;
    
    /**
     * lastUpdatedBy of Appointment.  
     */
    private String lastUpdatedBy;
    
    /**
     * id of customer associated with Appointment.  
     */
    private int customerId;
    
    /**
     * id user associated with Appointment.  
     */
    private int userId;
    
    /**
     * id of contact associated with Appointment.  
     */
    private int contactId;
    
    /**
     * name of contact associated with Appointment.  
     */
    private String contactName;
    
    /**
     * name of customer associated with Appointment.  
     */
    private String customerName;

    // Constructors

    /**
     * Constructor for Appointment. 
     * @param appointmentId The id of the Appointment
     * @param title The title of the Appointment
     * @param description The description of the Appointment
     * @param location The location of the Appointment
     * @param type The type of Appointment
     * @param start The start of Appointment in UTC
     * @param end The end of Appointment in UTC
     * @param createDate The date the Appointment was created in UTC
     * @param createdBy The name of the user who created the Appointment
     * @param lastUpdate The last updated time of Appointment in UTC
     * @param lastUpdatedBy The name of the user who updated the Appointment
     * @param customerId The id of the Customer associated with the Appointment
     * @param userId The id of the User associated with the Appointment
     * @param contactId The id of the Contact associated with the Appointment
     * @param contactName The name of the contact associated with the Appointment
     * @param customerName The name of the customer associated with the Appointment
     */
    
    public Appointment(int appointmentId, String title, String description, String location, String type, Instant start, Instant end, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contactName, String customerName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
        this.customerName = customerName;
    }

    /**
     * Default empty constructor for Appointment. 
     */
    public Appointment() {
    }
    
    // Getters

    @Override 
    public String getName(){
        return getTitle();
    }
    
    @Override
    public int getId() {
        return getAppointmentId();
    }
    
    /**
     * Getter for AppointmentId. 
     * @return Returns the id of the Appointment as int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Getter for Title. 
     * @return Returns the Title of the Appointment as String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for Description. 
     * @return Returns the description of the Appointment as String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for Location. 
     * @return Returns the location of the Appointment as String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for Type. 
     * @return Returns the type of the Appointment as String
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for start. 
     * @return Returns the start time of the appointment as Instant
     */
    public Instant getStart() {
        return start;
    }

    /**
     * Getter for end. 
     * @return Returns the end time of the appointment as Instant
     */
    public Instant getEnd() {
        return end;
    }

    /**
     * Getter for CreateDate. 
     * @return Returns the create date of the appointment as Instant
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     * Getter for CreatedBy. 
     * @return Returns the Name of the user that created the appointment as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for LastUpdate. 
     * @return Returns the last update time of the Appointment as Instant
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for LastUpdatedBy. 
     * @return Returns the name of the user that last updated the appointment as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Getter for CustomerId. 
     * @return Returns the id of the Customer associated with the appointment
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Getter for UserId. 
     * @return Returns the id of the User associated with the appointment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for ContactId. 
     * @return Returns the id of the Contact associated with the appointment
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Getter for ContactName. 
     * @return Returns the Name of the Contact associated with the appointment
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for CustomerName. 
     * @return Returns the name of the Customer associated with the appointment as String
     */
    public String getCustomerName() {
        return customerName;
    }
    
    
    // Setters

    /**
     * Setter for AppointmentId. 
     * @param appointmentId The id of the appointment
     */

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Setter for title. 
     * @param title The title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for description. 
     * @param description The description of the appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for location. 
     * @param location The location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter for type. 
     * @param type The type of the appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for start. 
     * @param start The start time of the appointment in UTC 
     */
    public void setStart(Instant start) {
        this.start = start;
    }

    /**
     * Setter for end. 
     * @param end The end time of the appointment in UTC
     */
    public void setEnd(Instant end) {
        this.end = end;
    }

    /**
     * Setter for createDate. 
     * @param createDate The create date for the appointment in UTC
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for createdBy. 
     * @param createdBy The name of the user that created the appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter for lastUpdate. 
     * @param lastUpdate The last updated time for the appointment in UTC
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for lastUpdated By. 
     * @param lastUpdatedBy The name of the user that updated the appointment
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Setter for customerId. 
     * @param customerId The id of the Customer associated with the appointment
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Setter for userId. 
     * @param userId The id of the User associated with the appointment
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Setter for contactId. 
     * @param contactId The id of the Contact associated with the appointment
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
        
}
