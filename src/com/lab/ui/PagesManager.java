package com.lab.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class manages navigation between pages using static methods. Each page has its own instance, therefore the page state is retained when replaced.
 * @author Ciceri Luigi
 */

public class PagesManager {
    private static Scene scene;
    /**
     * The bounds of the primary screen, in pixels
     */
    public static Rectangle2D bounds = Screen.getPrimary().getBounds();
    private static Page centerRegPage = new CenterRegPage();
    private static Page centerActionsPage = new CenterActionsPage();
    private static Page areaSelectionPage = new AreaSelectionPage();

    /**
     * Sets the application main stage and creates a new scene with an empty HBox Node
     * @param stage The stage associated with this application
     */
    public static void setStage(Stage stage) {
        scene = new Scene(new HBox(), bounds.getWidth() * 0.5, bounds.getHeight() * 0.5);
        stage.setScene(scene);
    }

    private static void open(Page page) {
        scene.setRoot(page.getRoot());
    }

    /**
     * Open the center registration page
     */
    public static void openCenterReg() {
        open(centerRegPage);
    }

    /**
     * Opens the area selection page
     */
    public static void openAreaSelection() {
        open(areaSelectionPage);
    }

    /**
     * Opens the center actions page
     */
    public static void openCenterActions() {
        open(centerActionsPage);
    }
}
