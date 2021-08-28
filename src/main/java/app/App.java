package app;

import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.DataCache;
import logic.SessionInformation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Generates the JavaFX UI
 */
public class App extends Application {
    /**
     * @param primaryStage The initial stage generated
     * @throws Exception Passes all exceptions to the calling method
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Locale.setDefault(new Locale("fr", "FR")); // Uncomment to test french translation. Must import java.util.Locale;
        ResourceBundle rb = ResourceBundle.getBundle("language_files/login");
        SessionInformation.initialize();
        SessionInformation.setResourceBundle(rb);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/LoginPage.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The entry point of the JavaFX application.
     *
     * @param args the application input arguments
     */
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.startConnection();
            DataCache.initialize(connection);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        launch(args);
        try {
            DBConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
