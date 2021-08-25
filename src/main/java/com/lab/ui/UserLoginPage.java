package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * Controller of the user login page. Layout is stored in "user_login.fxml".
 *
 * @author Ciceri Luigi
 */
public class UserLoginPage extends Page {

    @FXML
    private StackPane root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton register;
    @FXML
    private JFXRippler back;

    private JFXSnackbar loginFailedNotification;
    private JFXSnackbarLayout loginFailedNotificationLayout = new JFXSnackbarLayout("Nome utente o password sbagliati");

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
        loginFailedNotification = new JFXSnackbar(root);

        username.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);

        register.setOnAction(actionEvent ->
        {
            if (isDataValid()) {
                User u = Cittadini.login(username.getText(), password.getText());
                if (u != null) {
                    ((UserMainPage) PagesManager.open(PagesManager.PageType.USERMAIN)).setLoggedIn(u);
                    reset();
                } else
                    loginFailedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(loginFailedNotificationLayout));
            }
        });

        back.setOnMouseClicked(actionEvent ->
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
