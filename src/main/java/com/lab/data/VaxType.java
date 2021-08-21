package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    };

    private static final Map<String, VaxType> VAX_TYPE_MAP;

    static {
        Map<String, VaxType> map = new HashMap<>();
        for (VaxType type : VaxType.values())
            map.put(type.toString().toLowerCase(), type);
        VAX_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    public static VaxType fromString(String name) {
        return VAX_TYPE_MAP.get(name.toLowerCase());
    }
}