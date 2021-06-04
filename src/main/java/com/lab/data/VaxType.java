package com.lab.data;

/**
 * This enumerator represents a vaccination type.
 * toString() methods are overridden to easily get properly formatted names.
 *
 * @author Ciceri Luigi
 */

public enum VaxType {

    PFIZER {
        @Override
        public String toString() {
            return "Pfizer";
        }
    },
    ASTRAZENECA {
        @Override
        public String toString() {
            return "AstraZeneca";
        }
    },
    MODERNA {
        @Override
        public String toString() {
            return "Moderna";
        }
    },
    JJ {
        @Override
        public String toString() {
            return "Johnson & Johnson";
        }
    }
}