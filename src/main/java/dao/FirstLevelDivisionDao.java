package dao;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

/**
 * Dao interface for FirstLevelDivision. 
 * @author Charles Plett
 */
public interface FirstLevelDivisionDao {
    
    /**
     * Gets the FirstLevelDivision associated with the given id. 
     * @param id The id of the FirstLevelDivision to retrieve
     * @return Returns the FirstLevelDivision requested.
     */
    public FirstLevelDivision getFirstLevelDivision(int id);

    /**
     * Gets all FirstLevelDivisions. 
     * @return Returns ObservableList containing all FirstLevelDivisions.
     */
    public ObservableList<FirstLevelDivision> getAllFirstLevelDivisions();

    /**
     * Gets all FirstLevelDivisions associated with a Country. 
     * @param id The id of the Country to retrieve the FirstLevelDivisions of.
     * @return An ObservableList containing all the FirstLevelDivisions requested.
     */
    public ObservableList<FirstLevelDivision> getCountryFirstLevelDivisions(int id);
}
