package com.lab.ui;

import com.jfoenix.controls.JFXRippler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * This class represents a simple center selection interface.
 * <p>Its layout is store into "center_selection.fxml".<br>
 * This page only shows a {@link CenterSearchCommonPage} and a simple {@link com.jfoenix.controls.JFXToolbar}.
 * By clicking on an item on the list a {@link VaxRegPage} will be opened.
 */
public class CenterSelectionPage extends Page {
    @FXML
    private BorderPane root;
    @FXML
    private JFXRippler back;
    @FXML
    private CenterSearchCommonPage searchController;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        searchController.reset();
    }
}
