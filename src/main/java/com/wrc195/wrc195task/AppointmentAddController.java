package com.wrc195.wrc195task;

import DAO.AppointmentsQuery;
import DAO.ContactsQuery;
import DAO.CustomersQuery;
import DAO.UsersQuery;
import helper.Alerts;
import helper.Misc;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {

    private final ObservableList<Contacts> allContacts = ContactsQuery.getAllContacts();
    private final ObservableList<Customers> allCustomers = CustomersQuery.getAllCustomers();
    private final ObservableList<Users> allUsers = UsersQuery.getAllUsers();

    //private int generatedID = 1;

    @FXML
    private TextArea apptDescriptionTxt;

    @FXML
    private DatePicker apptEndDatePicker;

    @FXML
    private Spinner<LocalTime> apptEndTimeSpinner;

    @FXML
    private TextField apptIDTxt;

    @FXML
    private TextField apptLocationText;

    @FXML
    private DatePicker apptStartDatePicker;

    @FXML
    private Spinner<LocalTime> apptStartTimeSpinner;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ChoiceBox<Contacts> contactCBox;

    @FXML
    private ChoiceBox<Customers> customerIDCBox;

    @FXML
    private ChoiceBox<Users> userIDCBox;

    /**
     * Cancel current appointment form
     * Display a confirmation dialog box
     *
     * @param event Cancel form button
     * @throws IOException From FXMLoader
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alerts.getConfirmation(event,2);
    }

    /**
     * Create a new appointment entry in the database.
     * Check for blank/empty text boxes, as well as unselected choice boxes.
     * Check if appointment date/time is within business hours and if it does not overlap existing appointments.
     *
     * @param event Save appointment button
     * @throws SQLException For unhandled SQL exceptions
     * @throws IOException For FXMLLoader.
     */
    @FXML
    void onActionSaveAppt(ActionEvent event) throws SQLException, IOException {
        String title = apptTitleTxt.getText();
        String description = apptDescriptionTxt.getText();
        String location = apptLocationText.getText();
        String type = apptTypeTxt.getText();

        // Handle null pointer exception
        Customers customer = customerIDCBox.getValue();
        if (customer == null) {
            Alerts.getError(13);
            return;
        }
        int customerID = customerIDCBox.getValue().getCustomerID();

        // Handle null pointer exception
        Contacts contact = contactCBox.getValue();
        if (contact == null) {
            Alerts.getError(14);
            return;
        }
        int contactID = contactCBox.getValue().getContactID();

        // Handle null pointer exception
        Users user = userIDCBox.getValue();
        if (user == null) {
            Alerts.getError(15);
            return;
        }
        int userID = userIDCBox.getValue().getUserID();

        // Handle null pointer exception
        LocalDate startDate = apptStartDatePicker.getValue();
        LocalTime startTime = apptStartTimeSpinner.getValue();
        if (startDate == null || startTime == null) {
            Alerts.getError(11);
            return;
        }
        LocalDateTime start = LocalDateTime.of(apptStartDatePicker.getValue(), apptStartTimeSpinner.getValue());

        // Handle null pointer exception
        LocalDate endDate = apptEndDatePicker.getValue();
        LocalTime endTime = apptEndTimeSpinner.getValue();
        if (endDate == null || endTime == null) {
            Alerts.getError(12);
            return;
        }
        LocalDateTime end = LocalDateTime.of(apptEndDatePicker.getValue(), apptEndTimeSpinner.getValue());

        // Handle blank/empty text boxes, and whether appointment overlaps or outside business hours
        if (title.isEmpty() || title.isBlank()) {
            Alerts.getError(7);
        } else if (description.isEmpty() || description.isBlank()) {
            Alerts.getError(8);
        } else if (location.isEmpty() || location.isBlank()) {
            Alerts.getError(9);
        } else if (type.isEmpty() || type.isBlank()) {
            Alerts.getError(10);
        } else if (Appointments.businessHours(start, end)) {
            Alerts.getError(16);
        } else if (Appointments.checkApptOverlap(customerID, start, end)) {
        } else {
            AppointmentsQuery.addAppointment(title, description, location, type, start, end, customerID, userID, contactID);
            Alerts.getInfo(1);
            Misc.jumpToPage(event, "AppointmentsView.fxml");
        }
    }

    /*
    private int generateID() {
        for (Appointments appointment : AppointmentsQuery.getAllAppointments()) {
            if (appointment.getApptID() == generatedID) {
                generatedID++;
            }
        }
        return generatedID;
    }
    /*
     */

    /**
     * Initialize controller and populate table view.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //generatedID = generateID();

        contactCBox.getItems().addAll(allContacts);
        customerIDCBox.getItems().addAll(allCustomers);
        userIDCBox.getItems().addAll(allUsers);
        apptStartTimeSpinner.setValueFactory(Misc.factoryStart);
        apptEndTimeSpinner.setValueFactory(Misc.factoryEnd);


    }
}
