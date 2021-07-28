package com.lab.ui;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.PostalAddress;
import com.lab.centrivaccinali.CentriVaccinali;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of the center registration page. Layout is stored in "center_registration.fxml".
 *
 * @author Ciceri Luigi
 */
public class CenterRegPage extends Page {
    @FXML
    private VBox root;
    @FXML
    private JFXButton register;
    @FXML
    private JFXButton back;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXComboBox<CenterType> type;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField province;
    @FXML
    private JFXTextField cap;
    private Center center;

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getRoot() {
        return root;
    }

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
        back.setOnAction(actionEvent -> PagesManager.openCenterActions());
    }

    private PostalAddress addressFromUI() {
        //Set cap to 0 if the input is an invalid number
        int capNumber = 0;
        try {
            capNumber = Integer.parseInt(cap.getText());
        } catch (NumberFormatException e) {
        }

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
                type.getSelectionModel().getSelectedItem());
    }
}
