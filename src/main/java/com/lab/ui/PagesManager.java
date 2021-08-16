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
    public enum PageType {
        CENTERREGISTRATION,
        CENTERACTIONS,
        AREASELECTION,
        USERMAIN,
        USERREGISTRATION,
        VAXREGISTREATION,
        USERLOGIN,
        CENTERSELECTION
    }

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
    private static FXMLLoader centerSelectionLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_selection.fxml"));
    //Pages controllers
    private static Page centerRegPage;
    private static Page centerActionsPage;
    private static Page areaSelectionPage;
    private static Page userMainPage;
    private static Page userRegPage;
    private static Page vaxRegPage;
    private static Page userLoginPage;
    private static Page centerSelectionPage;

    /**
     * Initializes the pages manager and loads all UI pages from fxml.
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
        centerSelectionPage = loadPage(centerSelectionLoader);
    }

    /**
     * Opens a new page of type {@link PageType}. Pages are reused so data from previous interactions will be present if not reset.
     *
     * @param type The type of page to open
     * @return The opened page or null if no page of <code>type</code> exists
     * @throws NullPointerException If <code>type</code> is null
     */
    public static Page open(PageType type) {

        if (type == null)
            throw new NullPointerException("Page type cannot be null");

        switch (type) {
            case USERMAIN:
                return open(userMainPage);
            case CENTERREGISTRATION:
                return open(centerRegPage);
            case CENTERACTIONS:
                return open(centerActionsPage);
            case AREASELECTION:
                return open(areaSelectionPage);
            case USERREGISTRATION:
                return open(userRegPage);
            case VAXREGISTREATION:
                return open(vaxRegPage);
            case USERLOGIN:
                return open(userLoginPage);
            case CENTERSELECTION:
                return open(centerSelectionPage);
            default:
                return null;
        }
    }

    private static Page open(Page page) {
        scene.setRoot(page.getRoot());
        return page;
    }

    /**
     * Opens a new empty scene of size 0.5 * {@link #bounds} . The scene contains an empty HBox.
     *
     * @param stage The stage to add the scene to
     */
    public static void openEmptyScene(Stage stage) {
        scene = new Scene(new HBox(), bounds.getWidth() * 0.5, bounds.getHeight() * 0.5);
        stage.setScene(scene);
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
