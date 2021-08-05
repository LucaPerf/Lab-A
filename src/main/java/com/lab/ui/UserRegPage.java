package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of the user registration page. Layout is stored in "user_registration.fxml".
 *
 * @author CIceri Luigi
 */
public class UserRegPage extends Page {
    @FXML
    private VBox root;
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
        //Add validators
        username.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);
        name.getValidators().add(requiredFieldValidator);
        surname.getValidators().add(requiredFieldValidator);
        ccf.getValidators().add(ccfValidator);
        email.getValidators().add(requiredFieldValidator);
        uid.getValidators().add(requiredFieldValidator);

        register.setOnAction(actionEvent ->
        {
            if (isDataValid()) {
                Cittadini.registraCittadino(userFromUI());
                reset();
                PagesManager.openUserMain();
            }
        });

        cancel.setOnAction(actionEvent ->
        {
            reset();
            PagesManager.openUserMain();
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
        return new User(name.getText(), surname.getText(), ccf.getText(), email.getText(), username.getText(), password.getText(), Integer.parseInt(uid.getText()));
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
