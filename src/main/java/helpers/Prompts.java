package helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import logic.entities.Appointment;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Abstract class with static methods to organize all prompt messages used in app.
 */
public abstract class Prompts {

    /**
     * Creates a confirmation alert
     * Acts as a single update point for all confirmation alerts used in app
     * @param confirmType ActionType enum
     * @param itemType Item Type enum
     * @return Portable Alert object
     */
    public static Alert confirm(ActionType confirmType, ItemType itemType) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(confirmType.label + " " + itemType.label);
        confirm.setContentText("Are you sure you want to " + confirmType.label + " " + itemType.label  );
        return confirm;
    }

    /**
     * Creates an alert to be used when a login attempt fails
     * @param resourceBundle a ResourceBundle with language information for alert message
     * @return Portable Alert object
     */
    public static Alert failedLogin(ResourceBundle resourceBundle) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(resourceBundle.getString("alert_title"));
        error.setContentText(resourceBundle.getString("content_title"));
        return error;
    }

    /**
     * Creates a dialog to act as a confirmation of a deleted appointment
     * @param appointment the Appointment object that has been deleted
     * @return portable Dialog object
     */
    public static Dialog<String> informDeletedAppointment (Appointment appointment){
        Dialog<String> dialog = new Dialog<>();
        String contentText = appointment.getType() + " appointment ID: " + String.valueOf(appointment.getAppointmentID()) + " has been removed.";
        dialog.setContentText(contentText);
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(ok);
        return dialog;

    }

    /**
     * creates a dialog to act as an information prompt about upcoming appointments
     * @param upcomingAppointments the Appointment object that is upcoming
     * @return portable Dialog object
     */
    public static Dialog<String> informUpcomingAppointments(List<Appointment> upcomingAppointments) {
        Dialog<String> dialog = new Dialog<String>();
        StringBuilder contentText = new StringBuilder();
        if (upcomingAppointments.size()> 1) dialog.setTitle( String.valueOf(upcomingAppointments.size()) + " Upcoming Appointments");
        else if (upcomingAppointments.size() == 1) dialog.setTitle("One Upcoming Appointment");
        else dialog.setTitle("No Upcoming Appointments");

        for (Appointment appointment: upcomingAppointments
             ) {
            //string builder
            contentText.append("\n").append(appointment.getTitle()).append(" with ").append(appointment.getCustomer()
                    .getCustomerName()).append(" at ").append(appointment.getStart().format(Format.timeFormat1));
        }
        dialog.setContentText(contentText.toString());
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(ok);
        return dialog;
    }

    /**
     * Creates a validation error alert
     * Acts as a single update point for all validation alerts used in app
     * @param context text context string to be displayed inside of alert message
     * @return Portable Alert object.
     */
    public static Alert validationError(String context){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Validation Error");
        error.setContentText(context);
        return error;
    }


}
