package app;

import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Main.
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        Locale.setDefault(new Locale("fr", "FR"));
        ResourceBundle rb = ResourceBundle.getBundle("language_files/login");
        SessionInformation.initialize();
        SessionInformation.setResourceBundle(rb);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/LoginPage.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
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
