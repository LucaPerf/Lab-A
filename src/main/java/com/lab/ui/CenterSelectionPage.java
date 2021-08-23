package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CenterSelectionPage extends Page {
    @FXML
    private BorderPane root;
    @FXML
    private JFXRippler back;
    @FXML
    private CenterSearchCommonPage searchController;

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    protected void initialize() {

        back.setOnMouseClicked(event ->
        {
            reset();
            PagesManager.open(PagesManager.PageType.CENTERACTIONS);
        });

        searchController.setOnListItemAction(event ->
        {
            VaxRegPage page = (VaxRegPage) PagesManager.open(PagesManager.PageType.VAXREGISTREATION);
            //Can't be null as when an item is clicked a center is selected
            page.setReferenceCenter(searchController.getSelectedCenter());
        });

        reset();
    }

    @Override
    public void reset() {
        searchController.reset();
    }
}
