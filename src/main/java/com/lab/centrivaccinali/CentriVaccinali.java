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
        try {
            //UI loading
            PagesManager.initialize(stage);
            //Data loading
            Data.createDirectory();
            Centri.load();
            Registrati.load();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorPage page = (ErrorPage) PagesManager.open(PagesManager.PageType.ERRORPAGE);
            page.setError(e);
            return;
        }

        PagesManager.open(PagesManager.PageType.AREASELECTION);
    }

    /**
     * Add a new center into "CentriVaccinali.csv" if no such center exists
     *
     * @param center The center to add
     * @return True if and only if the center doesn't exist and is added correctly
     * @throws IOException If the center could not be added to the file
     */
    public static boolean registraCentroVaccinale(Center center) throws IOException {
        if (!Centri.contains(center)) {
            Centri.add(center);
            return true;
        }
        return false;
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
