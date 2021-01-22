package model;

import java.time.Instant;

/**
 * Base class for Country objects. 
 * @author Charles Plett
 */
public class Country  extends Item{
    
    /**
     * id of Country.  
     */
    private int countryId;
    
    /**
     * name of Country.  
     */
    private String country;
    
    /**
     * create date of Country.  
     */
    private Instant createDate;
    
    /**
     * createdBy of Country.  
     */
    private String createdBy;
    
    /**
     * last update time of Country.  
     */
    private Instant lastUpdate;
    
    /**
     * lastUpdatedBy of Country.  
     */
    private String lastUpdatedBy;
    
    
    // Constructor

    /**
     * Constructor for Country. 
     * @param countryId The id of the Country
     * @param country The name of the Country
     * @param createDate The date the Country object was created
     * @param createdBy The name of the user that created the Country object
     * @param lastUpdate The last update time of the Country
     * @param lastUpdatedBy The name of the user that updated the Country
     */

    public Country(int countryId, String country, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    // Getters

    @Override
    public String getName(){
        return getCountry();
    }
    
    @Override
    public int getId() {
        return countryId;
    }

    /**
     * Getter for country name. 
     * @return The name of the Country as String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Getter for CreateDate. 
     * @return Returns the create date of the country as Instant
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     * Getter for CreatedBy. 
     * @return Returns the Name of the user that created the Country as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for LastUpdate. 
     * @return Returns the last update time of the Country as Instant
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for LastUpdatedBy. 
     * @return Returns the name of the user who last updated the Country as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    // Setters

    /**
     * Setter for CountryId. 
     * @param countryId The id of the country
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Setter for country Name. 
     * @param country The name of the Country as String
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Setter for createDate. 
     * @param createDate The create date for the country in UTC
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for createdBy. 
     * @param createdBy The name of the user that created the country
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter for lastUpdate. 
     * @param lastUpdate The last updated time for the country in UTC
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for lastUpdated By. 
     * @param lastUpdatedBy The name of the user that updated the country
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
}
