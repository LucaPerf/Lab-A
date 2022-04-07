package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * Controller of the user login page.
 * <p>Layout is stored in "user_login.fxml".
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

    /**
     * This snackbar will be shown when the login has failed.
     * <p>This could be due to the fact that the user does not exist, or the password or username are wrong.
     * A snackbar is a small notification at the bottom of the screen, which will be hidden automatically after a certain amount of time.
     */
    private JFXSnackbar loginFailedNotification;
    /**
     * Layout of {@link #loginFailedNotification}
     */
    private JFXSnackbarLayout loginFailedNotificationLayout = new JFXSnackbarLayout("Nome utente o password sbagliati");

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
                } else
                    loginFailedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(loginFailedNotificationLayout));
            }
        });

        back.setOnMouseClicked(actionEvent -> PagesManager.open(PagesManager.PageType.USERMAIN));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        resetField(username);
        resetField(password);
    }

    /**
     * Validates data inout into the page's fields.
     * <p>Lazy evaluation is not used to allow each field to be evaluated.
     * {@link com.jfoenix.validation.base.ValidatorBase} are used to check data correctness
     *
     * @return True if all data is valid
     */
    private boolean isDataValid() {
        return username.validate() & password.validate();
    }
}
