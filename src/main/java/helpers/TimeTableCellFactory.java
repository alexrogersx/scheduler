package helpers;

import javafx.scene.control.TableCell;
import logic.entities.Appointment;

import java.time.LocalDateTime;

/**
 * TableCellFactory object used to format a LocalDateTime object in an Appointment cell.
 */
public class TimeTableCellFactory extends TableCell<Appointment, LocalDateTime> {

    @Override
    protected void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
            setText(null);
        else
            setText(item.format(Format.dateTimeFormat1));
    }
}
