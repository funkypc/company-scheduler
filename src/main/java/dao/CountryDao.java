package dao;

import javafx.collections.ObservableList;
import model.Country;

/**
 * Dao interface for Country. 
 * @author Charles Plett
 */
public interface CountryDao {
    
    /**
     * Gets the country associated with the given id. 
     * @param id The id of the Country to retrieve. 
     * @return Returns the country requested or null if the Country is not found.
     */
    public Country getCountry(int id);

    /**
     * Gets all Countries. 
     * @return Returns an ObservableList with all the Countries.
     */
    public ObservableList<Country> getAllCountries();
}
