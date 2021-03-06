package controllers;

import helpers.Prompts;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.*;
import logic.entities.Country;
import logic.entities.Customer;
import logic.entities.Division;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.io.IOException;
import java.util.logging.Level;

/**
 * The FXML Controller for the manage customer page.
 */
public class ManageCustomersController {

    private final AppController appController = new AppController();

    @FXML
    private TextField textFilter;

    @FXML
    private TableView<Customer> tableCustomer;

    @FXML
    private TableColumn<Customer, Integer> columnCustomerID;

    @FXML
    private TableColumn<Customer, String> columnName;

    @FXML
    private TableColumn<Customer, String> columnAddress;

    @FXML
    private TableColumn<Customer, String> columnPostalCode;

    @FXML
    private TableColumn<Customer, String> columnPhone;

    @FXML
    private TableColumn<Customer, Division> columnDivision;

    @FXML
    private TableColumn<Customer, Country> columnCountry;

    private FilteredList<Customer> customersFilteredList = new FilteredList<>(DataCache.getCustomers(), s -> true);


    /**
     * Initialize.
     */
    public void initialize(){

        tableCustomer.setItems(customersFilteredList);
        columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        textFilter.textProperty().addListener((observable, oldValue, newValue) -> customersFilteredList.setPredicate(customer -> {
            if (newValue.isEmpty()) return true;
            if (customer.getCustomerName().toLowerCase().contains(newValue.toLowerCase())) return true;
            try {
                if ( customer.getCustomerID() == Integer.parseInt(newValue)) return true;
            }
            catch (Exception ignore) {
            }
            return false;
        }) );
    }

    /**
     * Eventhandler called when a customer is selected, stores selected customer in a variable to allow for access by
     * other methods.
     *
     */
    @FXML
    void handleCustomerSelect() {
        selectedCustomer = (tableCustomer.getSelectionModel().getSelectedItem());
    }
    private Customer selectedCustomer;

    /**
     * Handle add.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleAdd(ActionEvent event) throws IOException {
        appController.addCustomer(event);
    }

    /**
     * Handle exit.
     */
    @FXML
    void handleExit() {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> appController.exit());
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
     * Handle remove.
     * Removes customer from database
     * Confirms the intention to delete, then informs on successful delete
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleRemove(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.REMOVE, ItemType.CUSTOMER);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            try {
                if (DataCache.getAppointmentsByCustomer(selectedCustomer).size() > 0){
                    Prompts.validationError("Cannot Delete Customer \nCustomer Has Existing Appointments").show();
                    return;
                }
                DataCache.deleteCustomer(selectedCustomer);
                appController.mainMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Handle update.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleUpdate(ActionEvent event) throws IOException {
        if (selectedCustomer != null)
            appController.updateCustomer(event, selectedCustomer);
    }

}
