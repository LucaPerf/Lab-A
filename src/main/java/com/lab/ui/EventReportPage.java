package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.data.Center;
import com.lab.data.Event;
import com.lab.data.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class EventReportPage extends Page {
    @FXML
    private BorderPane root;
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

    private Center center;

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
        type.getItems().addAll(EventType.values());
        type.getValidators().add(requiredFieldValidator);

        back.setOnMouseClicked(event -> PagesManager.open(PagesManager.PageType.CENTERINFO));

        intensitySlider.setOnMouseReleased(event -> setIntensityText((int) intensitySlider.getValue()));

        report.setOnAction(event -> {
            if (type.validate()) {
                System.out.println("Not implemented");
                PagesManager.open(PagesManager.PageType.CENTERINFO);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {

    }

    /**
     * Sets the center the event will be added to
     *
     * @param center The center to add the event to
     */
    public void setCenter(Center center) {
        this.center = center;
    }

    //Sets the intensity text value (Molto lieve, Lieve, Moderata, Forte, Molto forte)
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

    private Event eventFromUI() {
        return new Event((int) intensitySlider.getValue(), notes.getText(), type.getSelectionModel().getSelectedItem());
    }
}
