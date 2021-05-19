package com.lab.data;

/**
 *
 * this class represents the address information of the vaccination center.
 *
 * @author Luca Perfetti
 */

public class PostalAddress {
    private String via;
    private String nome;
    private Integer number;
    private String comune;
    private String provincia;
    private Integer cap;

    public PostalAddress(String via, String nome, Integer number, String comune, String provincia, Integer cap){
        this.via = via;
        this.nome = nome;
        this.number = number;
        this.comune = comune;
        this.provincia = provincia;
        this.cap = cap;
    }
}
