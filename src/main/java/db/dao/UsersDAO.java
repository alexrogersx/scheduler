package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Users dao.
 */
public class UsersDAO extends DataAccessObject<User> {
    private static final String INSERT = "INSERT INTO users( User_ID, User_Name, Password ) VALUES(?,?,?)";

    private static final String GET_ONE_BY_ID = "SELECT User_ID, User_Name, Password " +
            "FROM users WHERE User_ID = ?";

    private static final String GET_ALL = "SELECT User_ID, User_Name, Password " +
            "FROM users";

    /**
     * Instantiates a new Users dao.
     *
     * @param connection the connection
     */
    public UsersDAO(Connection connection) {
        super(connection, "users");
    }

    @Override
    public User getByID(int ID) {
        User user = new User();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setUserID(resultSet.getInt("User_ID"));
                user.setUserName(resultSet.getString("User_Name"));
                user.setPassword(resultSet.getString("Password"));
            }
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ALL))) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("User_ID"));
                user.setUserName(resultSet.getString("User_Name"));
                user.setPassword(resultSet.getString("Password"));
                users.add(user);
            }
            return users;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public User insert(User object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, object.getID());
            statement.setString(2, object.getUserName());
            statement.setString(3, object.getPassword());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public void delete(int ID) {

    }
}
