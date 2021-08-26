package com.lab.data;

/**
 * This class represents a n aggregate statistic about an event type.
 * Values are updated through the <code>update</code> method.
 *
 * @author Luigi Ciceri
 */
public class Stat {
    private EventType type;
    private float average;
    private long reports;

    /**
     * Creates a new stat of <code>type</code> and with an average of <code>value</code>. The report variable will be set to 1.
     *
     * @param type  The type of stat
     * @param value The average value of this stat
     */
    public Stat(EventType type, float value) {
        this.type = type;
        average = value;
        reports = 1;
    }

    /**
     * Creates a new stat from a CSV row.
     *
     * @param row The row to get the data from mapped as follows:<br>
     *            * 0: {@link #type}<br>
     *            * 1: {@link #average}<br>
     *            * 2: {@link #reports}<br>
     */
    public Stat(String[] row) {
        type = EventType.fromString(row[0]);
        average = Float.parseFloat(row[1]);
        reports = Long.parseLong(row[2]);
    }

    /**
     * Updates the stat average and number of reports.
     * The number of reports is increased by 1.
     * The average value is calculated with the expression <br><code>newAverage = (oldAverage * oldReports + newValue)/(oldReports + 1)</code>
     *
     * @param value The new evnt value
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
     * @return The number of reporst
     */
    public long getReports() {
        return reports;
    }

    /**
     * @return The type of event represented by this stat
     */
    public EventType getType() {
        return type;
    }

    /**
     * @return A CSV row representation of this object,mapped as follows:<br>
     * 0: {@link #type}<br>
     * 1: {@link #average}<br>
     * 2: {@link #reports}<br>
     */
    public String[] toRow() {
        return new String[]{type.toString(), Float.toString(average), Long.toString(reports)};
    }
}
