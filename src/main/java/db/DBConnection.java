package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Acts as a single point of update for all logic relating to database connections
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String address = "//wgudb.ucertify.com/WJ08sa0";
    private static final String url = protocol + vendor + address;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "U08sa0";
    private static final String password = "53689381568";

    /**
     * Initiates a connection via the mysql JDBC driver
     * @return Connection type object representing database connection
     * @throws SQLException if connection to database cannot be made
     * @throws ClassNotFoundException if JDBC driver cannot be located
     */
    public static Connection startConnection() throws SQLException, ClassNotFoundException {

            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(url, username, password);
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
