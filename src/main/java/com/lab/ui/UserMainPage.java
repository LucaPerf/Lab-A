package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class UserMainPage extends Page {
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;
    @FXML
    private JFXTextField searchbar;
    @FXML
    private JFXListView centers;

    @Override
    protected void initialize() {
        login.setOnAction(actionEvent -> System.out.println("Not implemented"));

        register.setOnAction(actionEvent -> PagesManager.openUserReg());
    }
}
