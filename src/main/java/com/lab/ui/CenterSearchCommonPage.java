package com.lab.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
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
 * Controller of the reusable center search UI.
 * <p>Layout is stored in "center_search_common.fxml". This can can be re-used.
 * It contains a searchbar, a set of search filters and a {@link JFXListView} containing the search results.<br>
 * Every item in the list is associated with a {@link Center} instance.<br>
 * As this page can be used im multiple layouts, the action to be performed when clicking on a list item has to be set with {@link #setOnListItemAction(EventHandler)}. The default action is to do nothing.
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
     * Clears the result list and adds last 25 registered centers to the search result list.
     */
    public void setupCentersList() {
        centers.getItems().clear();
        centers.getItems().addAll(Centri.getRecent(25));
    }

    /**
     * @return The selected center type to search for
     */
    private CenterType getTypeFromUI() {
        if (searchModeGroup.getSelectedToggle() == name)
            return null;
        else
            return (CenterType) centerTypeGroup.getSelectedToggle().getUserData();
    }

    /**
     * Performs a center search using the currently selected filters.
     */
    private void search() {
        String key = searchbar.getText();
        if (!key.equals("")) {
            centers.getItems().clear();
            centers.getItems().addAll(Cittadini.cercaCentroVaccinale(key, getTypeFromUI()));
        }
    }

    /**
     * Manages type filters visibility.
     *
     * @param visible <code>true</code> shows the type filters, <code>false</code> hides them
     */
    private void setTypeFiltersVisible(boolean visible) {
        if (visible) {
            filtersBox.getChildren().add(typeFiltersBox);
        } else {
            filtersBox.getChildren().remove(typeFiltersBox);
        }
    }

    /**
     * Sets the method to be executed when a center on the result list is clicked.
     * <p>By default an empty {@link EventHandler} is executed, which does nothing. This method sets a new list cell factory.
     *
     * @param event The event handler to be executed
     */
    public void setOnListItemAction(EventHandler<MouseEvent> event) {
        //Sets a new cell factory binding event to OnMouseClicked
        centers.setCellFactory(param -> {
            JFXListCell<Center> cell = new JFXListCell<>();
            cell.setOnMouseClicked(event);
            return cell;
        });
    }

    /**
     * @return The currently selected center in the result list
     * <p>The selection happens when an item is clicked
     */
    public Center getSelectedCenter() {
        return centers.getSelectionModel().getSelectedItem();
    }
}
