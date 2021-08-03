package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lab.cittadini.Cittadini;
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
        register.setOnAction(actionEvent ->
        {
            if (Cittadini.login(username.getText(), password.getText())) ;
            {
                PagesManager.openUserMain();
                reset();
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
        username.clear();
        password.clear();
    }
}
