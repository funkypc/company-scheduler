package model;

import java.time.Instant;

/**
 * Base class for FirstLevelDivision objects. Extends Item.
 * @author Charles Plett
 */
public class FirstLevelDivision extends Item{
    
    /**
     * id of Division.  
     */
    private int divisionId;
    
    /**
     * name of Division.  
     */
    private String division;
    
    /**
     * create date of Division.  
     */
    private Instant createDate;
    
    /**
     * createdBy of Division.  
     */
    private String createdBy;
    
    /**
     * last update time of Division.  
     */
    private Instant lastUpdate;
    
    /**
     * lastUpdatedBy of Division.  
     */
    private String lastUpdatedBy;
    
    /**
     * id of country associated with Division.  
     */
    private int countryId;
    
    
    // Constructor

    /**
     * Constructor for FirstLevelDivision. 
     * @param divisionId The id of the division
     * @param division The name of the division
     * @param createDate The date the division was created in UTC 
     * @param createdBy The name of the user that created the division
     * @param lastUpdate The last update time of the division in UTC
     * @param lastUpdatedBy The name of the user that updated the division
     * @param countryId The id of the Country associated with the division
     */

    public FirstLevelDivision(int divisionId, String division, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    
    // Getters

    @Override
    public String getName(){
        return getDivision();
    }
    
    @Override
    public int getId() {
        return divisionId;
    }

    /**
     * Getter for Division. 
     * @return Returns the name of the division as String
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for CreateDate. 
     * @return Returns the create date of the division as Instant
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     * Getter for CreatedBy. 
     * @return Returns the Name of the user that created the division as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for LastUpdate. 
     * @return Returns the last update time of the division as Instant
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for LastUpdatedBy. 
     * @return Returns the name of the user that last updated the division as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Getter for CountryId. 
     * @return Returns the id of the Country associated with the division
     */
    public int getCountryId() {
        return countryId;
    }
    
    // Setters

    /**
     * Setter for divisionId. 
     * @param divisionId The id of the division
     */

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Setter for division name. 
     * @param division The name of the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Setter for createDate. 
     * @param createDate The create date for the division in UTC
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for createdBy. 
     * @param createdBy The name of the user that created the division
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter for lastUpdate. 
     * @param lastUpdate The last updated time for the division in UTC
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for lastUpdated By. 
     * @param lastUpdatedBy The name of the user that updated the division
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Setter for countryId. 
     * @param countryId The id of the Country associated with the division
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
}
