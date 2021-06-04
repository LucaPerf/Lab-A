package com.lab.ui;

import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.PostalAddress;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.lab.centrivaccinali.CentriVaccinali;

/**
 * Controller of the center registration page. Layout is stored in "center_registration.fxml".
 *
 * @author Ciceri Luigi
 */
public class CenterRegPage extends Page {
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
            CentriVaccinali.registraCentroVaccinale(centerFromUI());
            PagesManager.openCenterActions();
        });

        back.requestFocus();
        back.setOnAction(actionEvent -> {
            PagesManager.openCenterActions();
        });
    }

    private PostalAddress addressFromUI() {
        //Set cap to 0 if the input is an invalid number
        int capNumber = 0;
        try {
            capNumber = Integer.parseInt(cap.getText());
        } catch (NumberFormatException e) { }

        return new PostalAddress(
                address.getText(),
                city.getText(),
                province.getText(),
                capNumber);
    }

    private Center centerFromUI() {
        return new Center(
                name.getText(),
                addressFromUI(),
                (CenterType) type.getSelectionModel().getSelectedItem());
    }
}
