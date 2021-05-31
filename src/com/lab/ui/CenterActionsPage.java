package com.lab.ui;

import javafx.scene.control.Button;

/**
 * This class represents a page with center actions. Layout is stored in "center_actions.fxml".
 * @author CIceri Luigi
 */
public class CenterActionsPage extends Page {
    private Button addVax;
    private Button addCenter;
    private Button backButton;

    /**
     * Creates a new instance of this class with nodes from center_actions.fxml
     */
    public CenterActionsPage() {
        super("center_actions.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void getComponents() {
        addCenter = (Button) root.lookup("#addCenterButton");
        addCenter.setOnAction(actionEvent -> PagesManager.openCenterReg());

        addVax = (Button) root.lookup("#addVaxButton");
        addVax.setOnAction(actionEvent -> System.out.println("Not implemented"));

        backButton = (Button) root.lookup("#backButton");
        backButton.setOnAction(actionEvent -> PagesManager.openAreaSelection());
    }
}
