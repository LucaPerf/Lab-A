package com.lab.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class represents information about a vaccination center.
 *
 * @author Luca Perfetti
 */

public class Center {
    private String name;
    private PostalAddress address;
    private CenterType type;
    private HashMap<String, Stat> stats = new HashMap<>((int) Math.ceil(EventType.values().length / 0.8) + 1);

    /**
     * @return The name of this center
     * @author Luca Perfetti
     */
    public String getName() {

        return name;
    }

    /**
     * @return The address of this center
     * @author Luca Perfetti
     */

    // public PostalAddess getAddess(){}
    public PostalAddress getAddress() {

        return address;
    }

    /**
     * @return The type of this center
     * @author Luca Perfetti
     */
    public CenterType getType() {

        return type;
    }

    /**
     * Created a Center object with an empty stat for each event type. All string parameters are trimmed.
     *
     * @param name    The name of the center
     * @param address The address of the center
     * @param type    The type of the center
     * @author Luca Perfetti
     * @author Luigi Cieri
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
     * Creates a new user object from a csv row.
     *
     * @param row The row to get the data from, indexes mapped as follows:<br>
     *            0: name<br>
     *            1: cap<br>
     *            2: comune<br>
     *            3: address<br>
     *            4: provincia<br>
     *            5: type<br>
     *            6+: stats in groups of 3 elements
     * @throws NumberFormatException          If the cap is not a valid number
     * @throws ArrayIndexOutOfBoundsException If the array does not contain 6 elements
     * @throws IllegalArgumentException       If the center type is not valid
     * @author Ciceri Luigi
     */
    public Center(String[] row) throws NumberFormatException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        name = row[0];
        address = new PostalAddress(row[3], row[2], row[4], Integer.parseInt(row[1]));
        type = CenterType.fromString(row[5]);
        //Add stats
        for (int i = 6; i < row.length; i += 3) {
            Stat s = new Stat(Arrays.copyOfRange(row, i, i + 3));
            stats.put(s.getName(), s);
        }
    }

    /**
     * @return A string array containing all information about the User object, indexes mapped as follows:<br>
     * 0: name<br>
     * 1: cap<br>
     * 2: comune<br>
     * 3: address<br>
     * 4: provincia<br>
     * 5: type<br>
     * 6+: stats in groups of 3 elements
     * @author Ciceri Luigi
     */
    public String[] toRow() {
        String[] info = new String[6 + stats.size() * 3];
        info[0] = name;
        info[1] = address.getCap().toString();
        info[2] = address.getDistrict();
        info[3] = address.getStreet();
        info[4] = address.getProvince();
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
     * Updates the stat of with the new <code>event</code>. The global stat will also be updated.
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
     * @return A stat of <code>type</code> or <code>null</code> if no such stat exists
     */
    public Stat getStat(String name) {
        return stats.getOrDefault(name, null);
    }
}
