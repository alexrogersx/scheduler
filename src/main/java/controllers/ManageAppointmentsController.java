package controllers;

import helpers.Prompts;
import helpers.TimeTableCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableArray;
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
 * The FXML Controller for the manage appointments page.
 */
@SuppressWarnings("ALL")
public class ManageAppointmentsController {

    private final AppController appController = new AppController();

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
     * Filtered list used to filter appointments by either the customer or contact combo boxes
     */
    private final FilteredList<Appointment> appointmentsComboFilteredList = new FilteredList<Appointment>(appointmentDateFilteredList, s -> true);
    /**
     * Filtered list used to filter appointments by text parameter passed in the textFilter field
     */
    private final FilteredList<Appointment> appointmentsTextFilteredList = new FilteredList<Appointment>(appointmentsComboFilteredList, s -> true);

    /**
     * Deep copys the provided list and adds a null value to the first index.
     * @param list a list which to copy add a null value to
     * @param <E> And observablelist to be coppied
     * @return a copy of the Observable List
     */
    private static < E > ObservableList addNull(ObservableList< E > list) {
        ObservableList < E > newList =  FXCollections.observableArrayList();
        newList.addAll(list);
        if (newList.get(0) != null) newList.add(0, null);
        return newList;
    }

    /**
     * Initializes the scene.
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

        // retrieve customer + contact data and adds a null value. This will eventually The null value is used
        //  in a combo box to filter the tables. The null will be selectable as "no filter" option.
        ObservableList<Contact> contactsWithNull = addNull(DataCache.getContacts());
//        ObservableList<Contact> contactsWithNull = DataCache.getContacts();
        ObservableList<Customer> customersWithNull = addNull(DataCache.getCustomers());
//        ObservableList<Customer> customersWithNull = DataCache.getCustomers();

        comboContactFilter.setItems(contactsWithNull);
        comboCustomerFilter.setItems(customersWithNull);

        /**
         * assigns event listener to textProperty
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
     * Eventhandler for the Add button
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleAdd(ActionEvent event) throws IOException {
        appController.addAppointment(event);
    }

    /**
     * Eventhandler for the All sort button
     * @param event the event
     */
    @FXML
    void handleAll(ActionEvent event) {
        appointmentDateFilteredList.setPredicate(s -> true);
    }


    /**
     * Handle contact filter combo box.
     * @param event the event
     */
    @FXML
    void handleContactFilter(ActionEvent event) {
        if (comboContactFilter.getValue() == null){
            appointmentsComboFilteredList.setPredicate(s->true);
            return;
        }
        appointmentsComboFilteredList.setPredicate(appointment -> appointment.getContact().equals(comboContactFilter.getValue()));
        // reset other appointment filter
        comboCustomerFilter.setValue(null);
    }

    /**
     * Handle customer filter.
     * @param event the event
     */
    @FXML
    void handleCustomerFilter(ActionEvent event) {
        if (comboCustomerFilter.getValue() == null){
            appointmentsComboFilteredList.setPredicate(s-> true);
            return;
        }
        appointmentsComboFilteredList.setPredicate(appointment -> appointment.getCustomer().equals(comboCustomerFilter.getValue()));
        // reset other appointment filter
        comboContactFilter.setValue(null);
    }

    /**
     * Handle exit.
     * @param event the event
     */
    @FXML
    void handleExit(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            appController.exit();
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
        appController.mainMenu(event);
    }

    /**
     * Eventhandler for the This Month radio button
     * @param event the event
     */
    @FXML
    void handleMonthly(ActionEvent event) {
        appointmentDateFilteredList.setPredicate(s -> s.getStart().get(ChronoField.MONTH_OF_YEAR) == (LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR)));
    }

    /**
     * Handle remove.
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
                appController.mainMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Eventhandler for selecting an appointment in the table
     * writes selected appointment to variable for future use
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
            appController.updateAppointment(event, selectedAppointment);
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
