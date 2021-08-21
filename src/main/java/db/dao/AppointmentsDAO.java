package db.dao;

import db.dao.util.DataAccessObject;
import logic.entities.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Appointments dao.
 */
public class AppointmentsDAO extends DataAccessObject<Appointment> {
    private static final String INSERT = "INSERT INTO appointments( Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID ) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String GET_ONE_BY_ID = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID " +
            "FROM appointments WHERE Appointment_ID = ?";

    private static final String GET_ALL = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID " +
            "FROM appointments";

    private static final String UPDATE = "UPDATE appointments SET Appointment_ID = ?, Title =? , Description =? , " +
            "Location =?, Type =?, Start =? , End =?, Customer_ID =?, User_ID =?, Contact_ID =? " +
            "WHERE Appointment_ID = ?";

    private static final String DELETE = "DELETE FROM appointments WHERE Appointment_ID = ?";

    /**
     * Instantiates a new Appointments dao.
     *
     * @param connection the connection
     */
    public AppointmentsDAO(Connection connection) {
        super(connection, "appointments");
    }

    @Override
    public Appointment getByID(int ID) {
        Appointment appointment = new Appointment();
        try(PreparedStatement statement = this.connection.prepareStatement((GET_ONE_BY_ID))) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStart(resultSet.getTimestamp("Start").toLocalDateTime());
                appointment.setEnd(resultSet.getTimestamp("End").toLocalDateTime());
                appointment.setCustomer(new CustomersDAO(this.connection).getByID(resultSet.getInt("Customer_ID")));
                appointment.setUser(new UsersDAO(this.connection).getByID(resultSet.getInt("User_ID")));
                appointment.setContact(new ContactsDAO(this.connection).getByID(resultSet.getInt("Contact_ID")));
            }
            return appointment;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStart(resultSet.getTimestamp("Start").toLocalDateTime());
                appointment.setEnd(resultSet.getTimestamp("End").toLocalDateTime());
                appointment.setCustomer(new CustomersDAO(this.connection).getByID(resultSet.getInt("Customer_ID")));
                appointment.setUser(new UsersDAO(this.connection).getByID(resultSet.getInt("User_ID")));
                appointment.setContact(new ContactsDAO(this.connection).getByID(resultSet.getInt("Contact_ID")));
                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        return appointments;
    }

    @Override
    public Appointment insert(Appointment object) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getDescription());
            statement.setString(3, object.getLocation());
            statement.setString(4, object.getType());
            statement.setTimestamp(5, Timestamp.valueOf(object.getStart()));
            statement.setTimestamp(6, Timestamp.valueOf(object.getEnd()));
            statement.setInt(7, object.getCustomer().getID());
            statement.setInt(8, object.getUser().getID());
            statement.setInt(9, object.getContact().getID());
            statement.execute();
            int last_ID = this.getLastID(statement);
            return this.getByID(last_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Appointment update(Appointment object) {
        Appointment appointment;
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
            statement.setInt(1, object.getID());
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setString(4, object.getLocation());
            statement.setString(5, object.getType());
            statement.setTimestamp(6, Timestamp.valueOf(object.getStart()));
            statement.setTimestamp(7, Timestamp.valueOf(object.getEnd()));
            statement.setInt(8, object.getCustomer().getID());
            statement.setInt(9, object.getUser().getID());
            statement.setInt(10, object.getContact().getID());
            statement.setInt(11, object.getID());
            statement.execute();
            appointment = this.getByID(object.getAppointmentID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        return appointment;
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
