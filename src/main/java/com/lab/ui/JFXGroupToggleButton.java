package com.lab.ui;

import com.jfoenix.controls.JFXToggleNode;

/**
 * This class changes the selection behaviour of the {@link JFXToggleNode} by not allowing a {@link javafx.scene.control.ToggleGroup} to be left without a selected item.
 */
public class JFXGroupToggleButton extends JFXToggleNode {
    /**
     * Selects the button if not inside a {@link javafx.scene.control.ToggleGroup} or if the button is not selected yet.
     */
    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected())
            super.fire();
    }
}
