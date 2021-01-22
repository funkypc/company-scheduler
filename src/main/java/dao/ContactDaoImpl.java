package dao;

import static dao.Query.makeQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

/**
 * Dao class for Contact. 
 * @author Charles Plett
 */
public class ContactDaoImpl implements ContactDao{
    
    
    @Override
    public Contact getContact(int id){
        String sqlStatement = "SELECT * FROM contacts WHERE Contact_ID = '" + id + "' LIMIT 1";
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                Contact contactResult = parseResult(result);
                return contactResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
        
    @Override
    public ObservableList<Contact> getAllContacts(){
        String sqlStatement = "SELECT * FROM contacts";
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Contact contactResult = parseResult(result);
                allContacts.add(contactResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allContacts;
    }
    
    /** 
     * Parses the ResultSet into a new Contact. 
     * @param result The ResultSet to parse. 
     * @return The Contact created with the parsed data.
     * @throws SQLException If there is a problem parsing the ResultSet.
     */
    private Contact parseResult(ResultSet result) throws SQLException{
        int contactId = result.getInt("Contact_ID");
        String contactName = result.getString("Contact_Name");
        String email = result.getString("Email");
        Contact contactResult = new Contact(contactId, contactName, email);
        return contactResult;
    }
}
