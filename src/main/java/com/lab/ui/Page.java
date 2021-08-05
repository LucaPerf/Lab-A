package com.lab.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * Base class of an UI page controller.
 *
 * @author Ciceri Luigi
 */
abstract public class Page {
    /**
     * This validator checks if the field is empty
     */
    protected RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Campo obbligatorio");
    /**
     * This validator checks if the CAP is valid (string made up of exactly 5 numbers)
     */
    protected RegexValidator capValidator = new RegexValidator("CAP non valido");
    /**
     * This validators checks if the ccf is valid (6 letters -> 3 alphanumerics -> 2 numbers -> 4 alphanumerics -> 1 letter)
     */
    protected RegexValidator ccfValidator = new RegexValidator("Codice fiscale non valido");
    /**
     * This validator checks if the unique vaccination id is valid (value is less than 2^16)
     */
    protected UIDValidator uIDValidator = new UIDValidator("Valore massimo 65535");

    /**
     * Common constructor called from all subclasses
     */
    public Page() {
        capValidator.setRegexPattern("^[0-9]{5}$");
        ccfValidator.setRegexPattern("^[A-Z]{6}[A-Za-z0-9]{3}[0-9]{2}[A-Za-z0-9]{4}[A-Za-z]{1}$");
    }

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

    /**
     * Resets a JFX node to its default state, clearing its validators
     *
     * @param field The node to be reset
     */
    protected static void resetField(JFXTextField field) {
        field.clear();
        field.resetValidation();
    }

    /**
     * Resets a JFX node to its default state, clearing its validators
     *
     * @param field The node to be reset
     */
    protected static void resetField(JFXPasswordField field) {
        field.clear();
        field.resetValidation();
    }

    /**
     * Resets a JFX node to its default state, clearing its validators
     *
     * @param field The node to be reset
     */
    protected static void resetField(JFXComboBox field) {
        field.getSelectionModel().clearSelection();
        field.resetValidation();
    }
}
