package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.Division;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Divisions dao.
 */
public class DivisionsDAO extends DataAccessObject<Division> {
    private static final String INSERT = "INSERT INTO first_level_divisions( Division_ID," +
            "Division, Country_ID  ) VALUES(?,?,?)";

    private static final String GET_ONE_BY_ID = "SELECT Division_ID, Division, Country_ID " +
            "FROM first_level_divisions WHERE Division_ID = ?";

    private static final String GET_ALL = "SELECT Division_ID, Division, Country_ID " +
            "FROM first_level_divisions";

    /**
     * Instantiates a new Divisions dao.
     *
     * @param connection the connection
     */
    public DivisionsDAO(Connection connection) {
        super(connection, "first_level_divisions");
    }

    @Override
    public Division getByID(int ID) {
        Division division = new Division();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                division.setDivisionID(resultSet.getInt("Division_ID"));
                division.setDivision(resultSet.getString("Division"));
                division.setCountry(new CountriesDAO(this.connection).getByID(resultSet.getInt("Country_ID")));
            }
            return division;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<Division> getAll() {
        List<Division> divisions = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ALL))) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Division division = new Division();
                division.setDivisionID(resultSet.getInt("Division_ID"));
                division.setDivision(resultSet.getString("Division"));
                division.setCountry(new CountriesDAO(this.connection).getByID(resultSet.getInt("Country_ID")));
                divisions.add(division);
            }
            return divisions;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

    }

    @Override
    public Division insert(Division object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, object.getID());
            statement.setString(2, object.getDivision());
            statement.setInt(3, object.getCountry().getID());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Division update(Division object) {
        return null;
    }

    @Override
    public void delete(int ID) {

    }
}
