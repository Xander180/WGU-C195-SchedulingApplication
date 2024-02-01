package com.wrc195.wrc195task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AppointmentModifyController {
    @FXML
    private TextArea apptDescriptionTxt;

    @FXML
    private DatePicker apptEndDatePicker;

    @FXML
    private Spinner<?> apptEndTimeSpinner;

    @FXML
    private TextField apptIDTxt;

    @FXML
    private TextField apptLocationText;

    @FXML
    private DatePicker apptStartDatePicker;

    @FXML
    private Spinner<?> apptStartTimeSpinner;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ComboBox<String> contactCBox;

    @FXML
    private ChoiceBox<?> customerIDCBox;

    @FXML
    private ChoiceBox<?> userIDCBox;

    @FXML
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionSaveAppt(ActionEvent event) {

    }

}
