package com.lab.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * This class is an abstract representation of an UI page. It contains its own root Node and child nodes. The corresponding .fxml file loaded on instantiation and specified in the constructor of each subclass.
 *
 * @author Ciceri Luigi
 */

public abstract class Page {
    protected String source;
    protected Parent root;

    /**
     * Creates a new Page object with the nodes defined in resourceName, sets up the page components and sets its root node.
     * @param resourceName the .fxml file to load the nodes from
     */

    public Page(String resourceName) {
        source = resourceName;
        try {
            root = FXMLLoader.load(getClass().getResource(source));
            getComponents();
        } catch (IOException e) {
            System.out.println("Error loading " + source + System.lineSeparator() + e);
        }
    }

    /**
     * Gets child nodes from root one
     */
    abstract protected void getComponents();

    /**
     * @return The root node of this page instance
     */
    public Parent getRoot() {
        return root;
    }
}
