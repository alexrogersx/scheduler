package controllers;

import helpers.Prompts;
import helpers.InputValidators;
import helpers.TextFieldLength;
import helpers.TimeSpinnerValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.*;
import logic.entities.Appointment;
import logic.entities.Contact;
import logic.entities.Customer;
import logic.entities.User;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The type Modify appointment controller.
 */
public class ModifyAppointmentController {

    private final SceneController sceneController = new SceneController();

    @FXML
    private Label labelID;

    @FXML
    private TextArea textDescription;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private Spinner<LocalTime> spinStartTime;

    @FXML
    private Spinner<LocalTime> spinEndTime;

    @FXML
    private TextField textTitle;

    @FXML
    private TextField textType;

    @FXML
    private ComboBox<Customer> comboCustomer;

    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private TextField textLocation;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private Label labelTitle;

    /**
     * The form status.
     * Indicates whether the form is in "Add" or "Update" configuration.
     */
    private ActionType formStatus;

    /**
     * Clears all fields and to prepare for an appointment to be added.
     */
    private void clearFields(){
        labelID.setText("Auto Generated");
        textTitle.clear();
        textDescription.clear();
        textLocation.clear();
        textType.clear();
        dateStart.setValue(LocalDate.now());
        dateEnd.setValue(LocalDate.now());
        spinStartTime.getValueFactory().setValue(null);
        spinEndTime.getValueFactory().setValue(null);
        comboCustomer.setValue(null);
        comboUser.setValue(null);
        comboContact.setValue(null);
    }

    /**
     * Updates all fields with data from provided appointment
     * @param appointment the appointment
     */
    private void updateFields(Appointment appointment){
        LocalDateTime startTime = appointment.getStart();
        LocalDateTime endTime = appointment.getEnd();

        labelID.setText(String.valueOf(appointment.getID()));
        textTitle.setText(appointment.getTitle());
        textDescription.setText(appointment.getDescription());
        textLocation.setText(appointment.getLocation());
        textType.setText(appointment.getType());
        dateStart.setValue(startTime.toLocalDate());
        dateEnd.setValue(endTime.toLocalDate());
        spinStartTime.getValueFactory().setValue(startTime.toLocalTime());
        spinEndTime.getValueFactory().setValue(endTime.toLocalTime());
        comboCustomer.setValue(appointment.getCustomer());
        comboUser.setValue(appointment.getUser());
        comboContact.setValue(appointment.getContact());
    }


    /**
     * Set form to add configuration.
     */
    public void setFormToAdd(){
        clearFields();
        labelTitle.setText("Add Appointment");
        formStatus = ActionType.ADD;
    }


    /**
     * Set form to update configuration.
     *
     * @param appointmentToUpdate the appointment to update form fields to.
     */
    public void setFormToUpdate(Appointment appointmentToUpdate){
        labelTitle.setText("Update Appointment");
        updateFields(appointmentToUpdate);
        formStatus = ActionType.UPDATE;
    }
    private final TextFieldLength textDescriptionLimit = new TextFieldLength(50);

    /**
     * Initialize.
     */
    public void initialize(){
        spinStartTime.setValueFactory(new TimeSpinnerValueFactory());
        spinEndTime.setValueFactory(new TimeSpinnerValueFactory());

        comboContact.setItems(DataCache.getContacts());
        comboUser.setItems(DataCache.getUsers());
        comboCustomer.setItems(DataCache.getCustomers());
        textDescription.setTextFormatter(textDescriptionLimit.getLengthFormatter());
    }

    /**
     * Handle cancel.
     * Prompts message confirming tne intent to cancel.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleCancel(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.CANCEL, ItemType.APPOINTMENT);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            try {
                sceneController.manageAppointments(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Handle exit.
     * Lambda is used for clean inline definition of filter predicate
     */
    @FXML
    void handleExit() {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> sceneController.exit());
    }

    /**
     * Checks the appointment against multiple validations
     * Returns promp indicating if and how the appointment is invalid
     * @param appointment the appointment
     * @return boolean validation status
     */
    private boolean validateAppointment (Appointment appointment){
        if (appointment.getTitle().isEmpty() || appointment.getDescription().isEmpty() || appointment.getType().isEmpty() || appointment.getLocation().isEmpty()) {
            Prompts.validationError("Please complete all form fields").show();
            return true;
        }
        else if (!InputValidators.validateAppointmentStartIsBeforeEnd(appointment)){
            Prompts.validationError("Appointment Start time must be before End time").show();
            return true;
        }
        else if (!InputValidators.validateAppointmentOverlap(appointment)){
            Prompts.validationError("Appointment time overlaps with an existing appointment for customer " + appointment.getCustomer().getCustomerName()).show();
            return true;
        }
        else if (!InputValidators.validateAppointmentTime(appointment)){
            Prompts.validationError("Appointment time is outside of business hours. \nBusiness hours are 8 AM to 10 PM EST").show();
            return true;
        }
        else
            return false;
    }


    /**
     * Handle submit.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleSubmit(ActionEvent event) throws IOException {
        LocalDateTime start = LocalDateTime.of(dateStart.getValue(), spinStartTime.getValue());
        LocalDateTime end = LocalDateTime.of(dateEnd.getValue(), spinEndTime.getValue());
        Appointment appointment = new Appointment();
        appointment.setTitle(textTitle.getText());
        appointment.setDescription(textDescription.getText());
        appointment.setType(textType.getText());
        appointment.setLocation(textLocation.getText());
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setCustomer(comboCustomer.getValue());
        appointment.setUser(comboUser.getValue());
        appointment.setContact(comboContact.getValue());

        if (formStatus == ActionType.ADD) {
            if (validateAppointment(appointment))
                return;
            DataCache.addAppointment(appointment);
        }
        else {
            appointment.setAppointmentID(Integer.parseInt(labelID.getText()));
            if (validateAppointment(appointment)) {
                return;
            }
            DataCache.updateAppointment(appointment);
        }

        sceneController.manageAppointments(event);
    }

}
