package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class CenterInfoPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXRippler back;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField type;
    @FXML
    private JFXTextField street;
    @FXML
    private JFXTextField comune;
    @FXML
    private JFXTextField province;
    @FXML
    private JFXTextField cap;
    @FXML
    private JFXButton report;
    //Stats
    @FXML
    private StatField globalController;
    @FXML
    private StatField headacheController;
    @FXML
    private StatField feverController;
    @FXML
    private StatField artpainController;
    @FXML
    private StatField linfController;
    @FXML
    private StatField tachController;
    @FXML
    private StatField hyperController;
    @FXML
    private JFXTextField globalStat;

    private JFXSnackbar eventAddedNotification;
    private JFXSnackbarLayout eventAddedNotificationLayout = new JFXSnackbarLayout("Evento segnalato con successo");
    private Center center;
    private User user;

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    protected void initialize() {
        eventAddedNotification = new JFXSnackbar(root);

        report.setOnAction(event ->
        {
            EventReportPage page = (EventReportPage) PagesManager.open(PagesManager.PageType.EVENTREPORT);
            page.setCenter(center);
            page.setUser(user);
        });

        back.setOnMouseClicked(event ->
        {
            reset();
            PagesManager.open(PagesManager.PageType.USERMAIN);
        });
    }

    @Override
    public void reset() {
        center = null;
        setUser(null);
    }

    public void setCenter(Center center) {
        this.center = center;
        name.setText(center.getName());
        type.setText(center.getType().toString());
        PostalAddress address = center.getAddress();
        street.setText(address.getStreet());
        comune.setText(address.getDistrict());
        province.setText(address.getProvince());
        cap.setText(address.getCap().toString());
        setStats();
    }

    public void setUser(User user) {
        if (user != null) {
            borderPane.setBottom(report);
            this.user = user;
        } else
            borderPane.setBottom(null);
    }

    /**
     * Shows the event added notification {@link JFXSnackbar}
     */
    public void showEventAddedNotification() {
        eventAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(eventAddedNotificationLayout, SNACKBARDURATION));
    }

    //Sets the stats bars and global stat
    private void setStats() {
        Stat global = center.getStat("Global");
        long reports = global.getReports();
        if (reports > 0)
            globalStat.setText(reports + (global.getReports() == 1 ? " utente ha" : " utenti hanno") + " valutato questo centro con una media di " + String.format("%.1f", global.getAverage()));
        else
            globalStat.setText("Nessuna evento avverso segnalato");

        artpainController.setStat(center.getStat(EventType.ARTPAIN.toString()));
        headacheController.setStat(center.getStat(EventType.HEADACHE.toString()));
        feverController.setStat(center.getStat(EventType.FEVER.toString()));
        linfController.setStat(center.getStat(EventType.LINF.toString()));
        tachController.setStat(center.getStat(EventType.TACH.toString()));
        hyperController.setStat(center.getStat(EventType.HYPER.toString()));
    }
}
