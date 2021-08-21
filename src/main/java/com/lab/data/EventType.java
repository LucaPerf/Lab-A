package com.lab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This enumerator represents adverse events
 *
 * @author Luca Perfetti
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

    private static final Map<String, EventType> EVENT_TYPE_MAP;

    static {
        Map<String, EventType> map = new HashMap<>();
        for (EventType type : EventType.values())
            map.put(type.toString().toLowerCase(), type);
        EVENT_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    public static EventType fromString(String name) {
        return EVENT_TYPE_MAP.get(name.toLowerCase());
    }
}
