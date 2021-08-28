package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import static com.sun.tools.doclint.Entity.and;

/**
 * Acts as a single point of update for all logic relating to database connections
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String address = "//wgudb.ucertify.com/WJ08sa0";
    private static final String params = "connectTimeout=0&socketTimeout=0&autoReconnect=true";
    private static final String url = protocol + vendor + address +"?" + params;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "U08sa0";
    private static final String password = "53689381568";

    /**
     * Checks if a current connection to database exists. If no connection, one is created via the mysql JDBC driver.
     * Allows for the connection to be checked and re-established if lossed.
     * @return the connection
     * @throws SQLException in connection to database cannot be made
     * @throws ClassNotFoundException if JDBC driver cannot be located
     */
    public static Connection getCurrentConnection() throws SQLException, ClassNotFoundException {
        if(connection != null)
        {
            return connection;
        }
        else
        {
            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
    }

    /**
     * Initiates a connection to the database.
     * @return Connection type object representing database connection
     * @throws SQLException if connection to database cannot be made
     * @throws ClassNotFoundException if JDBC driver cannot be located
     */
    public static Connection startConnection() throws SQLException, ClassNotFoundException {
//            connection = DriverManager.getConnection(url, username, password);
        connection = getCurrentConnection();
        System.out.println("connected");
        return connection;
    }

    /**
     * Closes database connection
     * @throws SQLException if connection cannot be closed
     */
    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("connection closed");
    }

}
