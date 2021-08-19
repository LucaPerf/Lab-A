package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Controller of the user login page. Layout is stored in "user_login.fxml".
 *
 * @author Ciceri Luigi
 */
public class UserLoginPage extends Page {

    @FXML
    private BorderPane root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton register;
    @FXML
    private JFXButton back;

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
                    ((UserMainPage) PagesManager.open(PagesManager.PageType.USERMAIN)).setLoggedIn(u);
                    reset();
                }
            }
        });

        back.setOnAction(actionEvent ->
        {
            PagesManager.open(PagesManager.PageType.USERMAIN);
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
