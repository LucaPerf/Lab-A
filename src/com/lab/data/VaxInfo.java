package com.lab.data;

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
    private Date date;
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
    public VaxInfo(String name, String surname, String ccf, Date date, VaxType type, Integer uID) {
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
}
