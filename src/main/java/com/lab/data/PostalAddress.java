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

    public String getIndirizzo() {

        return indirizzo;
    }

    public String getComune() {

        return comune;
    }

    public String getProvincia() {

        return provincia;
    }

    public Integer getCap() {

        return cap;
    }

    public PostalAddress(String indirizzo, String comune, String provincia, Integer cap){
        this.indirizzo = indirizzo.trim();
        this.comune = comune.trim();
        this.provincia = provincia.trim();
        this.cap = cap;
    }

    public String[] ArrayAddress(){
        return new String[]{indirizzo, provincia, comune, cap.toString()};
    }
}
