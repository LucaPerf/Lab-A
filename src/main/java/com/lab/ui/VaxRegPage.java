package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.centrivaccinali.CentriVaccinali;
import com.lab.data.Center;
import com.lab.data.VaxInfo;
import com.lab.data.VaxType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller of the vaccination registration page.
 * <p>Layout is stored in "vax_registration.fxml".
 * This page contains fields required to input data for vaccination registration, including a {@link JFXDatePicker} to input the vaccination date.
 * A confirmation button is shown at the bottom of the screen.
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

    /**
     * The center to add the vaccinated user to.
     * <p>This must be passed from previous the page with {@link #setReferenceCenter(Center)}
     */
    private Center center;

    /**
     * @return {@inheritDoc}
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
                try {
                    CentriVaccinali.registraVaccinato(center.getName(), infoFromUI());
                } catch (IOException e) {
                    e.printStackTrace();
                    ErrorPage page = (ErrorPage) PagesManager.open(PagesManager.PageType.ERRORPAGE);
                    page.setError(e);
                    return;
                }
                addAgain.show(root);
            }
        });

        back.setOnMouseClicked(actionEvent -> PagesManager.open(PagesManager.PageType.CENTERSELECTION));

        //Dialog
        yes.setOnAction(actionEvent -> {
            addAgain.close();
            Center prevCenter = center;
            reset();
            center = prevCenter;
        });

        no.setOnAction(actionEvent -> {
            addAgain.close();
            PagesManager.open(PagesManager.PageType.CENTERACTIONS);
        });
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * @return A {@link VaxInfo} object created with data from the UI
     */
    private VaxInfo infoFromUI() {
        return new VaxInfo(name.getText(), surname.getText(), ccf.getText(), date.getValue(), type.getSelectionModel().getSelectedItem(), Long.parseLong(uID.getText()));
    }

    /**
     * Validates data inout into the page's fields.
     * <p>Lazy evaluation is not used to allow each field to be evaluated.
     * {@link com.jfoenix.validation.base.ValidatorBase} are used to check data correctness
     *
     * @return True if all data is valid
     */
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
