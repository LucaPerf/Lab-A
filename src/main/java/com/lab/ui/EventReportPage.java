package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.cittadini.Cittadini;
import com.lab.data.Center;
import com.lab.data.Event;
import com.lab.data.EventType;
import com.lab.data.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * This class represents an event reporting page.
 * <p>A combo box is shown to select the type of event, as well as a slider for the intensity and a text area for the report comment.<br>
 * The report comment is limited to 256 characters by using a {@link TextFormatter}. An indicator below the area shows the current comment length.
 *
 * @author Luigi Ciceri
 */
public class EventReportPage extends Page {
    @FXML
    private StackPane root;
    @FXML
    private JFXRippler back;
    @FXML
    private JFXSlider intensitySlider;
    @FXML
    private Label intensityText;
    @FXML
    private JFXButton report;
    @FXML
    private JFXComboBox<EventType> type;
    @FXML
    private JFXTextArea notes;
    @FXML
    private Label charCounter;

    /**
     * This snackbar will be shown when an event cannot be reported.
     * <p>This could be due to the fact that an event of the same type has already been reported or the user has not benn vaccinated yet.
     * A snackbar is a small notification at the bottom of the screen, which will be hidden automatically after a certain amount of time.
     */
    private JFXSnackbar eventAddedNotification;
    /**
     * Layout of {@link #eventAddedNotification}
     */
    private JFXSnackbarLayout eventAddedNotificationLayout = new JFXSnackbarLayout("Segnalazione evnto fallita.\nNon hai selezionato il centro corretto oppure hai gi\u00E0 segnalato un evento di questo tipo");
    /**
     * The center whose statistics will be updated with the reported event.
     * <p>This is passed from previous pages with {@link #setCenter(Center)}
     */
    private Center center;
    /**
     * The user which is reporting the event.
     * <p>This is passed from previous pages with {@link #setUser(User)}}
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
        //Updates character counter and limits length of notes field
        notes.setTextFormatter(new TextFormatter<>(change ->
        {
            if (change.isContentChange() && change.getControlNewText().length() <= 256) {
                charCounter.setText(change.getControlNewText().length() + "/256");
                return change;
            }
            return null;
        }));

        type.getItems().addAll(EventType.values());
        type.getValidators().add(requiredFieldValidator);

        back.setOnMouseClicked(event -> PagesManager.open(PagesManager.PageType.CENTERINFO));

        intensitySlider.setOnMouseReleased(event -> setIntensityText((int) intensitySlider.getValue()));

        report.setOnAction(event -> {
            if (type.validate()) {
                boolean reportAdded;
                try {
                    reportAdded = Cittadini.inserisciEventiAvversi(user.getuID(), eventFromUI(), center);
                } catch (IOException e) {
                    e.printStackTrace();
                    ErrorPage page = (ErrorPage) PagesManager.open(PagesManager.PageType.ERRORPAGE);
                    page.setError(e);
                    return;
                }
                if (reportAdded) {
                    CenterInfoPage page = (CenterInfoPage) PagesManager.open(PagesManager.PageType.CENTERINFO);
                    page.updateStats();
                    page.showEventAddedNotification();
                } else
                    eventAddedNotification.fireEvent(new JFXSnackbar.SnackbarEvent(eventAddedNotificationLayout, NOTIFICATION_TIMEOUT));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        resetField(type);
        intensitySlider.setValue(3);
        notes.clear();
    }

    /**
     * Sets the center the event will be added to
     *
     * @param center The center to add the event to
     */
    public void setCenter(Center center) {
        this.center = center;
    }

    /**
     * Sets the currently logged-in user. The event will be added to this user's data.
     *
     * @param user The logged-in user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the intensity label.
     * <p>Text depends on the slider position:<br>
     * 1:Molto lieve<br>
     * 2:Lieve<br>
     * 3:Moderata<br>
     * 4:Forte<br>
     * 5:Molto forte
     */
    private void setIntensityText(int intensity) {
        String text;
        switch (intensity) {
            case 1: {
                text = "Molto lieve";
                break;
            }
            case 2: {
                text = "Lieve";
                break;
            }
            case 3: {
                text = "Moderata";
                break;
            }
            case 4: {
                text = "Forte";
                break;
            }
            case 5: {
                text = "Molto forte";
                break;
            }
            default:
                text = "";
        }
        intensityText.setText(text);
    }

    /**
     * @return An {@link Event} instance containing data taken from the UI
     */
    private Event eventFromUI() {
        return new Event((int) intensitySlider.getValue(), notes.getText(), type.getSelectionModel().getSelectedItem());
    }
}
