package helpers;

import javafx.scene.control.ListCell;
import logic.entities.Appointment;

/**
 * CellFactory object used to format an Appointment object in a list cell
 * Displays appointments title and formatted time
 */
public class UpcomingAppointmentCellFactory extends ListCell<Appointment> {

    @Override
    public void updateItem(Appointment appointment, boolean empty) {
        super.updateItem(appointment, empty);
        if (empty)
            setText(null);
        else
            setText(appointment.getTitle() +
                " " + appointment.getStart().format(Format.timeFormat1));

    }
}
