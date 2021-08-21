package com.lab.data;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Date;

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
    private Integer uID;

    /**
     * Class constructor
     *
     * @param name    the name of the person
     * @param surname the surname of the person
     * @param ccf     the "codice fiscale" of the person
     * @param date    the vaccination date
     * @param type    the vaccination type
     * @param uID     the unique vaccination id
     */
    public VaxInfo(String name, String surname, String ccf, LocalDate date, VaxType type, Integer uID) {
        this.name = name;
        this.surname = surname;
        this.ccf = ccf;
        this.date = date;
        this.type = type;
        this.uID = uID;
    }

    /**
     * Returns the 16 bit unique vaccination ID associated with the person
     *
     * @return the unique vaccination ID
     */
    public Integer getuID() {

        return uID;
    }

    public String[] toRow() {
        return new String[]{
                name, surname, ccf, date.toString(), type.toString(), uID.toString()
        };
    }

    /**
     * Creates a new vaxinfo object from a csv row.
     *
     * @param row The row to get the data from, indexes mapped as follows:
     *            0: name
     *            1: surname
     *            2: ccf
     *            3: date
     *            4: type
     *            5: uID
     * @throws IllegalArgumentException If the center uID is not valid
     * @author Luca Perfetti
     */

    public VaxInfo(String[] row) throws IllegalArgumentException {
        name = row[0];
        surname = row[1];
        ccf = row[2];
        date = LocalDate.parse(row[3]);
        type = VaxType.fromString(row[4]);
        uID = Integer.parseInt(row[5]);
    }
}
