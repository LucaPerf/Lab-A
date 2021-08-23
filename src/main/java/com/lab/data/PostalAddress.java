package com.lab.data;

/**
 * This class represents a postal address.
 *
 * @author Luca Perfetti
 */

public class PostalAddress {
    private String street;
    private String district;
    private String province;
    private Integer cap;

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
     * @return The "CAP"
     */
    public Integer getCap() {

        return cap;
    }

    /**
     * Creates a class. All string arguments are trimmed.
     *
     * @param address  The street address
     * @param city     The district
     * @param province The province
     * @param cap      The "CAP"
     */
    public PostalAddress(String address, String city, String province, Integer cap) {
        this.street = address.trim();
        this.district = city.trim();
        this.province = province.trim();
        this.cap = cap;
    }
}
