package com.lab.ui;

import com.jfoenix.controls.*;
import com.lab.centrivaccinali.CentriVaccinali;
import com.lab.data.VaxInfo;
import com.lab.data.VaxType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller of the vaccination registration page. Layout is stored in "vax:registration.fxml".
 *
 * @author Ciceri Luigi
 */
public class VaxRegPage extends Page {

    @FXML
    private StackPane root;
    @FXML
    private JFXTextField center;
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
    private JFXButton back;
    //Dialog
    @FXML
    private JFXDialog addAgain;
    @FXML
    private JFXButton yes;
    @FXML
    private JFXButton no;

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
        type.getItems().addAll(VaxType.values());
        add.setOnAction(actionEvent ->
        {
            CentriVaccinali.registraVaccinato(center.getText(), infoFromUI());
            addAgain.show(root);
        });
        back.setOnAction(actionEvent -> PagesManager.openCenterActions());

        //Dialog
        yes.setOnAction(actionEvent -> addAgain.close());
        no.setOnAction(actionEvent -> {
            addAgain.close();
            PagesManager.openCenterActions();
        });
    }

    private VaxInfo infoFromUI() {
        return new VaxInfo(name.getText(), surname.getText(), ccf.getText(), date.getValue(), type.getSelectionModel().getSelectedItem(), Integer.parseInt(uID.getText()));
    }
}
