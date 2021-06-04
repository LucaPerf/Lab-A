package com.lab.data;

/**
 *
 * this class represents the address information of the vaccination center.
 *
 * @author Luca Perfetti
 */

public class PostalAddress {
    private String indirizzo;
    private String comune;
    private String provincia;
    private Integer cap;

    public PostalAddress(String indirizzo, String comune, String provincia, Integer cap){
        this.indirizzo = indirizzo;
        this.comune = comune;
        this.provincia = provincia;
        this.cap = cap;
    }
}
