package com.lab.ui;

import javafx.fxml.FXML;

/**
 * Base class of an UI page controller.
 *
 * @author Ciceri Luigi
 */
abstract public class Page {
    /**
     * Binds methods to buttons and sets up the UI. This is called after the corresponding fxml file is loaded.
     */
    @FXML
    abstract protected void initialize();
}
