package controllers;

import helpers.Prompts;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.*;
import logic.entities.Country;
import logic.entities.Customer;
import logic.entities.Division;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.io.IOException;

/**
 * The Controller for the modify customer page.
 */
@SuppressWarnings("ALL")
public class ModifyCustomerController {

    private final SceneController sceneController = new SceneController();

    @FXML
    private Label labelID;

    @FXML
    private TextField textName;

    @FXML
    private ComboBox<Country> comboCountry;

    @FXML
    private ComboBox<Division> comboDivision;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textPostalCode;

    @FXML
    private TextField textPhone;

    @FXML
    private Label labelTitle;
    /**
     * The form status.
     * Indicates whether the form is in "Add" or "Update" configuration.
     */
    private ActionType formStatus;

    /**
     * clears the form fields in preparation for new customer to be entered.
     */
    private void clearFields(){
        labelID.setText("Auto Generated");
        textName.clear();
        textPhone.clear();
        textPostalCode.clear();
        textAddress.clear();
        comboCountry.setValue(null);
        comboDivision.setValue(null);
    }

    /**
     * updates fields based on the provided customer
     * @param customer customer to update form fields to.
     */
    private void updateFields (Customer customer) {
        labelID.setText(String.valueOf(customer.getID()));
        textName.setText(customer.getCustomerName());
        textAddress.setText(customer.getAddress());
        textPostalCode.setText(customer.getPostalCode());
        textPhone.setText(customer.getPhone());
        comboDivision.setValue(customer.getDivision());
        comboCountry.setValue(customer.getDivision().getCountry());
    }


    /**
     * Sets form to add configuration..
     */
    public void setFormToAdd(){
        clearFields();
        labelTitle.setText("Add Customer");
        formStatus = ActionType.ADD;
    }


    /**
     * Sets form to update.
     * Calls functions to update form based on provided customer
     * @param customer the customer
     */
    public void setFormToUpdate(Customer customer) {
        labelTitle.setText("Update Customer");
        updateFields(customer);
        handleCountryChange(new ActionEvent());
        formStatus = ActionType.UPDATE;
    }

    /**
     * A filtered list of divisions based on country selection
     */
    private final FilteredList<Division> divisionsFilteredList = new FilteredList<>(DataCache.getDivisions(), s -> true);


    /**
     * Initialize.
     */
    public void initialize(){
        comboDivision.setItems(divisionsFilteredList);
        comboCountry.setItems(DataCache.getCountries());

    }

    /**
     * Handle cancel.
     *
     * @param event the event
     */
    @FXML
    void handleCancel(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.CANCEL, ItemType.CUSTOMER);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            try {
                sceneController.manageCustomers(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Handle country change.
     *
     * @param event the event
     */
    @FXML
    void handleCountryChange(ActionEvent event) {
        Country newValue = comboCountry.getValue();
        divisionsFilteredList.setPredicate(division -> {
            if (newValue == null) return true;
            return division.getCountry().equals(newValue);
        });
    }

    /**
     * Handle exit.
     * Lambda is used for clean inline definition of filter predicate
     * @param event the event
     */
    @FXML
    void handleExit(ActionEvent event) {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> sceneController.exit());
    }

    /**
     * Validates the customer boolean.
     *
     * @param customer the customer
     * @return the boolean validation status
     */
    boolean validateCustomer (Customer customer){
        if (customer.getCustomerName().isEmpty() || customer.getAddress().isEmpty() || customer.getPostalCode().isEmpty() || customer.getPhone().isEmpty()){
            Prompts.validationError("Please Complete All Fields").show();
            return false;
        }
        return true;
    }

    /**
     * Handle submit.
     * Checks the formStatus and determines whether to update or add customer
     * Validates customer fields.
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void handleSubmit(ActionEvent event) throws IOException {
        Customer customer = new Customer();
        customer.setCustomerName(textName.getText());
        customer.setDivision(comboDivision.getValue());
        customer.setAddress(textAddress.getText());
        customer.setPhone(textPhone.getText());
        customer.setPostalCode(textPostalCode.getText());
        if (!validateCustomer(customer)){
            return;
        }
        if (formStatus.equals(ActionType.ADD)){
            DataCache.addCustomer(customer);
        } else {
            customer.setCustomerID(Integer.parseInt(labelID.getText()));
            DataCache.updateCustomer(customer);
        }
        sceneController.manageCustomers(event);
    }

}
