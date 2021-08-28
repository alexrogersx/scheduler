package controllers;

import helpers.Prompts;
import helpers.UpcomingAppointmentCellFactory;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.SessionInformation;
import logic.entities.Appointment;
import logic.DataCache;
import logic.enums.ActionType;
import logic.enums.ItemType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The FXML Controller for the main menu page.
 */
public class MainMenuController {

    private final AppController appController = new AppController();

    @FXML
    private ListView<Appointment> listViewAppointments;

    @FXML
    private Label labelUpcomingAppointments;

    /**
     * Predicate to compare filtered appointments, checks if time between appointment and local time plus 15 minutes
     * @param appointment an appointment to be compared
     * @return boolean whether appointment is within 15 minutes
     */
    private boolean upcomingAppointmentPredicate (Appointment appointment) {
        return  Math.abs(ChronoUnit.MINUTES.between(appointment.getStart(), LocalDateTime.now().plusMinutes(15))) <= 15;
    }

    private final FilteredList<Appointment> appointmentUpcomingFilteredList = new FilteredList<>(DataCache.getAppointments(), this::upcomingAppointmentPredicate);

    /**
     * Login.
     */
    public void login(){
        Dialog<String> inform = Prompts.informUpcomingAppointments(appointmentUpcomingFilteredList);
        inform.show();
    }

    /**
     * Initialize.
     */
    public void initialize(){
        if (appointmentUpcomingFilteredList.size() <1){
            listViewAppointments.setDisable(true);
            labelUpcomingAppointments.setText("No Upcoming Appointments");
        }
        listViewAppointments.setItems(appointmentUpcomingFilteredList);
        listViewAppointments.setCellFactory(f -> new UpcomingAppointmentCellFactory());
    }

    /**
     * Handle exit.
     * Lambda is used for clean inline definition of filter predicate
     */
    @FXML
    void handleExit() {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> appController.exit());
    }

    /**
     * Handle generate report.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleGenerateReport(ActionEvent event) throws IOException {
        appController.reportPage(event);
    }

    /**
     * Handle logout.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        SessionInformation.logout();
        appController.loginPage(event);
    }

    /**
     * Handle manage appointments.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleManageAppointments(ActionEvent event) throws IOException {
        appController.manageAppointments(event);
    }

    /**
     * Handle manage customers.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleManageCustomers(ActionEvent event) throws IOException {
        appController.manageCustomers(event);
    }

}
