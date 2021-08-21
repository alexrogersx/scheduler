package controllers;

import helpers.Prompts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.*;
import logic.entities.Appointment;
import logic.enums.ItemType;
import logic.enums.ActionType;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The type controller for the report page.
 */
public class ReportsController {

    private final SceneController sceneController = new SceneController();


    @FXML
    private ListView<String> listViewAppointments;

    @FXML
    private ComboBox<String> comboMonth;

    /**
     * A filtered list of appointments
     * To be filtered by the month combo selection
     *
     * Lambda is used for clean inline definition of a filter predicate
     */
    private final FilteredList<Appointment> appointmentsComboFilteredList = new FilteredList<>(DataCache.getAppointments(), s -> true);

    /**
     * Populates list view
     *
     * Lambda is used for clean inline use with Stream API
     */
    private void populateList () {
        ObservableList<String> appointmentList = appointmentsComboFilteredList.stream()
                .map(Appointment::getType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().concat(" Meeting Occurrences: " + entry.getValue()))
                .collect(Collector.of(FXCollections::observableArrayList, ObservableList::add, (l1, l2) -> { l1.addAll(l2); return l1; }));
        listViewAppointments.setItems(appointmentList);
        if (appointmentList.size() < 1){
            ObservableList<String> message = FXCollections.observableArrayList("There Are No Appointments In the Month of " +comboMonth.getValue());
            listViewAppointments.setItems(message);
        }
    }

    /**
     * Initialize.
     */
    public void initialize(){
        populateList();
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        ObservableList<String> months = FXCollections.observableArrayList(dateFormatSymbols.getMonths());
        months.add(0, "All");
        months.remove(months.size()-1);
        comboMonth.setItems(months);
        comboMonth.setValue("All");
    }

    /**
     * Handle exit.
     *
     * Lambda is used for clean inline definition of a filter predicate
     */
    @FXML
    void handleExit() {
        Alert confirm = Prompts.confirm(ActionType.EXIT, ItemType.APPLICATION);
        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> sceneController.exit());
    }

    /**
     * Handle filter month.
     * Filters appointments by month
     *
     * Lambda is used for clean inline definition of a filter predicate
     */
    @FXML
    void handleFilterMonth() {
        if (comboMonth.getValue().equals("All") || comboMonth.getValue().isEmpty()){
            appointmentsComboFilteredList.setPredicate(s->true);
        }else{
            appointmentsComboFilteredList.setPredicate(appointment -> appointment.getStart().getMonth().toString().equalsIgnoreCase(comboMonth.getValue()));
        }
        populateList();
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

}
