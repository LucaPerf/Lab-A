package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

/**
 * Controller of center actions page. Layout is stored in "center_actions.fxml".
 *
 * @author CIceri Luigi
 */
public class CenterActionsPage extends Page {
    @FXML
    private JFXButton addVax;
    @FXML
    private JFXButton addCenter;
    @FXML
    private JFXButton back;

    /**
     * {@inheritDoc}
     */
    @FXML
    protected void initialize() {
        addCenter.setOnAction(actionEvent -> PagesManager.openCenterReg());

        addVax.setOnAction(actionEvent -> PagesManager.openVaxReg());

        back.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }
}
