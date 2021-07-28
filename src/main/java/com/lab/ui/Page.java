package com.lab.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * Base class of an UI page controller.
 *
 * @author Ciceri Luigi
 */
abstract public class Page {
    /**
     * @return The root node of this layout
     */
    abstract public Parent getRoot();

    /**
     * Binds methods to buttons and sets up the UI. This is called after the corresponding fxml file is loaded.
     */
    @FXML
    abstract protected void initialize();

    /**
     * Resets this page to its intial state
     */
    abstract public void reset();
}
