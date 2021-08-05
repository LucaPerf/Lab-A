package com.lab.ui;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class UIDValidator extends ValidatorBase {

    //UID value is on 16 bit with range 0-65536 (2^16 values)
    private Integer MAX = 65535;

    public UIDValidator(String message) {
        super(message);
    }

    public UIDValidator() {
        super("Maximum value 65535");
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(false);

        try {
            Integer value = Integer.parseInt(textField.getText());
            if (value > MAX)
                hasErrors.set(true);
        } catch (NumberFormatException e) {
            hasErrors.set(true);
        }
    }
}
