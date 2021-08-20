package com.lab.data;

/**
 * This enumerator represents adverse events
 *
 * @author Luca Perfetti
 */

public enum EventType {

    HEADACHE{
        @Override
        public String toString(){
            return "Mal di testa";
        }
    },

    FEVER{
        @Override
        public String toString(){
            return "Febbre";
        }
    },

    ARTPAIN{
        @Override
        public String toString(){
            return "Dolori muscolari e articolari";
        }
    },

    LINF{
        @Override
        public String toString(){
            return "Linfoadenopatia";
        }
    },

    TACH{
        @Override
        public String toString(){
            return "Tachicardia";
        }
    },

    HYPER{
        @Override
        public String toString(){
            return "Crisi ipertensiva";
        }
    },

}
