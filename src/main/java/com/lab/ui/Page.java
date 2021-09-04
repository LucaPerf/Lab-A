package com.lab.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.time.LocalDate;

/**
 * Base class of a UI page controller.
 * <p>This class contains common variables that might be needed by subclasses such as input validators.<br>
 * Common methods to reset components are also included.<br>
 * <code>getRoot()</code>, <code>reset()</code> and <code>initialize()</code> are fundamental to this class and must be implemented by all subclasses.
 *
 * @author Ciceri Luigi
 */
abstract public class Page {
    /**
     * This validator checks if an input is empty
     */
    protected RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Campo obbligatorio");
    /**
     * This validator checks if a CAP is valid.
     * <p>The string must contain exactly 5 numeric characters.
     */
    protected RegexValidator capValidator = new RegexValidator("CAP non valido");
    /**
     * This validator checks if a "Codice Fisacle" is valid.
     * <p>The following pattern is checked:<br>
     * 6 letters -> 3 alphanumerics -> 2 numbers -> 4 alphanumerics -> 1 letter
     */
    protected RegexValidator ccfValidator = new RegexValidator("Codice fiscale non valido");
    /**
     * This validator checks if the unique vaccination id is valid.
     * <p>The id must contain exactly 16 numeric characters
     */
    protected RegexValidator uIDValidator = new RegexValidator("Sono richieste 16 cifre numeriche");
    /**
     * How long snackbars will be displayed before being hidden
     */
    protected static final javafx.util.Duration NOTIFICATION_TIMEOUT = Duration.seconds(4);

    /**
     * Common constructor called from all subclasses.
     * <p>It sets the regular expression patterns needed by validators.
     */
    public Page() {
        uIDValidator.setRegexPattern("^[0-9]{16}$");
        capValidator.setRegexPattern("^[0-9]{5}$");
        ccfValidator.setRegexPattern("^[A-Za-z]{6}[A-Za-z0-9]{3}[0-9]{2}[A-Za-z0-9]{4}[A-Za-z]{1}$");
    }

    /**
     * @return The root node of this page.
     * <p>The returned object will be the new root of the scene.
     */
    abstract public Parent getRoot();

    /**
     * This is called after the corresponding fxml file is loaded.
     * <p>Commonly used to bind methods to buttons and set up the UI.
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
    protected static <T> void resetField(JFXComboBox<T> field) {
        field.getSelectionModel().clearSelection();
        field.resetValidation();
    }

    /**
     * Resets a JFX node to its default state, clearing its validators
     *
     * @param field The node to be reset
     */
    protected static void resetField(JFXDatePicker field) {
        field.setValue(LocalDate.now());
        field.resetValidation();
    }
}
