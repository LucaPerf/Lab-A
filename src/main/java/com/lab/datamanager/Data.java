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

}
