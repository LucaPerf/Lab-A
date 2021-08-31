package com.lab.datamanager;

import java.io.File;

/**
 * Base class for all data managed by the application.
 */
public abstract class Data {
    protected static File dataDirectory = new File("data");

    /**
     * Creates the "data" folder where all data will be placed f and only if it doesn't exist.
     */
    public static void createDirectory() {
        if (!dataDirectory.exists())
            dataDirectory.mkdirs();
    }

    /**
     * @param loadFactor     The desired load factor
     * @param expectedValues How many values the map is expected to hold
     * @return The size a map should have to avoid rehashing if <code>expectedValues</code> are to be inserted
     */
    protected static int getMapSize(float loadFactor, int expectedValues) {
        return (int) (Math.ceil(expectedValues / loadFactor) + 1);
    }
}
