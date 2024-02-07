package model;

import com.wrc195.wrc195task.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Model for Appointments.
 *
 * @author Wilson Ramirez
 */
public class Appointments {
    static Stage stage;
    static Parent scene;
    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime apptStart;
    private LocalDateTime apptEnd;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for Appointments in database.
     * @param apptID Appointment ID.
     * @param apptTitle Appointment title.
     * @param apptDescription Appointment description.
     * @param apptLocation Appointment location.
     * @param apptType Appointment type.
     * @param apptStart Appointment start date/time.
     * @param apptEnd Appointment end date/time.
     * @param customerID Associated customer ID.
     * @param userID Associated user ID.
     * @param contactID Associated contact ID.
     */
    public Appointments(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, int userID, int contactID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     *
     * @return appointment ID.
     */
    public int getApptID() {
        return apptID;
    }



    /**
     *
     * @return appointment title.
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     *
     * @return appointment description.
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     *
     * @return appointment location.
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     *
     * @return appointment type.
     */
    public String getApptType() {
        return apptType;
    }

    /**
     *
     * @return appointment start date/time
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    /**
     *
     * @return appointment end date/time
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    /**
     *
     * @return associated customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return associated user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return associated contact ID.
     */
    public int getContactID() {
        return contactID;
    }

    public static LocalTime localStart() {
        LocalTime openingBusinessTime = LocalTime.of(8,0);
        ZoneId easternTime = ZoneId.of("America/New_York");
        ZoneId localTime = ZoneId.systemDefault();

        LocalDateTime businessEasternTime = LocalDateTime.of(LocalDate.now(), openingBusinessTime);
        LocalDateTime businessLocalTime = businessEasternTime.atZone(easternTime).withZoneSameInstant(localTime).toLocalDateTime();

        LocalTime businessStartLocalTime = businessLocalTime.toLocalTime();

        return businessStartLocalTime;

    }

    public static LocalTime localEnd() {
        LocalTime closingBusinessTime = LocalTime.of(22,0);
        ZoneId easternTime = ZoneId.of("America/New_York");
        ZoneId localTime = ZoneId.systemDefault();

        LocalDateTime businessEasternTime = LocalDateTime.of(LocalDate.now(), closingBusinessTime);
        LocalDateTime businessLocalTime = businessEasternTime.atZone(easternTime).withZoneSameInstant(localTime).toLocalDateTime();

        LocalTime businessEndLocalTime = businessLocalTime.toLocalTime();

        return businessEndLocalTime;

    }

    public static boolean businessHours(LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        LocalDateTime appStartEST = appointmentStart.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime appEndEST = appointmentEnd.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();

        LocalDateTime businessStartEST = appStartEST.withHour(8).withMinute(0);
        LocalDateTime businessEndEST = appEndEST.withHour(22).withMinute(0);

        if (appStartEST.isBefore(businessStartEST) || appEndEST.isAfter(businessEndEST)) {
            return true;
        } else {
            return false;
        }
    }
}
