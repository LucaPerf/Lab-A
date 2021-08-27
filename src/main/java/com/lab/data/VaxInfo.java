package com.lab.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class represents information about a vaccinated person.
 *
 * @author Ciceri Luigi
 */

public class VaxInfo {

    private String name;
    private String surname;
    private String ccf;
    private LocalDate date;
    private VaxType type;
    private Long uID;
    //Since we know the number of enums and the default load factor, we can calculate the exact map size
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
    public VaxInfo(String name, String surname, String ccf, LocalDate date, VaxType type, Long uID) {
        this.name = name.trim();
        this.surname = surname.trim();
        this.ccf = ccf.trim();
        this.date = date;
        this.type = type;
        this.uID = uID;
    }

    /**
     * Returns the 16 bit unique vaccination ID associated with the person
     *
     * @return the unique vaccination ID
     */
    public Long getuID() {

        return uID;
    }

    /**
     * @return A CSV row representation of this object with data mapped as follows:<br>
     * 0: name<br>
     * 1: surname<br>
     * 2: ccf<br>
     * 3: date<br>
     * 4: type<br>
     * 5: uID<br>
     * 6+: events with properties as follows:<br>
     * 0: type<br>
     * 1: intensity<br>
     * 2:report<br>
     */
    public String[] toRow() {
        //6 fixed properties + 3 properties for each event
        String[] info = new String[6 + events.size() * 3];
        info[0] = name;
        info[1] = surname;
        info[2] = ccf;
        info[3] = date.toString();
        info[4] = type.toString();
        info[5] = uID.toString();
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
     *            0: name<br>
     *            1: surname<br>
     *            2: ccf<br>
     *            3: date<br>
     *            4: type<br>
     *            5: uID<br>
     *            6+: events with properties as follows:<br>
     *            0: type<br>
     *            1: intensity<br>
     *            2:report<br>
     * @author Luca Perfetti
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
     * Adds a new event to this vaccination information
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
