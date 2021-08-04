package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller of the user area main page. Layout is stored in "user_main.fxml".
 *
 * @author Ciceri Luigi
 */
public class UserMainPage extends Page {
    @FXML
    private Label username;
    @FXML
    private JFXButton logout;
    @FXML
    private HBox topbar;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton back;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;
    @FXML
    private JFXTextField searchbar;
    @FXML
    private JFXListView centers;

    private User currentUser;

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
    @Override
    protected void initialize() {
        logout.setVisible(false);
        username.setVisible(false);

        logout.setOnAction(actionEvent -> setLoggedOut());

        login.setOnAction(actionEvent -> PagesManager.openUserLogin());

        register.setOnAction(actionEvent -> PagesManager.openUserReg());

        back.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        currentUser = null;
        username.setText("");
    }

    /**
     * Sets the page to login mode. User information is stored ,logout and username label are enabled.
     *
     * @param user The user information
     */
    public void setLoggedIn(User user) {
        currentUser = user;

        username.setVisible(true);
        username.setText(currentUser.getUserID());
        logout.setVisible(true);

        register.setVisible(false);
        register.toBack();
        login.setVisible(false);
        login.toBack();
    }

    /**
     * Sets the page to logout mode. Login and register buttons are shown, page information is reset.
     */
    public void setLoggedOut() {
        reset();

        logout.setVisible(false);
        logout.toBack();
        username.setVisible(false);
        username.toBack();

        register.setVisible(true);
        login.setVisible(true);
    }
}
