package db.dao.util;

import java.sql.*;
import java.util.List;

/**
 * Generic abstract class for DAO entity classes used in application
 *
 * @param <T> Type of entity which DAO will be built for
 */
public abstract class DataAccessObject <T extends  DataTransferObject> {
    public final Connection connection;
    public String table;

    /**
     * @param connection Connection type parameter to database
     * @param table String identifying the primary SQL table to be queried
     */
    public DataAccessObject(Connection connection, String table) {
        super();
        this.connection = connection;
        this.table = table;

    }

    /**
     * Generic method which returns a specific entry by its ID key
     * @param ID ID key from object
     * @return type T object found
     */
    public abstract T getByID(int ID);

    /**
     * Generic method which returns all entries in table
     * @return List of type T objects
     */
    public abstract List<T> getAll();

    /**
     * Generic method which inserts an entry into table
     * @param object Type T object to be inserted
     * @return the inserted object as validation
     */
    public abstract T insert(T object);

    /**
     * Generic method which updates an existing entry in table
     * @param object Type T object to be updated
     * @return the updated object as validation
     */
    public abstract T update(T object);

    /**
     * generic method which deleted an entry from table
     * @param ID int ID of item to be deleted
     */
    public abstract void delete(int ID);

    /**
     * Returns the last auto incremented ID key used in sql table
     * @param statement Statement object which to retrieve the ID from
     * @return int representing the last ID
     */
    protected int getLastID(Statement statement){
        int ID = -1;
        try(ResultSet resultSet = statement.getGeneratedKeys()) {
            while (resultSet.next()){
                ID = resultSet.getInt(1);
            }
            return ID;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

    }
}
