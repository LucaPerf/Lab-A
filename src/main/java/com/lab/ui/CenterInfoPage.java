package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.Center;
import com.lab.data.PostalAddress;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class CenterInfoPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXRippler back;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField type;
    @FXML
    private JFXTextField street;
    @FXML
    private JFXTextField comune;
    @FXML
    private JFXTextField province;
    @FXML
    private JFXTextField cap;
    @FXML
    private JFXButton report;

    private JFXSnackbar eventAddedNotification;
    private JFXSnackbarLayout eventAddedNotificationLayout = new JFXSnackbarLayout("Evento segnalato con successo");
    private Center center;
    private User user;

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    protected void initialize() {
        eventAddedNotification = new JFXSnackbar(root);

        report.setOnAction(event ->
        {
            EventReportPage page = (EventReportPage) PagesManager.open(PagesManager.PageType.EVENTREPORT);
            page.setCenter(center);
            page.setUser(user);
        });

        back.setOnMouseClicked(event ->
        {
            reset();
            PagesManager.open(PagesManager.PageType.USERMAIN);
        });
    }

    @Override
    public void reset() {
        center = null;
        setUser(null);
    }

    public void setCenter(Center center) {
        this.center = center;

        name.setText(center.getName());
        type.setText(center.getType().toString());
        PostalAddress address = center.getAddress();
        street.setText(address.getStreet());
        comune.setText(address.getDistrict());
        province.setText(address.getProvince());
        cap.setText(address.getCap().toString());
    }

    public void setUser(User user) {
        if (user != null) {
            borderPane.setBottom(report);
            this.user = user;
        } else
            borderPane.setBottom(null);
    }

    /**
     * Shows the event added notification {@link JFXSnackbar}
     */
    public void showEventAddedNotification() {
        eventAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(eventAddedNotificationLayout, SNACKBARDURATION));
    }
}
