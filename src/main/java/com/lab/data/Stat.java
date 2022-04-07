package com.lab.data;

/**
 * This class represents an aggregate statistic.
 * <p>Name, average and number of reports are contained in this class.<br>
 * Values are updated through the {@link Stat#update(float)} method.
 */
public class Stat {
    /**
     * The maximum value a stat can reach
     */
    public final static int MAX_VALUE = 5;
    /**
     * The name of this statistic
     */
    private String name;
    /**
     * The average value of reports
     */
    private float average;
    /**
     * The number of reports
     */
    private long reports;

    /**
     * Creates a new empty stat of <code>name</code>.
     * <p>Reports and average will be set to 0.
     *
     * @param name The type of stat
     */
    public Stat(String name) {
        this.name = name;
        average = 0;
        reports = 0;
    }

    /**
     * Creates a new stat from a CSV row.
     * <p>Each element of <code>row</code> is a cell.
     *
     * @param row The row to get the data from, mapped as follows:<br>
     *            0: {@link #name}<br>
     *            1: {@link #average}<br>
     *            2: {@link #reports}<br>
     */
    public Stat(String[] row) {
        name = row[0];
        average = Float.parseFloat(row[1]);
        reports = Long.parseLong(row[2]);
    }

    /**
     * Updates the stat average and number of reports.
     * <p>The number of reports is increased by 1.<br>
     * The average value is calculated with the expression <br><code>newAverage = (oldAverage * oldReports + newValue)/(oldReports + 1)</code>
     *
     * @param value The new event value
     */
    public void update(float value) {
        average = (average * reports + value) / ++reports;
    }

    /**
     * @return The average value of all the reports
     */
    public float getAverage() {
        return average;
    }

    /**
     * @return The normalized average of this stat
     */
    public double getAverageNormalized() {
        return average / MAX_VALUE;
    }

    /**
     * @return The number of reporst
     */
    public long getReports() {
        return reports;
    }

    /**
     * @return The name of this stat
     */
    public String getName() {
        return name;
    }

    /**
     * @return A CSV row representation of this object,mapped as follows:<br>
     * 0: {@link #name}<br>
     * 1: {@link #average}<br>
     * 2: {@link #reports}<br>
     */
    public String[] toRow() {
        return new String[]{name, Float.toString(average), Long.toString(reports)};
    }
}
