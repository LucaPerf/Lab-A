package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.cittadini.Cittadini;
import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.User;
import com.lab.datamanager.Centri;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;

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
    private HBox topbar;
    @FXML
    private StackPane root;
    @FXML
    private JFXButton back;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;
    @FXML
    private TextField searchbar;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXListView<Center> centers;

    //Search filters
    @FXML
    private HBox filtersBox;
    @FXML
    private ToggleGroup searchModeGroup;
    @FXML
    private JFXToggleNode name;
    @FXML
    private JFXToggleNode comune;
    @FXML
    private HBox typeFiltersBox;
    @FXML
    private ToggleGroup centerTypeGroup;
    @FXML
    private JFXToggleNode hub;
    @FXML
    private JFXToggleNode ospedaliero;
    @FXML
    private JFXToggleNode aziendale;

    //Warning dialog
    @FXML
    private JFXDialog logoutWarning;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton cancel;

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
        reset();
        setupCentersList();

        //Bind enum to each button
        hub.setUserData(CenterType.HUB);
        ospedaliero.setUserData(CenterType.OSPEDALIERO);
        aziendale.setUserData(CenterType.AZIENDALE);

        searchModeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == name)
                setTypeFiltersVisible(false);
            else if (newValue == comune)
                setTypeFiltersVisible(true);
        });

        //Set up buttons
        searchButton.setOnAction(event -> search());
        searchbar.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                search();
        });

        logout.setOnAction(actionEvent -> setLoggedOut());
        login.setOnAction(actionEvent -> PagesManager.openUserLogin());
        register.setOnAction(actionEvent -> PagesManager.openUserReg());
        back.setOnAction(actionEvent -> {
            if (currentUser != null)
                logoutWarning.show(root);
            else {
                PagesManager.openAreaSelection();
                reset();
            }
        });
        exit.setOnAction(event ->
        {
            PagesManager.openAreaSelection();
            setLoggedOut();
        });
        cancel.setOnAction(event -> logoutWarning.close());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        currentUser = null;
        logout.setVisible(false);
        username.setVisible(false);
        username.setText("");
        //Reset filters
        searchModeGroup.selectToggle(name);
        centerTypeGroup.selectToggle(ospedaliero);
        setTypeFiltersVisible(false);
        searchbar.clear();
    }

    /**
     * Sets the page to login mode. User information is stored, logout and username label are enabled.
     *
     * @param user The user information
     */
    public void setLoggedIn(User user) {
        currentUser = user;

        username.setVisible(true);
        username.setText(currentUser.getUserID());
        logout.setVisible(true);

        register.setVisible(false);
        register.toBack();
        login.setVisible(false);
        login.toBack();
    }

    /**
     * Sets the page to logout mode. Login and register buttons are shown, page information is reset.
     */
    public void setLoggedOut() {
        reset();

        logout.setVisible(false);
        logout.toBack();
        username.setVisible(false);
        username.toBack();

        register.setVisible(true);
        login.setVisible(true);
    }

    /**
     * Adds 25 centers to the search result list.
     */
    public void setupCentersList() {
        centers.getItems().clear();
        int i = 0;
        for (LinkedList<Center> list : Centri.getCenters().values())
            for (Center c : list) {
                if (i < 25) {
                    centers.getItems().add(c);
                    i++;
                } else return;
            }
    }

    private CenterType getTypeFromUI() {
        if (searchModeGroup.getSelectedToggle() == name)
            return null;
        else
            return (CenterType) centerTypeGroup.getSelectedToggle().getUserData();
    }

    private void search() {
        Cittadini.cercaCentroVaccinale(searchbar.getText(), getTypeFromUI());
    }

    //Shows or hides the comune type filters
    private void setTypeFiltersVisible(boolean visible) {
        if (visible) {
            filtersBox.getChildren().add(typeFiltersBox);
        } else {
            filtersBox.getChildren().remove(typeFiltersBox);
        }
    }
}
