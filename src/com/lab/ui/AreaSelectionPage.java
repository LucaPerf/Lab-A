package com.lab.ui;

import javafx.scene.control.Button;

/**
 * This class represents the area selection page: "Centri" or "Cittadini". Layout is stored in "area_selection.fxml".
 * @author Ciceri Luigi
 */
public class AreaSelectionPage extends Page {
    private Button centriButton;
    private Button cittadiniButton;

    /**
     * Creates a new instance of this class with nodes from area_selection.fxml
     */
    public AreaSelectionPage() {
        super("area_selection.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void getComponents() {
        centriButton = (Button) root.lookup("#centerButton");
        centriButton.setOnAction(actionEvent -> PagesManager.openCenterActions());

        cittadiniButton = (Button) root.lookup("#citButton");
        cittadiniButton.setOnAction(actionEvent -> System.out.println("Not implemented"));
    }
}
