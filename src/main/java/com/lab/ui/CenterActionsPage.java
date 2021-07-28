package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of center actions page. Layout is stored in "center_actions.fxml".
 *
 * @author CIceri Luigi
 */
public class CenterActionsPage extends Page {
    @FXML
    private VBox root;
    @FXML
    private JFXButton addVax;
    @FXML
    private JFXButton addCenter;
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
    @FXML
    protected void initialize() {
        addCenter.setOnAction(actionEvent -> PagesManager.openCenterReg());

        addVax.setOnAction(actionEvent -> PagesManager.openVaxReg());

        back.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() { }
}
