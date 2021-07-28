package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of the area selection page: "Centri" or "Cittadini". Layout is stored in "area_selection.fxml".
 *
 * @author Ciceri Luigi
 */
public class AreaSelectionPage extends Page {
    @FXML
    private VBox root;
    @FXML
    private JFXButton centerArea;
    @FXML
    private JFXButton userArea;

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
        centerArea.setOnAction(actionEvent -> PagesManager.openCenterActions());

        userArea.setOnAction(actionEvent -> PagesManager.openUserMain());
    }
}
