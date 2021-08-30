package com.lab.centrivaccinali;

import com.lab.data.Center;
import com.lab.data.VaxInfo;
import com.lab.datamanager.Centri;
import com.lab.datamanager.Data;
import com.lab.datamanager.Registrati;
import com.lab.datamanager.Vaccinati;
import com.lab.ui.ErrorPage;
import com.lab.ui.PagesManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

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
        PagesManager.openEmptyScene(stage);
        stage.show();
        //UI loading
        PagesManager.initialize(stage);

        //Data loading
        Data.createDirectory();
        try {
            Centri.load();
            Registrati.load();
        } catch (IOException e) {
            ErrorPage page = (ErrorPage) PagesManager.open(PagesManager.PageType.ERRORPAGE);
            page.setError(e);
            return;
        }

        PagesManager.open(PagesManager.PageType.AREASELECTION);
    }

    /**
     * Add a new center into "CentriVaccinali.csv"
     *
     * @param center The center to add
     * @throws IOException If the center could not be added to the file
     */
    public static void registraCentroVaccinale(Center center) throws IOException {
        Centri.add(center);
    }

    /**
     * Add a new vaccinated citizen into the <code>centerName</code> own file
     *
     * @param centerName The name of the center to add the information to
     * @param info       The information to add
     * @throws IOException If the vaccinated user could not be written to the file
     */
    public static void registraVaccinato(String centerName, VaxInfo info) throws IOException {
        Vaccinati.add(centerName, info);
    }
}
