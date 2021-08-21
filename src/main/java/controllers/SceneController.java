package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.entities.Appointment;
import logic.entities.Customer;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The type Scene controller.
 * Acts as a single point of control for loading scenes in projext
 */
public class SceneController {
    private Stage stage;
    private Scene scene;

    private ResourceBundle resourceBundle;

    /**
     * Login page page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/LoginPage.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Manage customers page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void manageCustomers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ManageCustomers.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main menu page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void mainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main menu from login page transition.
     * Special case when transitioning from login page
     * calls login() function on MainMenuController to pass logic indicating that the app is transitioning from login page
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void mainMenuFromLogin(ActionEvent event) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent root = rootLoader.load();
        MainMenuController controller = rootLoader.getController();
        controller.login();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Manage appointments page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void manageAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ManageAppointments.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Update appointment page transition.
     *
     * @param event       the event
     * @param appointment the appointment
     * @throws IOException the io exception
     */
    public void updateAppointment(ActionEvent event, Appointment appointment) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyAppointment.fxml"));
        Parent root = rootLoader.load();
        ModifyAppointmentController controller = rootLoader.getController();
        controller.setFormToUpdate(appointment);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Add appointment page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void addAppointment(ActionEvent event) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyAppointment.fxml"));
        Parent root = rootLoader.load();
        ModifyAppointmentController controller = rootLoader.getController();
        controller.setFormToAdd();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Add customer page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyCustomer.fxml"));
        Parent root = rootLoader.load();
        ModifyCustomerController controller = rootLoader.getController();
        controller.setFormToAdd();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Update customer page transition.
     *
     * @param event    the event
     * @param customer the customer
     * @throws IOException the io exception
     */
    public void updateCustomer(ActionEvent event, Customer customer) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyCustomer.fxml"));
        Parent root = rootLoader.load();
        ModifyCustomerController controller = rootLoader.getController();
        controller.setFormToUpdate(customer);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Report page page transition.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void reportPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reports.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exit.
     */
    public void exit() {
        Platform.exit();
        System.exit(0);
    }

}
