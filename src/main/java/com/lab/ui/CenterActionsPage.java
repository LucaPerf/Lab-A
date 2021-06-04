package com.lab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller of center actions page. Layout is stored in "center_actions.fxml".
 * @author CIceri Luigi
 */
public class CenterActionsPage extends Page{
    @FXML
    private Button addVax;
    @FXML
    private Button addCenter;
    @FXML
    private Button back;

    /**
     * {@inheritDoc}
     */
    @FXML
    protected void initialize() {
        addCenter.setOnAction(actionEvent -> PagesManager.openCenterReg());

        addVax.setOnAction(actionEvent -> System.out.println("Not implemented"));

        back.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }
}
