package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * Controller of center actions page.
 * <p>Layout is stored in "center_actions.fxml".<br>
 * This page has two buttons to allow the user to choose which action to perform.
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
    /**
     * This snackbar will be shown when a center has been registered successfully.
     * <p>A snackbar is a small notification at the bottom of the screen, which will be hidden autmatically after a certain amount of time.
     */
    private JFXSnackbar centerAddedNotification;
    /**
     * Layout of {@link #centerAddedNotification}
     */
    private JFXSnackbarLayout centerAddedNotificationLayout = new JFXSnackbarLayout("Centro registrato con suceesso");

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

        addCenter.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERREGISTRATION).reset());

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
     * This will show a notification informing the user that the center has been added successfully
     */
    public void showCenterAddedNotification() {
        centerAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(centerAddedNotificationLayout, NOTIFICATION_TIMEOUT));
    }
}
