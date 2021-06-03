package com.lab.ui;

import com.lab.data.Center;
import com.lab.data.CenterType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller of the center registration page. Layout is stored in "center_registration.fxml".
 * @author Ciceri Luigi
 */
public class CenterRegPage extends Page{
    @FXML
    private Button register;
    @FXML
    private Button back;
    @FXML
    private TextField name;
    @FXML
    private ComboBox type;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField province;
    @FXML
    private TextField cap;
    private Center center;

    /**
     * {@inheritDoc}
     */
    @FXML
    protected void initialize() {
        //Set center type items and select "Ospedaliero"
        type.getItems().addAll(CenterType.values());
        type.getSelectionModel().select(0);

        register.setOnAction(actionEvent -> {
            registraCentroVaccinale();
            PagesManager.openCenterActions();
        });

        back.requestFocus();
        back.setOnAction(actionEvent -> {
            PagesManager.openCenterActions();
        });
    }

    private void registraCentroVaccinale() {
        System.out.println("Not implemented");
        /*
        PostalAddress address = new PostalAddress(
            address.getText(),
            city.getText(),
            province.getText(),
            Integer.parseInt(cap.getText()));
        center = new Center(nameField.getText(),address,(CenterType)centerTypeCombo.getSelectionModel().getSelectedItem());
        Centri.add(center);
        Centri.save();
         */
    }
}
