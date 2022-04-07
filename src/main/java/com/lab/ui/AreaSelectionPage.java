package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller of the area selection page.
 * <p>Two buttons are shown to select which area to access to.<br>
 * The layout of this page is defined in "area_selection.fxml"
 */
public class AreaSelectionPage extends Page {
    @FXML
    private VBox root;
    @FXML
    private JFXButton centerArea;
    @FXML
    private JFXButton userArea;

    /**
     * @return {@inheritDoc}
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
        centerArea.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERACTIONS).reset());

        userArea.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.USERMAIN).reset());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
    }
}
