package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller of the user area main page.
 * <p>Layout is stored in "user_main.fxml".
 * This page contains a toolbar with several buttons which are dynamically shown base on user login status: if the user is logged in the username is shown next to a logout button, otherwise a login and register buttons are shown.
 * This page also contains a {@link CenterSearchCommonPage} to search for centers. By clicking an item on the list a {@link CenterInfoPage} opens up.
 */
public class UserMainPage extends Page {
    @FXML
    private Label username;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXToolbar topbar;
    @FXML
    private StackPane root;
    @FXML
    private JFXRippler back;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;

    //Warning dialog
    @FXML
    private JFXDialog logoutWarning;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton cancel;
    @FXML
    private CenterSearchCommonPage searchController;

    /**
     * The currently logged in user.
     * <p>This is <code>null</code> if no user is logged in.
     */
    private User currentUser;
    /**
     * This snackbar will be shown when a user has been registered successfully.
     * <p>A snackbar is a small notification at the bottom of the screen, which will be hidden autmatically after a certain amount of time.
     */
    private JFXSnackbar userRegisteredNotification;
    /**
     * Layout for {@link #userRegisteredNotification}
     */
    private JFXSnackbarLayout userRegisteredNotificationLayout = new JFXSnackbarLayout("Registrazione effettuata.\nOra puoi eseguire l'accesso");

    /**
     * @return {@inheritDoc}
     */
    @Override
    public Parent getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        userRegisteredNotification = new JFXSnackbar(root);

        searchController.setOnListItemAction(event ->
        {
            CenterInfoPage page = (CenterInfoPage) PagesManager.open(PagesManager.PageType.CENTERINFO);
            page.setUser(currentUser);
            page.setCenter(searchController.getSelectedCenter());
        });

        logout.setOnAction(actionEvent -> setLoggedOut());
        login.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.USERLOGIN).reset());
        register.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.USERREGISTRATION).reset());
        back.setOnMouseClicked(actionEvent -> {
            if (currentUser != null)
                logoutWarning.show(root);
            else {
                PagesManager.open(PagesManager.PageType.AREASELECTION);
            }
        });
        exit.setOnAction(event ->
        {
            logoutWarning.close();
            PagesManager.open(PagesManager.PageType.AREASELECTION);
        });
        cancel.setOnAction(event -> logoutWarning.close());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        setLoggedOut();
        searchController.reset();
    }

    /**
     * Sets the page to login mode.
     * <p>User information is stored and logout and username label are enabled.
     * Other dynamic nodes are hidden.
     *
     * @param user The logged in user
     */
    public void setLoggedIn(User user) {
        currentUser = user;

        topbar.getRightItems().addAll(username, logout);
        username.setText(currentUser.getUsername());
        topbar.getRightItems().removeAll(register, login);
    }

    /**
     * Sets the page to logout mode.
     * <p>Login and register buttons are shown, other dynamic nodes are hidden.
     * As this method is also invoked in {@link #initialize()}, login button must not be added again because they are already in the original FXML layout.
     */
    public void setLoggedOut() {
        currentUser = null;
        username.setText("");
        topbar.getRightItems().removeAll(username, logout);
        //If this method is called from initialize(), register and login buttons will already be there because they are defined inside the fxml
        if (topbar.getRightItems().size() == 0)
            topbar.getRightItems().addAll(login, register);
    }


    /**
     * This shows a notification which informs that a user was successfully registered
     */
    public void showUserRegisteredNotification() {
        userRegisteredNotification.fireEvent(new JFXSnackbar.SnackbarEvent(userRegisteredNotificationLayout, NOTIFICATION_TIMEOUT));
    }
}
