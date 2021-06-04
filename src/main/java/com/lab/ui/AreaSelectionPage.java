package com.lab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller of the area selection page: "Centri" or "Cittadini". Layout is stored in "area_selection.fxml".
 * @author Ciceri Luigi
 */
public class AreaSelectionPage extends Page{
    @FXML
    private Button centerArea;
    @FXML
    private Button userArea;

    /**
     * {@inheritDoc}
     */
    @FXML
    protected void initialize() {
        centerArea.setOnAction(actionEvent -> PagesManager.openCenterActions());

        userArea.setOnAction(actionEvent -> System.out.println("Not implemented"));
    }
}
