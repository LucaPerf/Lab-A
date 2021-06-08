package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

/**
 * Controller of the area selection page: "Centri" or "Cittadini". Layout is stored in "area_selection.fxml".
 *
 * @author Ciceri Luigi
 */
public class AreaSelectionPage extends Page {
    @FXML
    private JFXButton centerArea;
    @FXML
    private JFXButton userArea;

    /**
     * {@inheritDoc}
     */
    @FXML
    protected void initialize() {
        centerArea.setOnAction(actionEvent -> PagesManager.openCenterActions());

        userArea.setOnAction(actionEvent -> System.out.println("Not implemented"));
    }
}
