package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lab.cittadini.Cittadini;
import com.lab.data.User;
import javafx.fxml.FXML;

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

    @Override
    protected void initialize() {
        register.setOnAction(actionEvent ->
        {
            Cittadini.registraCittadino(userFromUI());
            PagesManager.openUserMain();
        });

        cancel.setOnAction(actionEvent -> PagesManager.openUserMain());
    }

    private User userFromUI() {
        return new User(name.getText(), surname.getText(), ccf.getText(), email.getText(), username.getText(), password.getText(), Integer.parseInt(uid.getText()));
    }
}
