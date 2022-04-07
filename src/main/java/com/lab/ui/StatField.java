package com.lab.ui;

import com.lab.data.Stat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Controller class for {@link StatField}.
 * <p>This class represents a stat field. Lyout is stored in "stat_field.fxml"<br>
 * It is made up of two labels indicating the number of reports and the average reported intensity plus a progressbar used to show the average value.
 */
public class StatField {
    @FXML
    private Label name;
    @FXML
    private Label value;
    @FXML
    private ProgressBar bar;

    /**
     * Stets the stat UI from a stat object
     *
     * @param stat The stat to get the values from
     */
    public void setStat(Stat stat) {
        name.setText(stat.getName());
        value.setText(String.format("%.1f", stat.getAverage()));
        bar.setProgress(stat.getAverageNormalized());
    }
}
