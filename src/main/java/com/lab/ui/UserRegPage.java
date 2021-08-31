package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Controller of the user registration page. Layout is stored in "user_registration.fxml".
 *
 * @author CIceri Luigi
 */
public class UserRegPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
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
    private JFXRippler back;

    private JFXSnackbar userRegisteredNotification;
    private JFXSnackbarLayout userRegisteredNotificationLayout = new JFXSnackbarLayout("Un utente con lo stesso nome esiste gi\u00E0");

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
        userRegisteredNotification = new JFXSnackbar(root);
        //Add validators
        username.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);
        name.getValidators().add(requiredFieldValidator);
        surname.getValidators().add(requiredFieldValidator);
        ccf.getValidators().add(ccfValidator);
        email.getValidators().add(requiredFieldValidator);
        uid.getValidators().add(uIDValidator);
        uid.getValidators().add(requiredFieldValidator);

        register.setOnAction(actionEvent ->
        {
            if (isDataValid()) {
                boolean registered;
                try {
                    registered = Cittadini.registraCittadino(userFromUI());
                } catch (IOException e) {
                    e.printStackTrace();
                    ErrorPage page = (ErrorPage) PagesManager.open(PagesManager.PageType.ERRORPAGE);
                    page.setError(e);
                    return;
                }
                if (registered) {
                    reset();
                    ((UserMainPage) PagesManager.open(PagesManager.PageType.USERMAIN)).showUserRegisteredNotification();
                } else
                    userRegisteredNotification.fireEvent(new JFXSnackbar.SnackbarEvent(userRegisteredNotificationLayout, NOTIFICATION_TIMEOUT));
            }
        });

        back.setOnMouseClicked(actionEvent ->
        {
            reset();
            PagesManager.open(PagesManager.PageType.USERMAIN);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        resetField(username);
        resetField(password);
        resetField(name);
        resetField(surname);
        resetField(email);
        resetField(uid);
        resetField(ccf);
    }

    //Creates a new User object from the UI fields
    private User userFromUI() {
        return new User(name.getText(), surname.getText(), ccf.getText(), email.getText(), username.getText(), password.getText(), Long.parseLong(uid.getText()));
    }

    private boolean isDataValid() {
        return username.validate() &
                password.validate() &
                name.validate() &
                surname.validate() &
                ccf.validate() &
                email.validate() &
                uid.validate();
    }
}
