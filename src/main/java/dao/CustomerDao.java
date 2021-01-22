package dao;

import javafx.collections.ObservableList;
import model.Customer;

/**
 * Dao interface for Customer. 
 * @author Charles Plett
 */
public interface CustomerDao {
    
    /**
     * Gets the customer associated with the given id. 
     * @param id The id of the Customer to retrieve. 
     * @return Returns the customer requested or null if the Customer is not found.
     */
    public Customer getCustomer(int id);

    /**
     * Gets all Customers. 
     * @return Returns an ObservableList with all the Customers.
     */
    public ObservableList<Customer> getAllCustomers();

    /**
     * Saves a customer to the database. 
     * @param customer The customer to save to the database.
     */
    public void createCustomer(Customer customer);

    /**
     * Updates a customer in the database. 
     * @param customer The customer to update. 
     */
    public void updateCustomer(Customer customer);

    /**
     * Deletes a customer from the database. 
     * @param customer The customer to delete. 
     */
    public void deleteCustomer(Customer customer);
}
