package dao;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * Dao interface for Contact. 
 * @author Charles Plett
 */
public interface ContactDao {
    
    /**
     * Gets the contact associated with the given id. 
     * @param id The id of the contact to retrieve. 
     * @return Returns the Contact requested or null if no Contact is found.
     */
    public Contact getContact(int id);

    /**
     * Gets all contacts. 
     * @return Returns an ObservableList containing all Contacts.
     */
    public ObservableList<Contact> getAllContacts();
}
