package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This enumerator represents adverse events
 * <p>A pattern allows the enumerator to convert to and from string easily.<br>
 * {@link Object#toString()} methods are overridden to provide a nicely formatted text.
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

    /**
     * Maps {@link VaxType} to a {@link String} representing its name.
     */
    private static final Map<String, VaxType> VAX_TYPE_MAP;

    static {
        Map<String, VaxType> map = new HashMap<>();
        for (VaxType type : VaxType.values())
            map.put(type.toString().toLowerCase(), type);
        VAX_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * @param name The enum string representation
     * @return An {@link VaxType} which corresponds to <code>name</code> or null if no such enumerator exists.
     */
    public static VaxType fromString(String name) {
        return VAX_TYPE_MAP.get(name.toLowerCase());
    }
}