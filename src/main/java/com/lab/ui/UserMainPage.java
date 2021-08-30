package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller of the user area main page. Layout is stored in "user_main.fxml".
 *
 * @author Ciceri Luigi
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

    private User currentUser;

    /**
     * {@inheritDoc}
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
        searchController.setOnListItemAction(event ->
        {
            CenterInfoPage page = (CenterInfoPage) PagesManager.open(PagesManager.PageType.CENTERINFO);
            page.setUser(currentUser);
            page.setCenter(searchController.getSelectedCenter());
        });

        logout.setOnAction(actionEvent -> setLoggedOut());
        login.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.USERLOGIN));
        register.setOnAction(actionEvent -> PagesManager.open(PagesManager.PageType.USERREGISTRATION));
        back.setOnMouseClicked(actionEvent -> {
            if (currentUser != null)
                logoutWarning.show(root);
            else {
                PagesManager.open(PagesManager.PageType.AREASELECTION);
                reset();
            }
        });
        exit.setOnAction(event ->
        {
            PagesManager.open(PagesManager.PageType.AREASELECTION);
            logoutWarning.close();
            reset();
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
     * Sets the page to login mode. User information is stored, logout and username label are enabled.
     *
     * @param user The user information
     */
    public void setLoggedIn(User user) {
        currentUser = user;

        topbar.getRightItems().addAll(username, logout);
        username.setText(currentUser.getUserID());
        topbar.getRightItems().removeAll(register, login);
    }

    /**
     * Sets the page to logout mode. Login and register buttons are shown, page information is reset.
     */
    public void setLoggedOut() {
        currentUser = null;
        username.setText("");
        topbar.getRightItems().removeAll(username, logout);
        //If this method is called from initialize(), register and login buttons will already be there because they are defined inside the fxml
        if (topbar.getRightItems().size() == 0)
            topbar.getRightItems().addAll(login, register);
    }


}
