package com.lab.data;

/**
 * This class represents the information of an adverse event on a vaccinated person
 *
 * @author Luca Perfetti
 */

public class Event {

    private Integer intensity;
    private String report;
    private EventType type;

    /**
     *
     * @return The intensity of this event
     */
    public Integer getIntensity() {
        return intensity;
    }

    /**
     *
     * @return The report of this event
     */
    public String getReport() {
        return report;
    }

    /**
     *
     * @return The type of this event
     */
    public EventType getType() {
        return type;
    }

    /**
     * Created a Event object
     *
     * @param intensity The intensity of the event
     * @param report The report of the event
     * @param type The type of the event
     */
    public Event(Integer intensity, String report, EventType type){
        this.intensity = intensity;
        this.report = report;
        this.type = type;
    }
}
