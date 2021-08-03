package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of the user area main page. Layout is stored in "user_main.fxml".
 *
 * @author Ciceri Luigi
 */
public class UserMainPage extends Page {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton back;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;
    @FXML
    private JFXTextField searchbar;
    @FXML
    private JFXListView centers;

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
        login.setOnAction(actionEvent -> PagesManager.openUserLogin());

        register.setOnAction(actionEvent -> PagesManager.openUserReg());

        back.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
    }
}
