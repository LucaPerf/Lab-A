package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * this enumerator represents a center type.
 *
 * @author Luca Perfetti
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

    private static final Map<String, CenterType> CENTER_TYPE_MAP;

    static {
        Map<String, CenterType> map = new HashMap<>();
        for (CenterType type : CenterType.values())
            map.put(type.toString().toLowerCase(), type);
        CENTER_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    public static CenterType fromString(String name) {
        return CENTER_TYPE_MAP.get(name.toLowerCase());
    }
}
