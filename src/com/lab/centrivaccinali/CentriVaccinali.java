package com.lab.centrivaccinali;

import com.lab.data.Center;
import com.lab.ui.PagesManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class used to initialize data and UI
 * @author Ciceri Luigi
 */
public class CentriVaccinali extends Application {

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens the UI stage, loads data, and opens the area selection page
     * @param stage The stage to load
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Lab AB");
        PagesManager.initialize(stage);
        stage.show();

        //Load files here

        PagesManager.openAreaSelection();
    }

    public static void registraCentroVaccinale(Center center) {
        System.out.println("Not implemented");
        /*
        Centri.add(center);
        Centri.save();
         */
    }
}
