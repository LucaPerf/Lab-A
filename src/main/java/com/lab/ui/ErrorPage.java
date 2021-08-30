package com.lab.ui;

import com.google.common.base.Throwables;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;


public class ErrorPage extends Page {
    private VBox root = new VBox();
    private Label title = new Label("Si \u00E8 verificato un errore!");
    private ScrollPane scroll = new ScrollPane();
    private Label stackTrace = new Label();
    private FontIcon errorIcon = new FontIcon("mdmz-warning");
    private JFXButton close = new JFXButton("ESCI");
    private String red = "#D32F2F";

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    protected void initialize() {
        //Setup root
        root.setPadding(new Insets(16, 16, 16, 16));
        root.setSpacing(16.0);
        root.setFillWidth(true);
        root.getStylesheets().add("/css/light.css");
        root.setAlignment(Pos.CENTER);
        //Setup error icon
        errorIcon.setIconSize(120);
        errorIcon.setIconColor(Paint.valueOf(red));
        root.getChildren().add(errorIcon);
        //Setup title
        title.getStyleClass().add("headline6");
        root.getChildren().add(title);
        //Setup error text and scroll pane
        stackTrace.getStyleClass().add("subtitle1");
        scroll.setFitToWidth(true);
        scroll.setContent(stackTrace);
        scroll.getStyleClass().add("jfx-list-view");
        root.getChildren().add(scroll);
        //Setup close button
        close.getStyleClass().add("button-contained");
        close.setStyle("-fx-background-color:" + red + ";");
        close.setOnAction(event -> System.exit(0));
        close.setPrefWidth(300.0);
        root.getChildren().add(close);
    }

    @Override
    public void reset() {

    }

    /**
     * Sets the exception to be shown as error. The exception stacktrace will be shown
     *
     * @param e The thrown exception
     */
    public void setError(Exception e) {
        stackTrace.setText(Throwables.getStackTraceAsString(e));
    }
}