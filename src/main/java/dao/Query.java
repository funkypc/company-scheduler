package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class providing query access to the database. 
 * @author Charles Plett
 */
public class Query {
    
    /**
     * Query the database. 
     * @param query The String containing the query. 
     * @return Returns result of query as ResultSet.
     */
    public static ResultSet makeQuery(String query){
        ResultSet result = null;
        try {
            Statement statement = DBConnection.connection.createStatement();
            if(query.toLowerCase().startsWith("select")) {
                result=statement.executeQuery(query);
            } else if(query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update")){
                statement.executeUpdate(query);
            }
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
}
