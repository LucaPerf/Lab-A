package com.lab.datamanager;

import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

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

    /**
     * Writes <code>sizes</code> as header. The <code>writer</code> is flushed after writing to apply changes immediately. The <code>writer</code> is advanced after this method is invoked.
     * Argument ints are prefixed with zeros to ensure that their length is exactly 10. A '\n' separator is added after the line.
     *
     * @param writer The writer to use for this operation
     * @param sizes  The header elements to be written
     * @throws IOException If writing fails
     */
    protected static void writeHeader(Writer writer, int... sizes) throws IOException {
        for (int i : sizes) {
            String n = Strings.padStart(Integer.toString(i), 10, '0');
            writer.write(n);
        }
        //Add separator
        writer.write('\n');
        writer.flush();
    }

    /**
     * Reads the header ints into an array. <code>intsHeader</code> are read with <code>reader</code>, each with a length of 10 characters.
     * The order of ints from the file is preserved.
     * The reader is advanced after this method is invoked and the '\n' header separator skipped.
     *
     * @param reader     The reader to use
     * @param headerSize The number of <code>int</code> contained in the header
     * @return An array containing all headers
     * @throws IOException If the header could not be read for any reason
     */
    protected static int[] readHeader(Reader reader, int headerSize) throws IOException {
        int[] read = new int[headerSize];
        char[] buffer = new char[10];
        for (int i = 0; i < headerSize; i++) {
            reader.read(buffer, 0, 10);
            read[i] = Integer.parseInt(new String(buffer));
        }
        //Skip separator
        reader.skip(1);
        return read;
    }
}
