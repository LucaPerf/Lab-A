package com.lab.data;

/**
 * This class represents the information of an adverse event reported by vaccinated person.
 * <p>Event intensity, type and report are stored.
 * Getters are provided for needed variable, as well as methods and constructors to convert to and from CSV row representation.
 *
 * @author Luca Perfetti
 * @author Luigi Ciceri
 */

public class Event {
    /**
     * The intensity of the event
     */
    private int intensity;
    /**
     * The optional report, maximum length 256 characters
     */
    private String report;
    /**
     * The {@link EventType}
     */
    private EventType type;

    /**
     * @return The intensity of this event
     */
    public Integer getIntensity() {
        return intensity;
    }

    /**
     * @return The report of this event
     */
    public String getReport() {
        return report;
    }

    /**
     * @return The type of this event
     */
    public EventType getType() {
        return type;
    }

    /**
     * Creates an Event object.
     * <p>The string <code>report</code> is trimmed.
     *
     * @param intensity The intensity of the event
     * @param report    The report of the event
     * @param type      The type of event
     */
    public Event(int intensity, String report, EventType type) {
        this.intensity = intensity;
        this.report = report.trim();
        this.type = type;
    }

    /**
     * Creates a new event from a CSV row
     *
     * @param row The row to get data from, indexes mapped as follows:
     *            0: {@link #type}<br>
     *            1: {@link #intensity}<br>
     *            2: {@link #report}
     */
    public Event(String[] row) {
        type = EventType.fromString(row[0]);
        intensity = Integer.parseInt(row[1]);
        report = row[2];
    }

    /**
     * @return A CSV row representing this object, mapped as follows:<br>
     * 0: {@link #type}<br>
     * 1: {@link #intensity}<br>
     * 2: {@link #report}
     */
    public String[] toRow() {
        return new String[]{type.toString(), Integer.toString(intensity), report};
    }
}
