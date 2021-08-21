package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Contacts dao.
 */
public class ContactsDAO extends DataAccessObject<Contact> {
    private static final String INSERT = "INSERT INTO contacts( Contact_ID, Contact_Name, Email  ) VALUES(?,?,?)";

    private static final String GET_ONE_BY_ID = "SELECT Contact_ID, Contact_Name, Email " +
            "FROM contacts WHERE Contact_ID = ?";

    private static final String GET_ALL = "SELECT Contact_ID, Contact_Name, Email " +
            "FROM contacts";

    /**
     * Instantiates a new Contacts dao.
     *
     * @param connection the connection
     */
    public ContactsDAO(Connection connection) {
        super(connection, "contacts");
    }

    @Override
    public Contact getByID(int ID) {
        Contact contact = new Contact();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contact.setContactID(resultSet.getInt("Contact_ID"));
                contact.setContactName(resultSet.getString("Contact_Name"));
                contact.setEmail(resultSet.getString("Email"));
            }
            return contact;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ALL))) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactID(resultSet.getInt("Contact_ID"));
                contact.setContactName(resultSet.getString("Contact_Name"));
                contact.setEmail(resultSet.getString("Email"));
                contacts.add(contact);
            }
            return contacts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Contact insert(Contact object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, object.getID());
            statement.setString(2, object.getContactName());
            statement.setString(3, object.getEmail());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Contact update(Contact object) {
        return null;
    }

    @Override
    public void delete(int ID) {

    }
}
