package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This enumerator represents a center type.
 * <p>A pattern allows the enumerator to convert to and from string easily.<br>
 * {@link Object#toString()} methods are overridden to provide a nicely formatted text.
 *
 * @author Luigi Ciceri
 */

public enum CenterType {
    OSPEDALIERO {
        @Override
        public String toString() {
            return "Ospedaliero";
        }
    },
    AZIENDALE {
        @Override
        public String toString() {
            return "Aziendale";
        }
    },
    HUB {
        @Override
        public String toString() {
            return "Hub";
        }
    };

    /**
     * Maps {@link CenterType} to a {@link String} representing its name.
     */
    private static final Map<String, CenterType> CENTER_TYPE_MAP;

    /*
     * Static initialization block.
     * Maps enums to their names through a UnmodifiableMap
     */
    static {
        Map<String, CenterType> map = new HashMap<>();
        for (CenterType type : CenterType.values())
            map.put(type.toString().toLowerCase(), type);
        CENTER_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * @param name The enum string representation
     * @return An {@link CenterType} which corresponds to <code>name</code> or null if no such enumerator exists.
     */
    public static CenterType fromString(String name) {
        return CENTER_TYPE_MAP.get(name.toLowerCase());
    }
}
