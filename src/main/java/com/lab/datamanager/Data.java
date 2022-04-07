package com.lab.datamanager;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.lab.ui.Page;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Base class for all data managed by the application.
 */
public abstract class Data {
    static {
        try {
            //Source path
            URL jar = Page.class.getProtectionDomain().getCodeSource().getLocation();
            File source = new File(jar.toURI());
            //Running from jar, parent is two directories above jar as the jar is in /bin
            if (Files.getFileExtension(source.getAbsolutePath()).equals("jar"))
                dataDirectory = new File(source.getParentFile().getParentFile(), "data");
            // Running from gradle, parent is 3 directories above class, as this class is in /build/classes/java/main/com/lab/datamanager
            else
                dataDirectory = new File(source.getParentFile().getParentFile().getParentFile().getParentFile(), "data");
        } catch (SecurityException | URISyntaxException e) {
            dataDirectory = null;
        }
    }

    /**
     * The folder all data will be saved into.
     */
    protected static File dataDirectory;

    /**
     * Creates the {@link #dataDirectory} folder where all data will be placed only if it doesn't exist.
     *
     * @throws FileNotFoundException If the data directory could not be created
     */
    public static void createDirectory() throws FileNotFoundException {
        //Throw exception if directory hadn't been created
        if (dataDirectory == null)
            throw new FileNotFoundException("Could not create data directory.");
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
     * Writes <code>sizes</code> as file header.
     * <p>The <code>writer</code> is flushed after writing to apply changes immediately. The <code>writer</code> is advanced after this method is invoked.
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
     * Reads the header ints into an array.
     * <p><code>intsHeader</code> are read with <code>reader</code>, each with a length of 10 characters.
     * The order of ints from the file is preserved.
     * The reader is advanced after this method is invoked and the '\n' header separator skipped.
     *
     * @param reader     The reader to use
     * @param headerSize The number of <code>int</code> contained in the header
     * @return An array containing all parsed headers
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
