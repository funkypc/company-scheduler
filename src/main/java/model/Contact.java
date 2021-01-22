package model;

/**
 * Base class for Contact objects. 
 * @author Charles Plett
 */
public class Contact extends Item{
    
    /**
     * id of Contact.  
     */
    private int contactId;
    
    /**
     * name of Contact.  
     */
    private String contactName;
    
    /**
     * email of Contact.  
     */
    private String email;
    
    // Constructor

    /**
     * Constructor for Contact. 
     * @param contactId The id for the contact
     * @param contactName The name of the contact
     * @param email The email address of the contact
     */

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
    
    // Getters

    @Override
    public int getId() {
        return contactId;
    }

    @Override
    public String getName() {
        return contactName;
    }

    /**
     * Getter for the email address of the contact. 
     * @return Returns the email address of the Contact
     */
    public String getEmail() {
        return email;
    }
    
    // Setters

    /**
     * Setter for contactId. 
     * @param contactId The id of the contact
     */

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Setter for contactName. 
     * @param contactName The name of the contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Setter for email. 
     * @param email The email address of the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
