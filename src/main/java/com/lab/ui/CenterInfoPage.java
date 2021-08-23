package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.lab.data.Center;
import com.lab.data.PostalAddress;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class CenterInfoPage extends Page {
    @FXML
    private BorderPane root;
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

    private Center center;

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    protected void initialize() {
        report.setOnAction(event ->
        {
            EventReportPage page = (EventReportPage) PagesManager.open(PagesManager.PageType.EVENTREPORT);
            page.setCenter(center);
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
        setLoggedIn(false);
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

    public void setLoggedIn(boolean loggedIn) {
        root.setBottom(loggedIn ? report : null);
    }
}
