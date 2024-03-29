package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Class for querying appointments database.
 *
 * @author Wilson Ramirez
 */
public class AppointmentsQuery {
    /**
     * SQL query for getting all appointments from the database.
     *
     * @return all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement getAppointments = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = getAppointments.executeQuery();

            while(rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptDescription = rs.getString("Description");
                String apptLocation = rs.getString("Location");
                String apptType = rs.getString("Type");
                LocalDateTime apptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime apptEnd = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, customerID, userID, contactID);
                allAppointments.add(appointment);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAppointments;

    }

    /**
     * Sql query to add new appointments to the database. Appointment ID is auto-generated.
     * @param apptTitle Appointment title
     * @param apptDescription Appointment description
     * @param apptLocation Appointment location
     * @param apptType Appointment type
     * @param apptStart Appointment start date/time
     * @param apptEnd Appointment end date/time
     * @param apptCustomerID Associated customer ID
     * @param apptUserID Associated user ID
     * @param apptContactID associated contact ID
     * @throws SQLException for unhandled SQL exception
     */
    public static void addAppointment(String apptTitle, String apptDescription, String apptLocation, String apptType,
                                      LocalDateTime apptStart, LocalDateTime apptEnd, int apptCustomerID,
                                      int apptUserID, int apptContactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addAppt = JDBC.getConnection().prepareStatement(sql);

        addAppt.setString(1, apptTitle);
        addAppt.setString(2, apptDescription);
        addAppt.setString(3, apptLocation);
        addAppt.setString(4, apptType);
        addAppt.setTimestamp(5, Timestamp.valueOf(apptStart));
        addAppt.setTimestamp(6, Timestamp.valueOf(apptEnd));
        addAppt.setInt(7, apptCustomerID);
        addAppt.setInt(8, apptUserID);
        addAppt.setInt(9, apptContactID);
        addAppt.executeUpdate();
    }

    /**
     * SQL query for updating existing appointments in the database.
     * @param apptID Appointment ID
     * @param apptTitle Appointment title
     * @param apptDescription Appointment description
     * @param apptLocation Appointment location
     * @param apptType Appointment type
     * @param apptStart Appointment start date/time
     * @param apptEnd Appointment end date/time
     * @param apptCustomerID Associated customer ID
     * @param apptUserID Associated user ID
     * @param apptContactID associated contact ID
     */
    public static void updateAppointment(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType,
                                         LocalDateTime apptStart, LocalDateTime apptEnd, int apptCustomerID,
                                         int apptUserID, int apptContactID) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppt = JDBC.getConnection().prepareStatement(sql);

            updateAppt.setString(1, apptTitle);
            updateAppt.setString(2, apptDescription);
            updateAppt.setString(3, apptLocation);
            updateAppt.setString(4, apptType);
            updateAppt.setTimestamp(5, Timestamp.valueOf(apptStart));
            updateAppt.setTimestamp(6, Timestamp.valueOf(apptEnd));
            updateAppt.setInt(7, apptCustomerID);
            updateAppt.setInt(8, apptUserID);
            updateAppt.setInt(9, apptContactID);
            updateAppt.setInt(10, apptID);
            updateAppt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SQL query for deleting an existing appointment from the database.
     * @param apptID Appointment ID
     */
    public static void deleteAppointment(int apptID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteAppt = JDBC.getConnection().prepareStatement(sql);

            deleteAppt.setInt(1, apptID);
            deleteAppt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SQL query to get all appointments for specific user.
     *
     * @param requestedUserID Requested user's ID number.
     * @return All appointments for user.
     */
    public static ObservableList<Appointment> getUserAppointments(int requestedUserID) {
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = '" + requestedUserID + "'";
            PreparedStatement getUserAppts = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = getUserAppts.executeQuery();
            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptDescription = rs.getString("Description");
                String apptLocation = rs.getString("Location");
                String apptType = rs.getString("Type");
                LocalDateTime apptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime apptEnd = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, customerID, userID, contactID);
                userAppointments.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAppointments;
    }

    /**
     * SQL query to gather all appointments and sort them by appointment type.
     * @return All appointment types and a count of how many of each.
     */
    public static ObservableList<Appointment> getAppointmentType() {
        ObservableList<Appointment> appointmentListType = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, Count(*) AS NUM FROM appointments GROUP BY Type";
            PreparedStatement getApptType = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = getApptType.executeQuery();
            while (rs.next()) {
                String apptType = rs.getString("Type");
                int apptTypeTotal = rs.getInt("NUM");
                Appointment results = new Appointment(apptType, apptTypeTotal);
                appointmentListType.add(results);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return appointmentListType;
    }

    /**
     * SQL query to gather all appointments and sort them by month.
     * @return All months with appointments in them and a count of how many of each.
     */
    public static ObservableList<Appointment> getAppointmentMonth() {
        ObservableList<Appointment> appointmentListType = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS NUM FROM appointments GROUP BY Month";
            PreparedStatement getApptMonth = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = getApptMonth.executeQuery();
            while (rs.next()) {
                String apptMonth = rs.getString("Month");
                int apptMonthTotal = rs.getInt("NUM");
                Appointment results = new Appointment(apptMonth, apptMonthTotal);
                appointmentListType.add(results);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return appointmentListType;
    }
}
