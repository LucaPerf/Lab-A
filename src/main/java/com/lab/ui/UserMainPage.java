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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
    private AnchorPane root;
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
    @FXML
    private HBox typeFilters;
    @FXML
    private JFXToggleNode hub;
    @FXML
    private JFXToggleNode ospedaliero;
    @FXML
    private JFXToggleNode aziendale;
    @FXML
    private JFXToggleNode nameFilter;
    @FXML
    private JFXToggleNode comuneTypeFilter;

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

        //Set up filters
        nameFilter.setOnAction(actionEvent -> setSearchTypeUI(true));
        comuneTypeFilter.setOnAction(event -> setSearchTypeUI(false));
        aziendale.setOnAction(event -> setTypeFiltersUI(CenterType.AZIENDALE));
        hub.setOnAction(event -> setTypeFiltersUI(CenterType.HUB));
        ospedaliero.setOnAction(event -> setTypeFiltersUI(CenterType.OSPEDALIERO));

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
            PagesManager.openAreaSelection();
            setLoggedOut();
        });
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
        nameFilter.setSelected(true);
        aziendale.setSelected(false);
        hub.setSelected(false);
        ospedaliero.setSelected(true);
        comuneTypeFilter.setSelected(false);
        typeFilters.setVisible(false);
        searchbar.clear();
    }

    /**
     * Sets the page to login mode. User information is stored ,logout and username label are enabled.
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
        if (nameFilter.isSelected())
            return null;
        else if (ospedaliero.isSelected())
            return CenterType.OSPEDALIERO;
        else if (aziendale.isSelected())
            return CenterType.AZIENDALE;
        else
            return CenterType.HUB;
    }

    private void search() {
        Cittadini.cercaCentroVaccinale(searchbar.getText(), getTypeFromUI());
    }

    //Hides or shows the UI type filter buttons
    private void setTypeFiltersUI(CenterType type) {
        switch (type) {
            case OSPEDALIERO: {
                hub.setSelected(false);
                aziendale.setSelected(false);
                break;
            }
            case AZIENDALE: {
                hub.setSelected(false);
                ospedaliero.setSelected(false);
                break;
            }
            case HUB: {
                aziendale.setSelected(false);
                ospedaliero.setSelected(false);
                break;
            }
        }
    }

    //Hides or shoes the UI search type buttons
    private void setSearchTypeUI(boolean byName) {
        if (byName) {
            typeFilters.setVisible(false);
            comuneTypeFilter.setSelected(false);
        } else {
            nameFilter.setSelected(false);
            typeFilters.setVisible(true);
        }
    }
}
