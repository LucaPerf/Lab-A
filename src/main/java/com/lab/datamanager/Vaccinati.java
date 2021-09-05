package com.lab.datamanager;

import com.lab.data.VaxInfo;
import org.simpleflatmapper.lightningcsv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * This class manages vaccination data of all centers.
 * <p> Data for each center is stored into "centerName_Vaccinati.csv".
 * It is therefore needed to load data dynamically when an operation is required.
 * This i sdone via {@link Vaccinati#load(String)}.<br>
 * The header contains the number of information contained in the file.<br>
 * Data is loaded into a {@link LinkedHashMap} with username as key, to improve iteration performance.
 *
 * @author Luca Perfetti
 * @author Luigi Ciceri
 */

public class Vaccinati extends Data {
    /**
     * Private contructor to avoid class instantiation.
     */
    private Vaccinati() {
    }

    /**
     * Stores vaccination information by unique vaccination id.
     */
    public static LinkedHashMap<Long, VaxInfo> vaxinfo = new LinkedHashMap<>();

    /**
     * Loads all vaccination information of <code>centerName<code/> into {@link #vaxinfo}
     * <p>A new map instance is created with the correct size, parsed from the header.
     *
     * @param centerName The name of the center whose vaccination data will be loaded
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load(String centerName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFileFromCenter(centerName), StandardCharsets.UTF_8))) {
            //Read size
            int size = readHeader(reader, 1)[0];
            vaxinfo = new LinkedHashMap<>(getMapSize(0.75f, size));
            //Read files
            Iterator<String[]> iter = CsvParser.iterator(reader);
            while (iter.hasNext()) {
                String[] row = iter.next();
                vaxinfo.put(Long.parseLong(row[5]), new VaxInfo(row));
            }
        }
    }

    /**
     * Saves all vaccination information into the file Vaccinati_<code>centerName</code>.csv
     * <p>This is needed because specific parts of the file cannot be overwritten without saving all data again (for example when reporting a new event).<br>
     * The header remains untouched.
     *
     * @throws IOException If data could not be saved to the file for any reason
     */
    public static void save(String centerName) throws IOException {
        try (RandomAccessFile rFile = new RandomAccessFile(getFileFromCenter(centerName), "rw");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rFile.getFD()), StandardCharsets.UTF_8))) {
            //Delete everything after header as we are overwriting data
            rFile.setLength(11);
            //Go to file end
            rFile.seek(11);
            //Save centers
            CsvWriter cw = CsvWriter.dsl().to(writer);
            for (VaxInfo vax : vaxinfo.values())
                cw.appendRow(vax.toRow());
        }
    }

    /**
     * Adds a vaccination information.
     * <p>Data is saved directly to the file "<code>centerName</code>_Vacinati.csv".<br>
     * In this case the header must be updated with the new size because data is not necessarily loaded into {@link #vaxinfo}.
     *
     * @param centerName The name of the center to add the information to
     * @param info       The vaccination information to add
     * @throws IOException If data could not be added to the file for any reason
     */
    public static void add(String centerName, VaxInfo info) throws IOException {
        //We have to update the old size as we have not loaded data into the HashMap
        //Read size
        int newSize;
        try (FileReader reader = new FileReader(getFileFromCenter(centerName), StandardCharsets.UTF_8)) {
            newSize = readHeader(reader, 1)[0];
            newSize++;
        }
        //Write new size and info
        try (RandomAccessFile rFile = new RandomAccessFile(getFileFromCenter(centerName), "rw");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rFile.getFD()), StandardCharsets.UTF_8))) {
            writeHeader(writer, newSize);
            rFile.seek(rFile.length());
            //Save info
            CsvWriter cw = CsvWriter.dsl().to(writer);
            cw.appendRow(info.toRow());
        }
    }

    /**
     * @param centerName The name of the center.
     * @return A {@link File} whose name follows the naming convention <code>centerName</code>_Vaccinati.csv.
     */
    public static File getFileFromCenter(String centerName) {
        return new File(dataDirectory, "Vaccinati_" + centerName + ".csv");
    }

    /**
     * Searches for vaccination information by unique id.
     *
     * @param uID Unique id associated with a vaccinated person
     * @return The {@link VaxInfo} associated with <code>uID</code> or null if no such information exists
     */
    public static VaxInfo find(long uID) {
        return vaxinfo.get(uID);
    }

    /**
     * Creates an empty vaccination file for the center <code>centerName</code> if and only if it doesn't exist, named Vaccinati_<code>centerName</code>.csv.
     * <p>The first line contains the number of vaccinated users.
     * This method uses {@link File#createNewFile()}.<br>
     * {@link BufferedOutputStream} is not used as only a small amount of data has to be written.
     *
     * @param centerName The name of the center to create the file of
     * @throws IOException If the file could not be created for any reason
     */
    public static boolean createNewFile(String centerName) throws IOException {
        File newFile = getFileFromCenter(centerName);
        if (newFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(newFile, StandardCharsets.UTF_8)) {
                writeHeader(writer, 0);
            }
            return true;
        }
        return false;
    }
}
