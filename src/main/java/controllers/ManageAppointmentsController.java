package controllers;

import helpers.Prompts;
import helpers.TimeTableCellFactory;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.*;
import logic.entities.Appointment;
import logic.entities.Contact;
import logic.entities.Customer;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;

/**
 * The Controller for the manage appointments page.
 */
@SuppressWarnings("ALL")
public class ManageAppointmentsController {

    private final SceneController sceneController = new SceneController();

    @FXML
    private TextField textFilter;

    @FXML
    private RadioButton radioWeekly;

    @FXML
    private ToggleGroup Sort;

    @FXML
    private RadioButton radioMonthly;

    @FXML
    private RadioButton radioAll;

    @FXML
    private ComboBox<Contact> comboContactFilter;

    @FXML
    private ComboBox<Customer> comboCustomerFilter;

    @FXML
    private ComboBox<ChronoField> comboSort;

    @FXML
    private TableView<Appointment> tableAppointment;

    @FXML
    private TableColumn<Appointment, Integer> columnAppointmentID;

    @FXML
    private TableColumn<Appointment, String> columnTitle;

    @FXML
    private TableColumn<Appointment, String> columnDescription;

    @FXML
    private TableColumn<Appointment, String> columnLocation;

    @FXML
    private TableColumn<Appointment, Contact> columnContact;

    @FXML
    private TableColumn<Appointment, String> columnType;

    @FXML
    private TableColumn<Appointment, LocalDateTime> columnStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> columnEnd;

    @FXML
    private TableColumn<Appointment, Customer> columnCustomerID;

    @FXML
    private Label labelTitle;
    /**
     * Top level filtered list.
     * Used to filter appointments by date
     * Lambda is used for clean inline definition of filter predicate
     */
    private final FilteredList<Appointment> appointmentDateFilteredList = new FilteredList<>(DataCache.getAppointments(), s -> true);
    /**
     * Filtered list used to filter appointments by either of the two customer, contact combo boxes
     * Lambda is used for clean inline definition of filter predicate
     */
    private final FilteredList<Appointment> appointmentsComboFilteredList = new FilteredList<Appointment>(appointmentDateFilteredList, s -> true);
    /**
     * Filtered list used to filter appointments by text parameter passed in the textFilter field
     * Lambda is used for clean inline definition of filter predicate
     */
    private final FilteredList<Appointment> appointmentsTextFilteredList = new FilteredList<Appointment>(appointmentsComboFilteredList, s -> true);

    /**
     * Initialize.
     */
    public void initialize(){

        tableAppointment.setItems(appointmentsTextFilteredList);
        columnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        columnStart.setCellFactory(callback -> new TimeTableCellFactory());
        columnEnd.setCellFactory(callback -> new TimeTableCellFactory());

        ObservableList<Contact> contactsWithNull = DataCache.getContacts();
        contactsWithNull.add(0, null);
        ObservableList<Customer> customersWithNull = DataCache.getCustomers();
        customersWithNull.add(0, null);

        comboContactFilter.setItems(contactsWithNull);
        comboCustomerFilter.setItems(customersWithNull);

        /**
         * assigns event listener to textProperty
         * @Lambda is used for clean inline definition of event handler
         */
        textFilter.textProperty().addListener((observable, oldValue, newValue) -> appointmentsTextFilteredList.setPredicate(appointment -> {
            if (newValue.isEmpty()) return true;
            if (appointment.getTitle().toLowerCase().contains(newValue.toLowerCase())) return true;
            try {
                if ( appointment.getAppointmentID() == Integer.parseInt(newValue)) return true;
            }
            catch (Exception ignored) {
            }
            return false;
        }) );
    }

    /**
     * Handle add.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleAdd(ActionEvent event) throws IOException {
        sceneController.addAppointment(event);
    }

    /**
     * Handle all.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleAll(ActionEvent event) {
        appointmentDateFilteredList.setPredicate(s -> true);
    }


    /**
     * Handle contact filter.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleContactFilter(ActionEvent event) {
        if (comboContactFilter.getValue() == null){
            appointmentsComboFilteredList.setPredicate(s->true);
            return;
        }
        appointmentsComboFilteredList.setPredicate(appointment -> appointment.getContact().equals(comboContactFilter.getValue()));
        comboCustomerFilter.setValue(null);
    }

    /**
     * Handle customer filter.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleCustomerFilter(ActionEvent event) {
        if (comboCustomerFilter.getValue() == null){
            appointmentsComboFilteredList.setPredicate(s-> true);
            return;
        }
        appointmentsComboFilteredList.setPredicate(appointment -> appointment.getCustomer().equals(comboCustomerFilter.getValue()));
        comboContactFilter.setValue(null);
    }

    /**
     * Handle exit.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleExit(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            sceneController.exit();
        });
    }

    /**
     * Handle menu.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        sceneController.mainMenu(event);
    }

    /**
     * Handle monthly.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleMonthly(ActionEvent event) {
        appointmentDateFilteredList.setPredicate(s -> s.getStart().get(ChronoField.MONTH_OF_YEAR) == (LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR)));
    }

    /**
     * Handle remove.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleRemove(ActionEvent event) throws IOException {
        Alert confirm = Prompts.confirm(ActionType.REMOVE, ItemType.APPOINTMENT);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            try {
                DataCache.deleteAppointment(selectedAppointment);
                Prompts.informDeletedAppointment(selectedAppointment).showAndWait();
                sceneController.mainMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Handle select appointment.
     *
     * @param event the event
     */
    @FXML
    void handleSelectAppointment(MouseEvent event) {
        selectedAppointment = tableAppointment.getSelectionModel().getSelectedItem();
    }

    /**
     * The Currently selected appointment.
     */
    Appointment selectedAppointment;

    /**
     * Handle update.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleUpdate(ActionEvent event) throws IOException {
        if (selectedAppointment != null)
            sceneController.updateAppointment(event, selectedAppointment);
    }

    /**
     * Handle weekly.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleWeekly(ActionEvent event) {
        appointmentDateFilteredList.setPredicate(s -> s.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == (LocalDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)));
    }

}
