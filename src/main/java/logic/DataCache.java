package logic;

import db.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.entities.*;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Acts as a single update point and cache for all DAO operations
 * Reduces data retrieval time by storing a local copy of data
 */
public abstract class DataCache {
    private static ObservableList<Appointment> appointments;
    private static ObservableList<Customer> customers;
    private static ObservableList<Contact> contacts;
    private static ObservableList<Division> divisions;
    private static ObservableList<Country> countries;
    private static ObservableList<User> users;

    private static AppointmentsDAO appointmentsDAO;
    private static CustomersDAO customersDAO;
    private static DivisionsDAO divisionsDAO;
    private static CountriesDAO countriesDAO;
    private static UsersDAO usersDAO;
    private static ContactsDAO contactsDAO;

    /**
     * Initializes cache
     * @param newConnection Connection type representing connection to database
     */
    public static void initialize (Connection newConnection) {
        appointmentsDAO = new AppointmentsDAO(newConnection);
        customersDAO = new CustomersDAO(newConnection);
        divisionsDAO = new DivisionsDAO(newConnection);
        countriesDAO = new CountriesDAO(newConnection);
        contactsDAO = new ContactsDAO(newConnection);
        usersDAO = new UsersDAO(newConnection);

        refreshAppointments();
        refreshCustomers();
        refreshContacts();
        refreshCountries();
        refreshDivisions();
        refreshUsers();
    }

    /**
     * Retrieves all appointments for a specified customer
     * Lambda used for clear inline definition of filter predicate
     * @param customer Customer object to retrieve appointment for
     * @return List of Appointment objects for specified customer
     */
    public static List<Appointment> getAppointmentsByCustomer (Customer customer) {
        return appointments.stream()
                .filter(a -> a.getCustomer().getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toList());
    }

    /**
     * Inserts customer into Database.
     * Updates local data cache
     * @param newCustomer Customer object to add to database
     */
    public static void addCustomer(Customer newCustomer){ customers.add(customersDAO.insert(newCustomer)); }
    /**
     * Updates customer in Database.
     * Updates local data cache
     * @param customerToUpdate Customer object to update
     */
    public static void updateCustomer(Customer customerToUpdate){  customers.set(customers.indexOf(customerToUpdate), customersDAO.update(customerToUpdate));}
    /**
     * Deletes customer from Database.
     * Updates local data cache
     * @param customerToDelete Customer object to delete
     */
    public static void deleteCustomer(Customer customerToDelete){
        customers.remove(customerToDelete);
        customersDAO.delete(customerToDelete.getCustomerID())
        ;}
    /**
     * Inserts appointment into Database.
     * Updates local data cache
     * @param newAppointment Appointment object to add to database
     */
    public static void addAppointment(Appointment newAppointment){ appointments.add(appointmentsDAO.insert(newAppointment));}
    /**
     * Updates Appointment in Database.
     * Updates local data cache
     * @param appointmentToUpdate Appointment object to update
     */
    public static void updateAppointment(Appointment appointmentToUpdate){ appointments.set( appointments.indexOf(appointmentToUpdate),appointmentsDAO.update(appointmentToUpdate));}
    /**
     * Deletes appointment from Database.
     * Updates local data cache
     * @param appointmentToDelete Appointment object to delete
     */
    public static void deleteAppointment(Appointment appointmentToDelete){
        appointments.remove(appointmentToDelete);
        appointmentsDAO.delete(appointmentToDelete.getAppointmentID());
    }

    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshUsers(){
        users = FXCollections.observableArrayList(usersDAO.getAll());
    }
    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshAppointments(){
        appointments = FXCollections.observableArrayList(appointmentsDAO.getAll());
    }
    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshCustomers(){
        customers = FXCollections.observableArrayList(customersDAO.getAll());
    }
    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshContacts(){
        contacts = FXCollections.observableArrayList(contactsDAO.getAll());
    }
    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshDivisions(){
        divisions = FXCollections.observableArrayList(divisionsDAO.getAll());
    }
    /**
     * Refreshes local data cache for this entity type
     */
    public static void refreshCountries(){
        countries = FXCollections.observableArrayList(countriesDAO.getAll());
    }

    /**
     * Retrieves customer list from local cache
     * @return List of Customer type objects
     */
    public static ObservableList<Customer> getCustomers() {
        return customers;
    }
    /**
     * Retrieves contact list from local cache
     * @return List of Contact type objects
     */
    public static ObservableList<Contact> getContacts() {
        return contacts;
    }
    /**
     * Retrieves divisions list from local cache
     * @return List of Division type objects
     */
    public static ObservableList<Division> getDivisions() {
        return divisions;
    }
    /**
     * Retrieves countries list from local cache
     * @return List of Country type objects
     */
    public static ObservableList<Country> getCountries() {
        return countries;
    }
    /**
     * Retrieves appointment list from local cache
     * @return List of Appointment type objects
     */
    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }
    /**
     * Retrieves user list from local cache
     * @return List of User type objects
     */
    public static ObservableList<User> getUsers() {
        return users;
    }

}

