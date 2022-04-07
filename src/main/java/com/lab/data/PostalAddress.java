package com.lab.data;

/**
 * This class represents a postal address.
 * <p>Street address, district, province and CAP are stored.<br>
 * Getters and setters for required variables are provided as well as methods and construcotrs needed to easily convert objects to and from CSV rows.
 */

public class PostalAddress {
    /**
     * The street address
     */
    private String street;
    /**
     * The district this address is in.
     * <p>The italian for district is "comune".
     */
    private String district;
    /**
     * The province this address is in
     */
    private String province;
    /**
     * The CAP (Codice Avviamento Postale) of this address
     */
    private int cap;

    /**
     * @return The street address
     */
    public String getStreet() {

        return street;
    }

    /**
     * @return The district
     */
    public String getDistrict() {

        return district;
    }

    /**
     * @return The province
     */
    public String getProvince() {

        return province;
    }

    /**
     * @return The CAP
     */
    public Integer getCap() {

        return cap;
    }

    /**
     * Class constructor. All string arguments are trimmed.
     *
     * @param address  The street address
     * @param city     The district
     * @param province The province
     * @param cap      The CAP
     */
    public PostalAddress(String address, String city, String province, int cap) {
        this.street = address.trim();
        this.district = city.trim();
        this.province = province.trim();
        this.cap = cap;
    }

    /**
     * Creates an object from a CSV row.
     * <p>Each element of the <code>row</code>> is a cell. Indexes are mapped as follows:<br>
     * 0: {@link #cap}<br>
     * 1: {@link #district}<br>
     * 2: {@link #street}<br>
     * 3: {@link #province}
     *
     * @param row The row to get data from
     */
    public PostalAddress(String[] row) {
        cap = Integer.parseInt(row[0]);
        district = row[1];
        street = row[2];
        province = row[3];
    }

    /**
     * @return A CSV row representation of this object.
     * <p>Each element is a cell. Indexes are mapped as follows:<br>
     * 0: {@link #cap}<br>
     * 1: {@link #district}<br>
     * 2: {@link #street}<br>
     * 3: {@link #province}
     */
    public String[] toRow() {
        return new String[]{Integer.toString(cap), district, street, province};
    }
}
