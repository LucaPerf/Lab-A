package com.lab.data;

/**
 *
 * this enumerator represents a center type.
 *
 * @author Luca Perfetti
 */

public enum CenterType {
    OSPEDALIERO{
        @Override
        public String toString(){
            return "Ospedaliero";
        }
    },
    AZIENDALE{
        @Override
        public String toString() {
            return "Aziendale";
        }
    },
    HUB{
        @Override
        public String toString() {
            return "Hub";
        }
    },
}
