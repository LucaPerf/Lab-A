package com.lab.ui;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.PostalAddress;
import com.lab.centrivaccinali.CentriVaccinali;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Controller of the center registration page. Layout is stored in "center_registration.fxml".
 *
 * @author Ciceri Luigi
 */
public class CenterRegPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private JFXButton register;
    @FXML
    private JFXRippler back;
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

    JFXSnackbar centerAddedNotification;
    JFXSnackbarLayout centerAddedNotificationLayout = new JFXSnackbarLayout("Esiste gi\u00E0 un centro con lo steso nome");

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
        centerAddedNotification = new JFXSnackbar(root);
        //Set validators
        name.getValidators().add(requiredFieldValidator);
        type.getValidators().add(requiredFieldValidator);
        address.getValidators().add(requiredFieldValidator);
        city.getValidators().add(requiredFieldValidator);
        province.getValidators().add(requiredFieldValidator);
        cap.getValidators().add(capValidator);

        type.getItems().addAll(CenterType.values());

        register.setOnAction(actionEvent -> {
            if (isDataValid()) {
                try {
                    CentriVaccinali.registraCentroVaccinale(centerFromUI());
                } catch (IOException e) {
                    PagesManager.openErrorPage(e);
                    return;
                }
                PagesManager.open(PagesManager.PageType.CENTERACTIONS);
            }
        });

        back.setOnMouseClicked(actionEvent -> {
            reset();
            PagesManager.open(PagesManager.PageType.CENTERACTIONS);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        resetField(name);
        resetField(type);
        resetField(address);
        resetField(city);
        resetField(province);
        resetField(cap);
    }

    private PostalAddress addressFromUI() {
        return new PostalAddress(
                address.getText(),
                city.getText(),
                province.getText(),
                Integer.parseInt(cap.getText()));
    }

    private Center centerFromUI() {
        return new Center(
                name.getText(),
                addressFromUI(),
                type.getSelectionModel().getSelectedItem());
    }

    private boolean isDataValid() {
        return name.validate() & type.validate() & address.validate() & city.validate() & province.validate() & cap.validate();
    }
}
