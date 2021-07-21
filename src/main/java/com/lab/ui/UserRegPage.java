package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;

/**
 * Controller of the user registration page. Layout is stored in "user_registration.fxml".
 *
 * @author CIceri Luigi
 */
public class UserRegPage extends Page {
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField surname;
    @FXML
    private JFXTextField ccf;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField uid;
    @FXML
    private JFXButton register;
    @FXML
    private JFXButton cancel;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        register.setOnAction(actionEvent ->
        {
            Cittadini.registraCittadino(userFromUI());
            PagesManager.openUserMain();
        });

        cancel.setOnAction(actionEvent -> PagesManager.openUserMain());
    }

    //Creates a new User object from the UI fields
    private User userFromUI() {
        return new User(name.getText(), surname.getText(), ccf.getText(), email.getText(), username.getText(), password.getText(), Integer.parseInt(uid.getText()));
    }
}
