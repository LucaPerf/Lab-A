package com.lab.ui;

import com.lab.data.Center;
import com.lab.data.CenterType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class represents the center registration page. Layout is stored in "center_registration.fxml".
 * @author Ciceri Luigi
 */
public class CenterRegPage extends Page {
    private Button register;
    private Button back;
    private TextField nameField;
    private ComboBox centerTypeCombo;
    private TextField addressField;
    private TextField cityField;
    private TextField capField;
    private Center center;
    /**
     * Creates a new instance of this class with nodes from center_registration.fxml
     */
    public CenterRegPage() {
        super("center_registration.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void getComponents() {
        nameField = (TextField) root.lookup("#centerName");

        //Set center type items and select "Ospedaliero"
        centerTypeCombo = (ComboBox) root.lookup("#typeSelector");
        centerTypeCombo.getItems().addAll(CenterType.values());
        centerTypeCombo.getSelectionModel().select(0);

        addressField = (TextField) root.lookup("#addressName");

        cityField = (TextField) root.lookup("#comuneName");

        capField = (TextField) root.lookup("#cap");

        register = (Button) root.lookup("#registerButton");
        register.setOnAction(actionEvent -> {
            registraCentroVaccinale();
            PagesManager.openCenterActions();
        });

        back = (Button) root.lookup("#backButton");
        back.requestFocus();
        back.setOnAction(actionEvent -> {
            PagesManager.openCenterActions();
        });
    }

    private void registraCentroVaccinale() {
        System.out.println("Not implemented");
        /*
        PostalAddress address = new PostalAddress(
            addressField.getText(),
            comuneField.getText(),
            Integer.parseInt(capField.getText()));
        center = new Center(nameField.getText(),address,(CenterType)centerTypeCombo.getSelectionModel().getSelectedItem());
        Centri.add(center);
        Centri.save();
         */
    }
}
