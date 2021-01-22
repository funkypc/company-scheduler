package model;

import java.time.Instant;

/**
 * Base class for Customer objects. Extends Item. 
 * @author Charles Plett
 */
public class Customer extends Item{
    
    /**
     * id of Customer.  
     */
    private int customerId;
    
    /**
     * name of Customer.  
     */
    private String customerName;
    
    /**
     * address of Customer.  
     */
    private String address;
    
    /**
     * postal code of Customer.  
     */
    private String postalCode;
    
    /**
     * phone number of Customer.  
     */
    private String phone;
    
    /**
     * create date of Customer.  
     */
    private Instant createDate;
    
    /**
     * createdBy of Customer.  
     */
    private String createdBy;
    
    /**
     * last update time of Customer.  
     */
    private Instant lastUpdate;
    
    /**
     * lastUpdatedBy of Customer.  
     */
    private String lastUpdatedBy;
    
    /**
     * id of division associated with Customer.  
     */
    private int divisionId;
    
    /**
     * name of division associated with Customer.  
     */
    private String division;
    
    /**
     * id of country associated with Customer.  
     */
    private int countryId;
    
    /**
     * name of country associated with Customer.  
     */
    private String country;
    
    
    // Constructors

    /**
     * Constructor for Customer. 
     * @param customerId The id of the Customer
     * @param customerName The name of the Customer
     * @param address The address of the Customer
     * @param postalCode The postal code of the Customer
     * @param phone The phone number of the Customer
     * @param createDate The date the Customer was created in UTC
     * @param createdBy The name of the user that created the Customer
     * @param lastUpdate The last update time of the Customer in UTC
     * @param lastUpdatedBy The name of the user that updated the Customer
     * @param divisionId The id of the division associated with the Customer
     * @param division The name of the division associated with the Customer
     * @param countryId The id of the Country associated with the Customer
     * @param country The name of the Country associated with the Customer
     */

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy, int divisionId, String division, int countryId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Default empty constructor for Customer. 
     */
    public Customer() {
    }
    
    
    // Getters

    @Override
    public int getId() {
        return getCustomerId();
    }

    @Override
    public String getName() {
        return getCustomerName();
    }

    /**
     * Getter for CustomerId. 
     * @return Returns the id of the Customer as int
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Getter for CustomerName. 
     * @return Returns the name of the Customer as String
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Getter for Address. 
     * @return Returns the Address of the customer as String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for PostalCode. 
     * @return Returns Postal Code as String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Getter for phone number. 
     * @return Returns phone number as String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Getter for CreateDate. 
     * @return Returns the create date of the Customer as Instant
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     * Getter for CreatedBy. 
     * @return Returns the Name of the user that created the Customer as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for LastUpdate. 
     * @return Returns the last update time of the Customer as Instant
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for LastUpdatedBy. 
     * @return Returns the name of the user that last updated the Customer as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Getter for DivisionId. 
     * @return Returns the id of the division associated with the Customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Getter for Division name. 
     * @return Returns the name of the division associated with the Customer as String
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for CountryId. 
     * @return Returns the id of the Country associated with the Customer
     */
    public int getCountryId() {
        return countryId;
    }
    
    /**
     * Getter for Country. 
     * @return Returns the name of the Country associated with the Customer
     */
    public String getCountry() {
        return country;
    }
    
    
    // Setters

    /**
     * Setter for customerId. 
     * @param customerId The id of the Customer
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Setter for customerName. 
     * @param customerName The name of the Customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Setter for Address. 
     * @param address The address of the Customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter for postalCode. 
     * @param postalCode The Postal Code of the Customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Setter for phone number. 
     * @param phone The phone number of the Customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Setter for createDate. 
     * @param createDate The create date for the Customer in UTC
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for createdBy. 
     * @param createdBy The name of the user that created the Customer
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter for lastUpdate. 
     * @param lastUpdate The last updated time for the Customer in UTC
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for lastUpdated By. 
     * @param lastUpdatedBy The name of the user that updated the Customer
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Setter for First Level Division. 
     * @param division The First Level Division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
    
    /**
     * Setter for divisionId. 
     * @param divisionId The id of the FirstLevelDivision to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Setter for Country. 
     * @param country The name of the Country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Setter for countryId. 
     * @param countryId The id of the Country to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
