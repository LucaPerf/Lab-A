package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * Controller of center actions page. Layout is stored in "center_actions.fxml".
 *
 * @author CIceri Luigi
 */
public class CenterActionsPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private JFXButton addVax;
    @FXML
    private JFXButton addCenter;
    @FXML
    private JFXRippler back;

    private JFXSnackbar centerAddedNotification;
    private JFXSnackbarLayout centerAddedNotificationLayout = new JFXSnackbarLayout("Centro registrato con suceesso");

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

        addCenter.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERREGISTRATION));

        addVax.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERSELECTION).reset());

        back.setOnMouseClicked(actionEvent -> PagesManager.open(PagesManager.PageType.AREASELECTION));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
    }

    /**
     * This will show a notification saying that the center was added successfully
     */
    public void showCenterAddedNotification() {
        centerAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(centerAddedNotificationLayout, NOTIFICATION_TIMEOUT));
    }
}
