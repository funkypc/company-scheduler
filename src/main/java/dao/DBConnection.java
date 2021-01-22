package dao;

import static com.charlesplett.companyscheduler.SharedUtility.myParseInt;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

/**
 * Class providing connection to Database. 
 * @author Charles Plett
 */
public class DBConnection {
    
    /**
     * The static Connection to the database. 
     */
    static Connection connection;
    
    /**
     * Opens connection to database. 
     */
    public static void connect() {
        DataSource dataSource = getMySQLDataSource();
        System.out.println("Connecting to DB...");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            System.out.println("There was a problem connecting to the DB. ");
        }
    }
    
    /**
     * Closes connection to database. 
     */
    public static void close() {
        System.out.println("Closing DB...");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            System.out.println("There was a problem closing the DB. ");
        }
    }
    
    /**
     * Sets the MysqlDataSource connection properties based on the db.properties file. 
     * @return MysqlDataSource on success.
     */
    private static MysqlDataSource getMySQLDataSource() {
        Properties props = new Properties();
        MysqlDataSource mysqlDS = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/db.properties");
            props.load(fileInputStream);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("DB_URL"));
            mysqlDS.setPortNumber(myParseInt(props.getProperty("DB_PORT")));
            mysqlDS.setUser(props.getProperty("DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            System.out.println("Unable to set DB connection properties. ");
        }
        return mysqlDS;
    }
}
