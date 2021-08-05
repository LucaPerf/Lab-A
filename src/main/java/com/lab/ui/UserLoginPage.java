package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of the user login page. Layout is stored in "user_login.fxml".
 *
 * @author Ciceri Luigi
 */
public class UserLoginPage extends Page {

    @FXML
    private VBox root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton register;
    @FXML
    private JFXButton cancel;

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
        username.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);

        register.setOnAction(actionEvent ->
        {
            if (isDataValid()) {
                User u = Cittadini.login(username.getText(), password.getText());
                if (u != null) {
                    PagesManager.openUserMain().setLoggedIn(u);
                    reset();
                }
            }
        });

        cancel.setOnAction(actionEvent ->
        {
            PagesManager.openUserMain();
            reset();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        resetField(username);
        resetField(password);
    }

    private boolean isDataValid() {
        return username.validate() & password.validate();
    }
}
