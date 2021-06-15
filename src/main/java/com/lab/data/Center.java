package com.lab.data;

/**
 *
 * this class represents information about a vaccination center.
 *
 * @author Luca Perfetti
 */

public class Center {
    public String getName() {
        return name;
    }

    private String name;

    public PostalAddress getAddress() {
        return address;
    }

    private PostalAddress address;

    public CenterType getType() {
        return type;
    }

    private CenterType type;

    public Center(String name, PostalAddress address, CenterType type){
        this.name = name;
        this.address = address;
        this.type = type;
    }
}
