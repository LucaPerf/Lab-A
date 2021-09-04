package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * This class represents a center information page.
 * <p>Each piece of information is shown inside a properly themed disabled {@link JFXTextField}.
 * Below all information a separate section shows all statistics of the center trough {@link StatField} elements.
 * At the bottom of the screen a button allows a logged-in user to report events.
 * This button is hidden if no login has been executed.
 */
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

    /**
     * This snackbar will be shown when an event has been successfully reported.
     * <p>A snackbar is a small notification at the bottom of the screen, which will be hidden autmatically after a certain amount of time.
     */
    private JFXSnackbar eventAddedNotification;
    /**
     * Layout of {@link #eventAddedNotification}
     */
    private JFXSnackbarLayout eventAddedNotificationLayout = new JFXSnackbarLayout("Evento segnalato con successo");
    /**
     * The center to show the information of.
     * <p>This object must be set from another page with {@link #setCenter(Center)}
     */
    private Center center;
    /**
     * This is needed when an event report page is to be shown after this one.
     * <p>This object must be set from another page with {@link #setUser(User)} and will be passed to the event report page with {@link EventReportPage#setUser(User)}.
     * If this is <code>null</code> no user is logged in and the event report button is hidden.
     */
    private User user;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        center = null;
        setUser(null);
    }

    /**
     * Sets the center of which information will be shown on this page.
     * <p>Apart from setting {@link #center}, all UI labels are set according to data contained in <code>center</code>.
     *
     * @param center The center to show the information of
     */
    public void setCenter(Center center) {
        this.center = center;
        name.setText(center.getName());
        type.setText(center.getType().toString());
        PostalAddress address = center.getAddress();
        street.setText(address.getStreet());
        comune.setText(address.getDistrict());
        province.setText(address.getProvince());
        cap.setText(address.getCap().toString());
        updateStats();
    }

    /**
     * Sets the user which will be passed to {@link EventReportPage} instance.
     * <p>This method is also responsible for showing or hiding the event report button.
     * If <code>user</code> is null, the button will be hidden.
     *
     * @param user The currently logged-in user
     */
    public void setUser(User user) {
        if (user != null) {
            borderPane.setBottom(report);
            this.user = user;
        } else
            borderPane.setBottom(null);
    }

    /**
     * Shows the event added notification
     */
    public void showEventAddedNotification() {
        eventAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(eventAddedNotificationLayout, NOTIFICATION_TIMEOUT));
    }

    /**
     * Updates the stats indicators with current data from the currently set center.
     * <p>Per type statistics are shown through a {@link StatField}, the global statistic is shown in plain text instead with the following syntax:<br>
     * "Il centro ha ricevuto  + number of reports +  segnalazione\i  con una media di  + average" or<br>
     * "Nessuna evento avverso segnalato" if no report has been filed.
     */
    public void updateStats() {
        Stat global = center.getStat("Global");
        long reports = global.getReports();
        if (reports > 0)
            globalStat.setText("Il centro ha ricevuto " + reports + (reports == 1 ? " segnalazione " : " segnalazioni") + " con una media di " + String.format("%.1f", global.getAverage()));
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
