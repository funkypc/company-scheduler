package dao;

import static com.charlesplett.companyscheduler.SharedUtility.timestampToInstant;
import static dao.Query.makeQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

/**
 * Dao class for Country. 
 * @author Charles Plett
 */
public class CountryDaoImpl implements CountryDao{

    
    @Override
    public Country getCountry(int id) {
        String sqlStatement = "SELECT * FROM countries WHERE Country_Id = '" + id + "' LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                Country countryResult = parseResult(result);
                return countryResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public ObservableList<Country> getAllCountries() {
        String sqlStatement = "SELECT * FROM countries";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Country countryResult = parseResult(result);
                allCountries.add(countryResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allCountries;
    }
    
    /** 
     * Parses the ResultSet into a new Country. 
     * @param result The ResultSet to parse. 
     * @return The Country created with the parsed data.
     * @throws SQLException If there is a problem parsing the ResultSet.
     */
    private Country parseResult(ResultSet result) throws SQLException{
        int countryId = result.getInt("Country_ID");
        String country = result.getString("Country");
        Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
        String createdBy = result.getString("Created_By");
        Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
        String lastUpdatedBy = result.getString("Last_Updated_By");
        Country countryResult = new Country(countryId, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
        return countryResult;
    }
}
