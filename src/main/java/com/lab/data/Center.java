package com.lab.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class represents a vaccination center.
 * <p>Methods and constructors are provided for a simple conversion from and to CSV row.<br>
 * This class contains getters and setters for needed variables.<br>
 * A {@link java.util.HashMap} is used to store all types of statistics about the center.
 */

public class Center {
    /**
     * The name of the center
     */
    private String name;
    /**
     * The {@link PostalAddress} of this center
     */
    private PostalAddress address;
    /**
     * The {@link CenterType} of this center
     */
    private CenterType type;
    /**
     * A hashmap containing all {@link Stat} about this center.
     * <p>The stat's name is used as key to allow the addition of custom statistics.
     * The hashmap size is calculated from {@link EventType} possible values. Custom statistics are not taken into account.
     * Default load factor is used.
     */
    private HashMap<String, Stat> stats = new HashMap<>((int) Math.ceil(EventType.values().length / 0.75f) + 1);

    /**
     * @return The name of this center
     */
    public String getName() {

        return name;
    }

    /**
     * @return The {@link PostalAddress} of this center
     */
    public PostalAddress getAddress() {

        return address;
    }

    /**
     * @return The {@link CenterType} of this center
     */
    public CenterType getType() {

        return type;
    }

    /**
     * Creates a Center object with an empty stat for each event type. All string parameters are trimmed.
     *
     * @param name    The name of the center
     * @param address The address of the center
     * @param type    The type of the center
     */
    public Center(String name, PostalAddress address, CenterType type) {
        this.name = name.trim();
        this.address = address;
        this.type = type;
        //Add global stat
        stats.put("Global", new Stat("Global"));
        //Add per-event stats
        for (EventType event : EventType.values())
            stats.put(event.toString(), new Stat(event.toString()));
    }

    /**
     * Creates a new user object from a CSV row.
     * <p>Eacxh item in the array represents a cell.
     * The constructor of {@link PostalAddress} is used.
     *
     * @param row The row to get the data from, indexes mapped as follows:<br>
     *            0: {@link #name}<br>
     *            1: cap<br>
     *            2: comune<br>
     *            3: address<br>
     *            4: provincia<br>
     *            5: {@link #type}
     *            6+: {@link #stats} in groups of 3 elements
     */
    public Center(String[] row) {
        name = row[0];
        address = new PostalAddress(Arrays.copyOfRange(row, 1, 5));
        type = CenterType.fromString(row[5]);
        //Add stats
        for (int i = 6; i < row.length; i += 3) {
            Stat s = new Stat(Arrays.copyOfRange(row, i, i + 3));
            stats.put(s.getName(), s);
        }
    }

    /**
     * @return A string array containing all information about the User object, indexes mapped as follows:<br>
     * 0: {@link #name}<br>
     * 1: cap<br>
     * 2: comune<br>
     * 3: address<br>
     * 4: provincia<br>
     * 5: {@link #type}
     * 6+: {@link #stats} in groups of 3 elements
     */
    public String[] toRow() {
        String[] info = new String[6 + stats.size() * 3];
        info[0] = name;
        System.arraycopy(address.toRow(), 0, info, 1, 4);
        info[5] = type.toString();
        //Add stats
        int offset = 6;
        for (Stat stat : stats.values()) {
            String[] row = stat.toRow();
            System.arraycopy(row, 0, info, offset, row.length);
            offset += 3;
        }
        return info;
    }

    /**
     * @return A brief summary of this center, used by {@link com.jfoenix.controls.JFXListCell}
     */
    @Override
    public String toString() {
        return name + "\nCentro " + type.toString().toLowerCase(Locale.ROOT) + ", si trova nel comune di " + address.getDistrict();
    }

    /**
     * Updates the stat {@link EventType#toString()} with the new <code>event</code>.
     * <p>The global stat will also be updated.
     *
     * @param event The event ot update the stat with
     */
    public void updateStat(Event event) {
        float value = event.getIntensity();
        String key = event.getType().toString();
        //Update global stat
        stats.get("Global").update(value);
        //Update partial stat
        stats.get(key).update(value);
    }

    /**
     * @param name The type of stat to return
     * @return A stat whose name is <code>name</code> or <code>null</code> if no such stat exists
     */
    public Stat getStat(String name) {
        return stats.getOrDefault(name, null);
    }
}
