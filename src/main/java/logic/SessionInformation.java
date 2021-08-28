package logic;

import logic.entities.User;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * The type Session information.
 * handles logic related to the current session
 */
public abstract class SessionInformation {

    private static User CURRENT_USER;
    private static String SYSTEM_LANGUAGE;
    private static String SYSTEM_ZONE;

    private static ResourceBundle resourceBundle;

    /**
     * The constant loginLogger.
     */
    public static Logger loginLogger;
    public static Logger errorLogger;

    /**
     * Checks login credentials and logs user in
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public static boolean login(String username, String password){
        if (username.isEmpty() || password.isEmpty()){
            return false;
        }
        DataCache.refreshUsers();
        for (User user : DataCache.getUsers()
             ) {
            if (user.getPassword().equals(password) && user.getUserName().equals(username)){
                CURRENT_USER = user;
                loginLogger.log(Level.INFO, "Successful Login Attempt Username: " + username );
                return true;
            }
        }
        loginLogger.warning("Unsuccessful Login Attempt Username: " + username);
        return false;
    }

    /**
     * Initialize.
     *
     * @throws IOException the io exception
     */
    public static void initialize() throws IOException {
        getSystemLanguage();
        getSystemZone();
        loginLogger = Logger.getLogger("login_activity.txt");
        errorLogger = Logger.getLogger("error_logs.txt");

        FileHandler loginFileHandler = new FileHandler("login_activity.txt", true);
        FileHandler errorFileHandler = new FileHandler("error_logs.txt", true);

        SimpleFormatter simpleFormatter = new SimpleFormatter();
        loginFileHandler.setFormatter(simpleFormatter);
        loginLogger.addHandler(loginFileHandler);
        loginLogger.setLevel(Level.ALL);

        errorFileHandler.setFormatter(simpleFormatter);
        errorLogger.addHandler(errorFileHandler);
        errorLogger.setLevel(Level.WARNING);
    }

    /**
     * Get system language.
     */
    public static void getSystemLanguage(){
        SYSTEM_LANGUAGE = Locale.getDefault().getLanguage();
    }

    /**
     * Get system zone as string.
     *
     * @return the string
     */
    public static String getSystemZone(){
        return SYSTEM_ZONE = ZoneId.systemDefault().getId();
    }

    /**
     * Logout.
     */
    public static void logout(){
        CURRENT_USER = null;
    }

    /**
     * Gets resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Sets resource bundle.
     *
     * @param resourceBundle the resource bundle
     */
    public static void setResourceBundle(ResourceBundle resourceBundle) {
        SessionInformation.resourceBundle = resourceBundle;
    }
}
