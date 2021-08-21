package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Countries dao.
 */
public class CountriesDAO extends DataAccessObject<Country> {
    private static final String INSERT = "INSERT INTO countries( Country_ID," +
            "Country  ) VALUES(?,?)";

    private static final String GET_ONE_BY_ID = "SELECT Country_ID, Country " +
            "FROM countries WHERE Country_ID = ?";

    private static final String GET_ALL = "SELECT Country_ID, Country " +
            "FROM countries";

    /**
     * Instantiates a new Countries dao.
     *
     * @param connection the connection
     */
    public CountriesDAO(Connection connection) {
        super(connection, "country");
    }

    @Override
    public Country getByID(int ID) {
        Country country = new Country();
        try (PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                country.setCountryID(resultSet.getInt("Country_ID"));
                country.setCountry(resultSet.getString("Country"));
            }
            return country;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

        @Override
    public List<Country> getAll() {
            List<Country> countries = new ArrayList<>();
            try (PreparedStatement statement = this.connection.prepareStatement((GET_ALL))) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Country country = new Country();
                    country.setCountryID(resultSet.getInt("Country_ID"));
                    country.setCountry(resultSet.getString("Country"));
                    countries.add(country);
                }
                return countries;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new RuntimeException(throwables);
            }
    }

    @Override
    public Country insert(Country object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, object.getID());
            statement.setString(2, object.getCountry());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Country update(Country object) {
        return null;
    }

    @Override
    public void delete(int ID) {

    }
}
