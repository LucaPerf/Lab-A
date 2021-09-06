package com.lab.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * This class manages navigation between pages.
 * <p>Pages are re-usable to avoid resources consumption and loading times improving UI fluency.
 * Each page has its own instance, therefore the page state is retained when replaced with another one.
 * This class contains {@link FXMLLoader} and controller variables for each page (except {@link ErrorPage}.
 * They follow a common naming convention therefore they are not documented:
 * <code>pageNameLoader</code> for loaders and <code>pageNamePage</code> for controllers.<br>
 * A factory-like method is provided to open a specific page. As pages a re-usable, they must be reset manually by invoking {@link Page#reset()}.
 * A nested enumerator provides means to select the page to open.
 *
 * @author Ciceri Luigi
 */

public class PagesManager {
    /**
     * Private constructor to prevent class instantiation.
     */
    private PagesManager() {
    }

    /**
     * Represents the type of page.
     * <p>This is used to choose which page to open.
     */
    public enum PageType {
        CENTERREGISTRATION,
        CENTERACTIONS,
        AREASELECTION,
        USERMAIN,
        USERREGISTRATION,
        VAXREGISTREATION,
        USERLOGIN,
        CENTERSELECTION,
        CENTERINFO,
        EVENTREPORT,
        ERRORPAGE
    }

    /**
     * The main scene
     */
    private static Scene scene;
    //Resource loaders
    private static FXMLLoader centerRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_registration.fxml"));
    private static FXMLLoader centerActionsLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_actions.fxml"));
    private static FXMLLoader areaSelectionLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/area_selection.fxml"));
    private static FXMLLoader userMainLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_main.fxml"));
    private static FXMLLoader userRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_registration.fxml"));
    private static FXMLLoader vaxRegLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/vax_registration.fxml"));
    private static FXMLLoader userLoginLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/user_login.fxml"));
    private static FXMLLoader centerSelectionLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_selection.fxml"));
    private static FXMLLoader centerInfoLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/center_info.fxml"));
    private static FXMLLoader eventReportLoader = new FXMLLoader(PagesManager.class.getResource("/fxml/event_report.fxml"));
    //Pages controllers
    private static Page centerRegPage;
    private static Page centerActionsPage;
    private static Page areaSelectionPage;
    private static Page userMainPage;
    private static Page userRegPage;
    private static Page vaxRegPage;
    private static Page userLoginPage;
    private static Page centerSelectionPage;
    private static Page centerInfoPage;
    private static Page eventReportPage;
    private static Page errorPage = new ErrorPage();

    /**
     * Loads the layout of all pages.
     * <p>The error page is loaded first so that it can be used if any other page fails to load.
     *
     * @param stage The stage associated with this application
     * @throws IOException If an error occurs during loading
     */
    public static void initialize(Stage stage) throws IOException {
        //Load all pages
        errorPage.initialize();
        centerRegPage = loadPage(centerRegLoader);
        centerActionsPage = loadPage(centerActionsLoader);
        areaSelectionPage = loadPage(areaSelectionLoader);
        userMainPage = loadPage(userMainLoader);
        userRegPage = loadPage(userRegLoader);
        vaxRegPage = loadPage(vaxRegLoader);
        userLoginPage = loadPage(userLoginLoader);
        centerSelectionPage = loadPage(centerSelectionLoader);
        centerInfoPage = loadPage(centerInfoLoader);
        eventReportPage = loadPage(eventReportLoader);
    }

    /**
     * Opens a new page of type {@link PageType}.
     * <p>Pages are re-usable so data from previous interactions will be present if not reset. If a page type cannot be found the error page is opened and an {@link IllegalArgumentException} is printed onto the stacktrace.
     *
     * @param type The type of page to open
     * @return The opened page or null if no page of <code>type</code> exists
     */
    public static Page open(@Nonnull PageType type) {
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
            case CENTERINFO:
                return open(centerInfoPage);
            case EVENTREPORT:
                return open(eventReportPage);
            case ERRORPAGE:
                return open(errorPage);
            default: {
                IllegalArgumentException e = new IllegalArgumentException("Cannot open page of type " + type);
                e.printStackTrace();
                return openErrorPage(e);
            }
        }
    }

    /**
     * Convenience method to open a new page.
     * <p>The current scene root node is replaced with {@link Page#getRoot()}
     *
     * @param page The page to open
     * @return The opened page
     */
    private static Page open(Page page) {
        scene.setRoot(page.getRoot());
        return page;
    }

    /**
     * Opens a new empty scene of size 1280px * 720px .
     * <p>The scene contains an empty HBox.
     *
     * @param stage The stage to add the scene to
     */
    public static void openEmptyScene(Stage stage) {
        scene = new Scene(new HBox(), 1280, 720);
        stage.setScene(scene);
    }

    /**
     * Convenience method which loads a FXML controller.
     *
     * @param loader The loader to load the controller from
     * @return The loaded controller
     * @throws IOException If an error occurs during loading
     */
    private static Page loadPage(FXMLLoader loader) throws IOException {
        loader.load();
        return loader.getController();
    }

    /**
     * Convenience method to open the error page and set its exception.
     *
     * @param e The exception to set
     * @return The opened error page
     */
    public static Page openErrorPage(Exception e) {
        ErrorPage page = (ErrorPage) open(errorPage);
        page.setError(e);
        return page;
    }
}
