package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.centrivaccinali.CentriVaccinali;
import com.lab.data.Center;
import com.lab.data.VaxInfo;
import com.lab.data.VaxType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

/**
 * Controller of the vaccination registration page. Layout is stored in "vax:registration.fxml".
 *
 * @author Ciceri Luigi
 */
public class VaxRegPage extends Page {

    @FXML
    private StackPane root;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField surname;
    @FXML
    private JFXTextField ccf;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<VaxType> type;
    @FXML
    private JFXTextField uID;
    @FXML
    private JFXButton add;
    @FXML
    private JFXRippler back;
    //Dialog
    @FXML
    private JFXDialog addAgain;
    @FXML
    private JFXButton yes;
    @FXML
    private JFXButton no;

    private Center center;

    /**
     * {@inheritDoc}
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        //Add validators
        name.getValidators().add(requiredFieldValidator);
        surname.getValidators().add(requiredFieldValidator);
        ccf.getValidators().add(requiredFieldValidator);
        ccf.getValidators().add(ccfValidator);
        date.getValidators().add(requiredFieldValidator);
        type.getValidators().add(requiredFieldValidator);
        uID.getValidators().add(requiredFieldValidator);
        uID.getValidators().add(uIDValidator);

        date.setValue(LocalDate.now());

        type.getItems().addAll(VaxType.values());

        add.setOnAction(actionEvent ->
        {
            if (isDataValid()) {
                CentriVaccinali.registraVaccinato(center.getName(), infoFromUI());
                addAgain.show(root);
            }
        });

        back.setOnMouseClicked(actionEvent -> {
            reset();
            PagesManager.open(PagesManager.PageType.CENTERSELECTION);
        });

        //Dialog
        yes.setOnAction(actionEvent -> {
            addAgain.close();
            Center prevCenter = center;
            reset();
            center = prevCenter;
        });

        no.setOnAction(actionEvent -> {
            addAgain.close();
            reset();
            PagesManager.open(PagesManager.PageType.CENTERACTIONS);
        });
    }

    @Override
    public void reset() {
        center = null;
        resetField(name);
        resetField(surname);
        resetField(ccf);
        resetField(date);
        resetField(type);
        resetField(uID);
    }

    private VaxInfo infoFromUI() {
        return new VaxInfo(name.getText(), surname.getText(), ccf.getText(), date.getValue(), type.getSelectionModel().getSelectedItem(), Integer.parseInt(uID.getText()));
    }

    private boolean isDataValid() {
        return name.validate() & surname.validate() & ccf.validate() & date.validate() & type.validate() & uID.validate();
    }

    /**
     * Sets the center the vaccinated user will be added to.
     *
     * @param center The center to add the vaccinated user to
     */
    public void setReferenceCenter(Center center) {
        this.center = center;
    }
}
