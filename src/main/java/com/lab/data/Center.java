package com.lab.data;

/**
 *
 * this class represents information about a vaccination center.
 *
 * @author Luca Perfetti
 */

public class Center{
    private String name;
    private PostalAddress address;
    private CenterType type;

    public String getName() {

        return name;
    }

    public PostalAddress getAddress() {

        return address;
    }

    public CenterType getType() {

        return type;
    }

    public Center(String name, PostalAddress address, CenterType type){
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String[] csvColumns(){
        return new String[]{name, address.getCap().toString(), address.getComune(), address.getIndirizzo(), address.getProvincia(), type.toString()};
    }

    public String[] ArrayCenter(){
            return new String[]{name, address.toString(), type.toString()};
    }
}
