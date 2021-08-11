package com.lab.data;

import java.util.Locale;

/**
 * this class represents information about a vaccination center.
 *
 * @author Luca Perfetti
 */

public class Center {
    private String name;
    private static PostalAddress address;
    private static CenterType type;

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
    public static PostalAddress getAddress() {

        return address;
    }

    /**
     * @return The type of this center
     * @author Luca Perfetti
     */
    public static CenterType getType() {

        return type;
    }

    /**
     * Created a Center object
     *
     * @param name    The name of the center
     * @param address The address of the center
     * @param type    The type of the center
     * @author Luca Perfetti
     */
    public Center(String name, PostalAddress address, CenterType type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }

    /**
     * Creates a new user object from a csv row.
     *
     * @param row The row to get the data from, indexes mapped as follows:
     *            0: name
     *            1: cap
     *            2: comune
     *            3: address
     *            4: provincia
     *            5: type
     * @throws NumberFormatException          If the cap is not a valid number
     * @throws ArrayIndexOutOfBoundsException If the array does not contain 6 elements
     * @throws IllegalArgumentException       If the center type is not valid
     * @author Ciceri Luigi
     */
    public Center(String[] row) throws NumberFormatException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        name = row[0];
        address = new PostalAddress(row[3], row[2], row[4], Integer.parseInt(row[1]));
        type = CenterType.valueOf(row[5].toUpperCase(Locale.ROOT));
    }

    /**
     * @return A string array containing all information about the User object, indexes mapped as follows:
     * 0: name
     * 1: cap
     * 2: comune
     * 3: address
     * 4: provincia
     * 5: type
     * @author Ciceri Luigi
     */
    public String[] toRow() {
        return new String[]{name, address.getCap().toString(), address.getComune(), address.getIndirizzo(), address.getProvincia(), type.toString()};
    }

    /**
     * @return A brief summary of this center
     */
    @Override
    public String toString() {
        return name + "\nCentro " + type.toString().toLowerCase(Locale.ROOT) + ", si trova nel comune di " + address.getComune();
    }
}
