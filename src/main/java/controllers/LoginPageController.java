package controllers;

import helpers.Prompts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.SessionInformation;

import java.io.IOException;
import java.util.ResourceBundle;


/**
 * The FXML Controller for the login page.
 */
public class LoginPageController {
    private final AppController appController = new AppController();

    @FXML
    private Label labelUsername;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label labelPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label labelTimeZone;

    @FXML
    private Label labelLocation;

    @FXML
    private Button buttonExit;

    @FXML
    private Label labelTitle;

    private ResourceBundle resourceBundle;

    /**
     * Initializes page.
     * Sets location label to systems ZoneID.
     */
    public void initialize(){
        this.resourceBundle = SessionInformation.getResourceBundle();
        labelLocation.setText(SessionInformation.getSystemZone());

        labelTimeZone.setText(resourceBundle.getString("time_zone"));
        labelTitle.setText(resourceBundle.getString("title"));
        labelUsername.setText(resourceBundle.getString("username"));
        labelPassword.setText(resourceBundle.getString("password"));
        buttonExit.setText(resourceBundle.getString("exit"));
        buttonLogin.setText(resourceBundle.getString("login"));

    }

    /**
     * Exits application.
     */
    @FXML
    void handleExit() {
            appController.exit();
    }

    /**
     * Action handler for login button.
     * Passes login credentials to DataCache login function
     * If login returns successful, transitions to main menu page
     *
     * @param event Button click event
     * @throws IOException if main menu cannot be loaded
     */
    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        String userName = txtUsername.getText().strip().toLowerCase();
        String password = txtPassword.getText().strip();
        if (SessionInformation.login(userName, password))
            appController.mainMenuFromLogin(event);
        else {
            Alert error = Prompts.failedLogin(resourceBundle);
            error.show();
            txtPassword.clear();
        }
    }

}
