package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This enumerator represents adverse events
 * <p>A pattern allows the enumerator to convert to and from string easily.<br>
 * {@link Object#toString()} methods are overridden to provide a nicely formatted text.
 *
 * @author Luca Perfetti
 * @author Luigi Ciceri
 */

public enum EventType {

    HEADACHE {
        @Override
        public String toString() {
            return "Mal di testa";
        }
    },

    FEVER {
        @Override
        public String toString() {
            return "Febbre";
        }
    },

    ARTPAIN {
        @Override
        public String toString() {
            return "Dolori muscolari e articolari";
        }
    },

    LINF {
        @Override
        public String toString() {
            return "Linfoadenopatia";
        }
    },

    TACH {
        @Override
        public String toString() {
            return "Tachicardia";
        }
    },

    HYPER {
        @Override
        public String toString() {
            return "Crisi ipertensiva";
        }
    };

    /**
     * Maps {@link EventType} to a {@link String} representing its name.
     */
    private static final Map<String, EventType> EVENT_TYPE_MAP;

    static {
        Map<String, EventType> map = new HashMap<>();
        for (EventType type : EventType.values())
            map.put(type.toString().toLowerCase(), type);
        EVENT_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * @param name The enum string representation
     * @return An {@link EventType} which corresponds to <code>name</code> or null if no such enumerator exists.
     */
    public static EventType fromString(String name) {
        return EVENT_TYPE_MAP.get(name.toLowerCase());
    }
}
