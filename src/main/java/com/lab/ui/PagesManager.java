package com.lab.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
    //Resource loaders
    private static FXMLLoader centerRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_registration.fxml"));
    private static FXMLLoader centerActionsLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_actions.fxml"));
    private static FXMLLoader areaSelectionLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/area_selection.fxml"));
    private static FXMLLoader userMainLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_main.fxml"));
    private static FXMLLoader userRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_registration.fxml"));
    private static FXMLLoader vaxRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/vax_registration.fxml"));
    private static FXMLLoader userLoginLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_login.fxml"));
    //Pages controllers
    private static Page centerRegPage;
    private static Page centerActionsPage;
    private static Page areaSelectionPage;
    private static Page userMainPage;
    private static Page userRegPage;
    private static Page vaxRegPage;
    private static Page userLoginPage;

    /**
     * Loads all UI pages from fxml, creates a new scene with an empty HBox Node and sets the stage scene
     *
     * @param stage The stage associated with this application
     */
    public static void initialize(Stage stage) {
        //Load all pages
        centerRegPage = loadPage(centerRegLoader);
        centerActionsPage = loadPage(centerActionsLoader);
        areaSelectionPage = loadPage(areaSelectionLoader);
        userMainPage = loadPage(userMainLoader);
        userRegPage = loadPage(userRegLoader);
        vaxRegPage = loadPage(vaxRegLoader);
        userLoginPage = loadPage(userLoginLoader);

        //Set scene to an empty HBox
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

    /**
     * Opens the user area main page
     */
    public static void openUserMain() {
        open(userMainPage);
    }

    /**
     * Opens the user registration page
     */
    public static void openUserReg() {
        open(userRegPage);
    }

    /**
     * Opens the vaccination registration page
     */
    public static void openVaxReg() {
        open(vaxRegPage);
    }

    /**
     * Opens the user login page
     */
    public static void openUserLogin() {
        open(userLoginPage);
    }

    private static Page loadPage(FXMLLoader loader) {
        try {
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            System.out.println("Page resource loading failed \n");
            e.printStackTrace();
            return null;
        }
    }
}
