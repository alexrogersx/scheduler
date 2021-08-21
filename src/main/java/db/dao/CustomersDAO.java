package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Customers dao.
 */
public class CustomersDAO extends DataAccessObject<Customer> {
    private static final String INSERT = "INSERT INTO customers( Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?)";

    private static final String GET_ONE_BY_ID = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID " +
            "FROM customers WHERE Customer_ID = ?";

    private static final String GET_ALL = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID " +
            "FROM customers";

    private static final String UPDATE = "UPDATE customers SET Customer_Name =?, Address =?, Postal_Code =?, Phone =?, Division_ID =? " +
            "WHERE Customer_ID = ?";

    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID = ?";


    /**
     * Instantiates a new Customers dao.
     *
     * @param connection the connection
     */
    public CustomersDAO(Connection connection) {
        super(connection, "appointments");
    }

    @Override
    public Customer getByID(int ID) {
        Customer customer = new Customer();
        try(PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setDivision(new DivisionsDAO(this.connection).getByID(resultSet.getInt("Division_ID")));
            }
            return customer;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setDivision(new DivisionsDAO(this.connection).getByID(resultSet.getInt("Division_ID")));
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        return customers;
    }

    @Override
    public Customer insert(Customer object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, object.getCustomerName());
            statement.setString(2, object.getAddress());
            statement.setString(3, object.getPostalCode());
            statement.setString(4, object.getPhone());
            statement.setInt(5, object.getDivision().getID());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);

        }
    }

    @Override
    public Customer update(Customer object) {
        Customer customer;
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE)){
            statement.setString(1, object.getCustomerName());
            statement.setString(2, object.getAddress());
            statement.setString(3, object.getPostalCode());
            statement.setString(4, object.getPhone());
            statement.setInt(5, object.getDivision().getID());
            statement.setInt(6, object.getCustomerID());
            statement.execute();
            customer = this.getByID(object.getCustomerID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        return customer;

    }

    @Override
    public void delete(int ID) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
            statement.setInt(1, ID);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }
}
