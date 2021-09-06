package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.centrivaccinali.CentriVaccinali;
import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.PostalAddress;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Controller of the center registration page.
 * <p>Layout is stored in "center_registration.fxml".<br>
 * This page contains the required input fields to input data needed to register a center. A confirmation button is shown at the bottom of the screen.
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

    /**
     * This snackbar will be shown when a center with the input name already exists.
     * <p>A snackbar is a small notification at the bottom of the screen, which will be hidden autmatically after a certain amount of time.
     */
    JFXSnackbar centerAddedNotification;
    /**
     * Layout of {@link #centerAddedNotification}
     */
    JFXSnackbarLayout centerAddedNotificationLayout = new JFXSnackbarLayout("Esiste gi\u00E0 un centro con lo steso nome");

    /**
     * @return {@inheritDoc}
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
                boolean centerAdded;
                try {
                    centerAdded = CentriVaccinali.registraCentroVaccinale(centerFromUI());
                } catch (IOException e) {
                    e.printStackTrace();
                    PagesManager.openErrorPage(e);
                    return;
                }

                if (centerAdded) {
                    CenterActionsPage page = (CenterActionsPage) PagesManager.open(PagesManager.PageType.CENTERACTIONS);
                    page.showCenterAddedNotification();
                } else
                    centerAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(centerAddedNotificationLayout, NOTIFICATION_TIMEOUT));
            }
        });

        back.setOnMouseClicked(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERACTIONS));
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

    /**
     * @return A {@link PostalAddress} object constructed from data input into the page
     */
    private PostalAddress addressFromUI() {
        return new PostalAddress(
                address.getText(),
                city.getText(),
                province.getText(),
                Integer.parseInt(cap.getText()));
    }

    /**
     * @return A {@link Center} object constructed from data input into the page
     */
    private Center centerFromUI() {
        return new Center(
                name.getText(),
                addressFromUI(),
                type.getSelectionModel().getSelectedItem());
    }

    /**
     * Validates data inout into the page's fields.
     * <p>Lazy evaluation is not used to allow each field to be evaluated.
     * {@link com.jfoenix.validation.base.ValidatorBase} are used to check data correctness
     *
     * @return True if all data is valid
     */
    private boolean isDataValid() {
        return name.validate() & type.validate() & address.validate() & city.validate() & province.validate() & cap.validate();
    }
}
