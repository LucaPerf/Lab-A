package com.lab.ui;

import com.lab.data.Stat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Controller class for {@link StatField}. This class represents a stat field, made up of two labels indicating the number of reports and the average reported intensity and a progressbar used to show the average value.
 *
 * @author Luigi Ciceri
 */
public class StatField {
    @FXML
    private Label name;
    @FXML
    private Label value;
    @FXML
    private ProgressBar bar;

    /**
     * Stets the stat UI
     *
     * @param stat The stat to get the values from
     */
    public void setStat(Stat stat) {
        name.setText(stat.getType().toString());
        value.setText(String.format("%.1f", stat.getAverage()));
        bar.setProgress(stat.getAverageNormalized());
    }
}
