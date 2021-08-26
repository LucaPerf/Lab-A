package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.lab.cittadini.Cittadini;
import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.datamanager.Centri;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller of the center search UI, which means searchbar + search filters + centers result list all inside a {@link VBox}. Layout is stored in "center_search_common.fxml".
 *
 * @author Ciceri Luigi
 */
public class CenterSearchCommonPage extends Page {
    @FXML
    private VBox root;
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
    private JFXGroupToggleButton name;
    @FXML
    private JFXGroupToggleButton comune;
    @FXML
    private HBox typeFiltersBox;
    @FXML
    private ToggleGroup centerTypeGroup;
    @FXML
    private JFXGroupToggleButton hub;
    @FXML
    private JFXGroupToggleButton ospedaliero;
    @FXML
    private JFXGroupToggleButton aziendale;

    /**
     * {@inheritDoc}
     *
     * @return
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
        setupCentersList();

        //Bind enum to each button
        hub.setUserData(CenterType.HUB);
        ospedaliero.setUserData(CenterType.OSPEDALIERO);
        aziendale.setUserData(CenterType.AZIENDALE);

        setOnListItemAction(event -> {
        });

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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        setupCentersList();
        //Reset filters
        searchModeGroup.selectToggle(name);
        centerTypeGroup.selectToggle(ospedaliero);
        setTypeFiltersVisible(false);
        searchbar.clear();
    }

    /**
     * Adds 25 centers to the search result list.
     */
    public void setupCentersList() {
        centers.getItems().clear();
        int i = 0;
        for (Center center : Centri.getCenters().values()) {
            if (i < 25) {
                centers.getItems().add(0,center);
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
        String key = searchbar.getText();
        if (!key.equals("")) {
            centers.getItems().clear();
            centers.getItems().addAll(Cittadini.cercaCentroVaccinale(key, getTypeFromUI()));
        }
    }

    //Shows or hides the comune type filters
    private void setTypeFiltersVisible(boolean visible) {
        if (visible) {
            filtersBox.getChildren().add(typeFiltersBox);
        } else {
            filtersBox.getChildren().remove(typeFiltersBox);
        }
    }

    /**
     * Sets the method to be executed when a center on the result list is clicked. By default an empty {@link EventHandler} is executed, which does nothing.
     *
     * @param event The event handler to be executed
     */
    public void setOnListItemAction(EventHandler<MouseEvent> event) {
        centers.setOnMouseClicked(event);
    }

    /**
     * @return The currently selected center in the result list
     */
    public Center getSelectedCenter() {
        return centers.getSelectionModel().getSelectedItem();
    }
}
