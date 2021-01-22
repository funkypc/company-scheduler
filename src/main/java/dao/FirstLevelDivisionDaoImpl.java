package dao;

import static com.charlesplett.companyscheduler.SharedUtility.timestampToInstant;
import static dao.Query.makeQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

/**
 * Dao class for FirstLevelDivision. 
 * @author Charles Plett
 */
public class FirstLevelDivisionDaoImpl implements FirstLevelDivisionDao{

    @Override
    public FirstLevelDivision getFirstLevelDivision(int id) {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_Id = '" + id + "' LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                FirstLevelDivision divisionResult = parseResult(result);
                return divisionResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {
        String sqlStatement = "SELECT * FROM first_level_divisions";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        try{
            while(result.next()){
                FirstLevelDivision divisionResult = parseResult(result);
                allDivisions.add(divisionResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allDivisions;
    }
    
    @Override
    public ObservableList<FirstLevelDivision> getCountryFirstLevelDivisions(int id) {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Country_ID = '" + id + "'";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        try{
            while(result.next()){
                FirstLevelDivision divisionResult = parseResult(result);
                allDivisions.add(divisionResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allDivisions;
    }
    
    /** 
     * Parses the ResultSet into a new FirstLevelDivision. 
     * @param result The ResultSet to parse. 
     * @return The FirstLevelDivision created with the parsed data.
     * @throws SQLException If there is a problem parsing the ResultSet.
     */
    private FirstLevelDivision parseResult(ResultSet result) throws SQLException{
        int divisionId = result.getInt("Division_ID");
        String division = result.getString("Division");
        Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
        String createdBy = result.getString("Created_By");
        Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
        String lastUpdatedBy = result.getString("Last_Updated_By");
        int countryId = result.getInt("Country_ID");
        FirstLevelDivision divisionResult = new FirstLevelDivision(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
        return divisionResult;
    }
    
}
