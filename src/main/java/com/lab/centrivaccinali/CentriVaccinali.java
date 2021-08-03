package com.lab.centrivaccinali;

import com.lab.data.Center;
import com.lab.data.VaxInfo;
import com.lab.datamanager.Centri;
import com.lab.datamanager.Registrati;
import com.lab.ui.PagesManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class used to initialize data and UI
 *
 * @author Ciceri Luigi
 */
public class CentriVaccinali extends Application {

    /**
     * Launches the application
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens the UI stage, loads data, and opens the area selection page
     *
     * @param stage The stage to load
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Lab AB");
        PagesManager.initialize(stage);
        stage.show();

        Centri.load();
        Registrati.load();

        PagesManager.openAreaSelection();
    }

    /**
     * Add a new center into "CentriVaccinali.csv"
     *
     * @param center The center to add
     */
    public static void registraCentroVaccinale(Center center) {
        Centri.saveCenter(center);
    }

    /**
     * Add a new vaccinated citizen into the center own file

    public static void registraVaccinato(String centerName, VaxInfo info) {
        Vaccinati.add(centerName,info);
    }
     **/
}
