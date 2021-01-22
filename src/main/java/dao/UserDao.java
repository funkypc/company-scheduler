package dao;

import model.User;
import javafx.collections.ObservableList;

/**
 * Dao interface for User. 
 * @author Charles Plett
 */
public interface UserDao {

    /**
     * Gets the User associated with the given id. 
     * @param id The id of the User to retrieve. 
     * @return Returns the User requested or null if the User is not found.
     */
    public User getUser(int id);

    /**
     * Checks to see if a User exists with this username and password. 
     * @param user The name of the User.
     * @param password The password of the User.
     * @return Returns the User if successful. Returns null on failure.
     */
    public User authenticateUser(String user, String password);

    /**
     * Gets all Users. 
     * @return Returns an ObservableList containing all Users.
     */
    public ObservableList<User> getAllUsers();
}
