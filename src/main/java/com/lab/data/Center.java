package com.lab.data;

/**
 *
 * this class represents information about a vaccination center.
 *
 * @author Luca Perfetti
 */

public class Center {
    private String name;
    private PostalAddress address;
    private CenterType type;

    public Center(String name, PostalAddress address, CenterType type){
        this.name = name;
        this.address = address;
        this.type = type;
    }
}
