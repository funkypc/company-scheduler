package dao;

import static com.charlesplett.companyscheduler.SharedUtility.instantToTimestamp;
import static com.charlesplett.companyscheduler.SharedUtility.timestampToInstant;
import static dao.Query.makeQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

/**
 * Dao class for Customer. 
 * @author Charles Plett
 */
public class CustomerDaoImpl implements CustomerDao{
    
    
    @Override
    public Customer getCustomer(int id){
        String sqlStatement = "SELECT customers.*, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers "
                + "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                + "WHERE customers.Customer_ID = '" + id + "' LIMIT 1";
        
        ResultSet result = makeQuery(sqlStatement);
        try{
            while(result.next()){
                Customer customerResult = parseResult(result);
                return customerResult;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
        
    @Override
    public ObservableList<Customer> getAllCustomers(){
                String sqlStatement = "SELECT customers.*, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers "
                + "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
        
        ResultSet result = makeQuery(sqlStatement);
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try{
            while(result.next()){
                Customer customerResult = parseResult(result);
                allCustomers.add(customerResult);
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return allCustomers;
    }
        
    @Override
    public void createCustomer(Customer customer){
        String name = customer.getName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        Timestamp createDate = instantToTimestamp(customer.getCreateDate());
        String createdBy = customer.getCreatedBy();
        Timestamp lastUpdate = instantToTimestamp(customer.getLastUpdate());
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionId = customer.getDivisionId();
        String sqlStatement = "INSERT INTO customers"
                + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, "
                + "Last_Update, Last_Updated_By, Division_ID) "
                + "VALUES('" + name + "', '" + address + "', '" + postalCode + "', '" + phone + "', '" 
                + createDate + "', '" + createdBy + "', '" + lastUpdate 
                + "', '" + lastUpdatedBy + "'," + divisionId + ")";
        makeQuery(sqlStatement);
    }
        
    @Override
    public void updateCustomer(Customer customer){
        String name = customer.getName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        Timestamp lastUpdate = instantToTimestamp(customer.getLastUpdate());
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionId = customer.getDivisionId();
        int customerId = customer.getCustomerId();
        String sqlStatement = "UPDATE customers SET " +
            "Customer_Name = '" + name + "'," +
            "Address = '" + address + "'," +
            "Postal_Code = '" + postalCode + "'," +
            "Phone = '" + phone + "'," +
            "Last_Update = '" + lastUpdate + "'," +
            "Last_Updated_By = '" + lastUpdatedBy + "'," +
            "Division_ID = " + divisionId +
            " WHERE Customer_ID = " + customerId;
        makeQuery(sqlStatement);
    }
        
    @Override
    public void deleteCustomer(Customer customer){
        int customerId = customer.getCustomerId();
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = " + customerId;
        makeQuery(sqlStatement);
    }
    
    /** 
     * Parses the ResultSet into a new Customer. 
     * @param result The ResultSet to parse. 
     * @return The Customer created with the parsed data.
     * @throws SQLException If there is a problem parsing the ResultSet.
     */
    private Customer parseResult(ResultSet result) throws SQLException{
        int customerId = result.getInt("Customer_ID");
        String customerName = result.getString("Customer_Name");
        String address = result.getString("Address");
        String postalCode = result.getString("Postal_Code");
        String phone = result.getString("Phone");
        Instant createDate = timestampToInstant(result.getObject("Create_Date", java.sql.Timestamp.class));
        String createdBy = result.getString("Created_By");
        Instant lastUpdate = timestampToInstant(result.getObject("Last_Update", java.sql.Timestamp.class));
        String lastUpdatedBy = result.getString("Last_Updated_By");
        int divisionId = result.getInt("Division_ID");
        String division = result.getString("Division");
        int countryId = result.getInt("Country_ID");
        String country = result.getString("Country");
        Customer customerResult = new Customer(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, division, countryId, country);
        return customerResult;
    }
}
