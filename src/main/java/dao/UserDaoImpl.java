package dao;

import static com.charlesplett.companyscheduler.SharedUtility.timestampToInstant;
import static dao.Query.makeQuery;
import model.User;
import java.sql.ResultSet;
import java.time.Instant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Dao class for User. 
 * @author Charles Plett
 */
public class UserDaoImpl implements UserDao{
    
    @Override
    public User getUser(int id){
        String sqlStatement = "SELECT * FROM users WHERE User_Id = '" + id + "' LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
                String createdBy = result.getString("Created_By");
                Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
                String lastUpdatedBy = result.getString("Last_Updated_By");
                User userResult = new User(userId, userName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                return userResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public User authenticateUser(String user, String password){
        String sqlStatement = "SELECT * FROM users WHERE User_Name = '" + user + "' && password = '" + password + "'  LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
                String createdBy = result.getString("Created_By");
                Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
                String lastUpdatedBy = result.getString("Last_Updated_By");
                User userResult = new User(userId, userName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                return userResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public  ObservableList<User> getAllUsers(){
        String sqlStatement = "SELECT * FROM users";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try{
            while(result.next()){
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
                String createdBy = result.getString("Created_By");
                Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
                String lastUpdatedBy = result.getString("Last_Updated_By");
                User userResult = new User(userId, userName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allUsers.add(userResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allUsers;
    }
    
}
