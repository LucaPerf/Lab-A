package com.lab.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class represents information about a vaccinated person.
 * <p>Name, surname, "Codice Fiscale", vaccination date, type of vaccine and unique vaccination identifier are contained in this class.<br>
 * Reported events are stored into a {@link HashMap}.<br>
 * Required getters and setters are provided.
 *
 * @author Ciceri Luigi
 * @author Luca Perfetti
 */

public class VaxInfo {
    /**
     * The name of this vaccinated citizen.
     */
    private String name;
    /**
     * The surname of this vaccinated citizen.
     */
    private String surname;
    /**
     * The "Codice Fiscale" of this vaccinated citizen.
     */
    private String ccf;
    /**
     * The vaccination date of this citizen.
     * <p>Precision up to days is used.<br>
     */
    private LocalDate date;
    /**
     * The type of vaccine.
     */
    private VaxType type;
    /**
     * The unique vaccination identifier.
     * <p>A 16 digits unsigned number.
     */
    private long uID;
    /**
     * A {@link HashMap} containing the events reported by this citizen.
     * <p>Only rported events are stored in this map.<br>
     * The size of the map is calculated from the number of {@link EventType}, such that no rehashing will ever occur.
     */
    private HashMap<EventType, Event> events = new HashMap<>((int) Math.ceil(EventType.values().length / 0.8) + 1);

    /**
     * Class constructor. All string parameters are trimmed.
     *
     * @param name    the name of the person
     * @param surname the surname of the person
     * @param ccf     the "codice fiscale" of the person
     * @param date    the vaccination date
     * @param type    the vaccination type
     * @param uID     the unique vaccination id
     */
    public VaxInfo(String name, String surname, String ccf, LocalDate date, VaxType type, long uID) {
        this.name = name.trim();
        this.surname = surname.trim();
        this.ccf = ccf.trim();
        this.date = date;
        this.type = type;
        this.uID = uID;
    }

    /**
     * Returns the 16 digits unique vaccination ID associated with the person
     *
     * @return the unique vaccination ID
     */
    public Long getuID() {

        return uID;
    }

    /**
     * @return A CSV row representation of this object with data mapped as follows:<br>
     * 0: {@link #name}<br>
     * 1: {@link #surname}<br>
     * 2: {@link #ccf}<br>
     * 3: {@link #date}<br>
     * 4: {@link #type}<br>
     * 5: {@link #uID}<br>
     * 6+: {@link #events} with properties as follows:<br>
     * 0: type<br>
     * 1: intensity<br>
     * 2: report<br>
     */
    public String[] toRow() {
        //6 fixed properties + 3 properties for each event
        String[] info = new String[6 + events.size() * 3];
        info[0] = name;
        info[1] = surname;
        info[2] = ccf;
        info[3] = date.toString();
        info[4] = type.toString();
        info[5] = Long.toString(uID);
        //Add events: 3 properties per event (type ,intensity ,report)
        int offset = 6;
        for (Event event : events.values()) {
            String[] row = event.toRow();
            System.arraycopy(row, 0, info, offset, row.length);
            offset += 3;
        }
        return info;
    }

    /**
     * Creates a new vaxinfo object from a csv row.
     *
     * @param row The row to get the data from, indexes mapped as follows:<br>
     *            0: {@link #name}<br>
     *            1: {@link #surname}<br>
     *            2: {@link #ccf}<br>
     *            3: {@link #date}<br>
     *            4: {@link #type}<br>
     *            5: {@link #uID}<br>
     *            6+: {@link #events} with properties as follows:<br>
     *            0: type<br>
     *            1: intensity<br>
     *            2: report<br>
     */

    public VaxInfo(String[] row) {
        name = row[0];
        surname = row[1];
        ccf = row[2];
        date = LocalDate.parse(row[3]);
        type = VaxType.fromString(row[4]);
        uID = Long.parseLong(row[5]);
        //Get events: each event has 3 properties
        for (int i = 6; i < row.length; i += 3) {
            Event event = new Event(Arrays.copyOfRange(row, i, i + 3));
            events.put(event.getType(), event);
        }
    }

    /**
     * Adds a new event to this vaccination information.
     *
     * @param e The event to add
     * @return False if an event of type <code>e.getType()</code> already exists
     */
    public boolean addEvent(Event e) {
        //Check if event exists
        if (events.containsKey(e.getType()))
            return false;
        events.put(e.getType(), e);
        return true;
    }
}
