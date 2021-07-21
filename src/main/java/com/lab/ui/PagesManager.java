package com.lab.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class manages navigation between pages using static methods. Each page has its own instance, therefore the page state is retained when replaced.
 *
 * @author Ciceri Luigi
 */

public class PagesManager {
    private static Scene scene;
    /**
     * The bounds of the primary screen, in pixels
     */
    public static Rectangle2D bounds = Screen.getPrimary().getBounds();
    private static Parent centerRegPage;
    private static Parent centerActionsPage;
    private static Parent areaSelectionPage;
    private static Parent userMainPage;
    private static Parent userRegPage;

    /**
     * Loads all UI pages from fxml, creates a new scene with an empty HBox Node and sets the stage scene
     *
     * @param stage The stage associated with this application
     */
    public static void initialize(Stage stage) {
        //Load all pages from fxml
        try {
            centerRegPage = FXMLLoader.load(PagesManager.class.getResource("/fxml/center_registration.fxml"));
            centerActionsPage = FXMLLoader.load(PagesManager.class.getResource("/fxml/center_actions.fxml"));
            areaSelectionPage = FXMLLoader.load(PagesManager.class.getResource("/fxml/area_selection.fxml"));
            userMainPage = FXMLLoader.load(PagesManager.class.getResource("/fxml/user_main.fxml"));
            userRegPage = FXMLLoader.load(PagesManager.class.getResource("/fxml/user_registration.fxml"));
        } catch (IOException e) {
            System.out.println(e);
        }

        //Set scene to an empty HBox
        scene = new Scene(new HBox(), bounds.getWidth() * 0.5, bounds.getHeight() * 0.5);
        stage.setScene(scene);
    }

    private static void open(Parent node) {
        scene.setRoot(node);
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

    public static void openUserMain() { open(userMainPage); }

    public static void openUserReg() { open(userRegPage); }
}
