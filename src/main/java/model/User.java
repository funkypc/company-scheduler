package model;

import java.time.Instant;

/**
 * Base class for User. Extends Item.
 * @author Charles Plett
 */
public class User extends Item{
    
    /**
     * id of User.  
     */
    private int userId;
    
    /**
     * name of User.  
     */
    private String userName;
    
    /**
     * create date of User.  
     */
    private Instant createDate;
    
    /**
     * createdBy of User.  
     */
    private String createdBy;
    
    /**
     * last update time of User.  
     */
    private Instant lastUpdate;
    
    /**
     * lastUpdatedBy of User.  
     */
    private String lastUpdatedBy;
    
    // Constructor

    /**
     * Constructor for User. 
     * @param userId The id of the User
     * @param userName The name of the User
     * @param createDate The date the User was created in UTC 
     * @param createdBy The name of the user that created the User
     * @param lastUpdate The last update time of the User in UTC
     * @param lastUpdatedBy The name of the user that updated the User
     */
    public User(int userId, String userName, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    // Getters

    @Override
    public int getId() {
        return userId;
    }

    @Override
    public String getName() {
        return userName;
    }

    /**
     * Getter for CreateDate. 
     * @return Returns the create date of the User as Instant
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     * Getter for CreatedBy. 
     * @return Returns the Name of the user that created the User as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for LastUpdate. 
     * @return Returns the last update time of the User as Instant
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for LastUpdatedBy. 
     * @return Returns the name of the user that last updated the User as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    // Setters

    /**
     * Setter for userId. 
     * @param userId The id of the User
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Setter for userName. 
     * @param userName The name of the User
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Setter for createDate. 
     * @param createDate The create date for the User in UTC
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for createdBy. 
     * @param createdBy The name of the user that created the User
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter for lastUpdate. 
     * @param lastUpdate The last updated time for the User in UTC
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for lastUpdated By. 
     * @param lastUpdatedBy The name of the user that updated the User
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
