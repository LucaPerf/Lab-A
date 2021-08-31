package com.lab.datamanager;

import com.google.common.base.Strings;
import com.lab.data.VaxInfo;
import org.simpleflatmapper.lightningcsv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * This class manages vaccination data of all centers.
 * Each center data is saved into a file named Vaccinati_centerName.csv
 *
 * @author Luca Perfetti
 */

public class Vaccinati extends Data {
    private Vaccinati() {
    }

    public static LinkedHashMap<Long, VaxInfo> vaxinfo = new LinkedHashMap<>();

    /**
     * Loads all vaccination information of <code>centerName<code/> into {@link #vaxinfo}
     *
     * @param centerName The name of the center whose vaccination data will be loaded
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load(String centerName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFileFromCenter(centerName)))) {
            //Read size
            char[] size = new char[10];
            reader.read(size, 0, 10);
            int mapSize = Integer.parseInt(new String(size));
            vaxinfo = new LinkedHashMap<>(mapSize);
            reader.skip(1);

            //Read files
            Iterator<String[]> iter = CsvParser.iterator(reader);
            while (iter.hasNext()) {
                String[] row = iter.next();
                vaxinfo.put(Long.parseLong(row[5]), new VaxInfo(row));
            }
        }
    }

    /**
     * Save all vaccination information to the file Vaccinati_centerName.csv
     *
     * @throws IOException If data could not be saved to the file for any reason
     */
    public static void save(String centerName) throws IOException {
        try (RandomAccessFile rFile = new RandomAccessFile(getFileFromCenter(centerName), "rw");
             BufferedWriter writer = new BufferedWriter(new FileWriter(rFile.getFD()))) {
            //Delete everything after header as we are overwriting data
            rFile.setLength(11);
            rFile.seek(11);

            //Save centers
            CsvWriter cw = CsvWriter.dsl().to(writer);
            for (VaxInfo vax : vaxinfo.values())
                cw.appendRow(vax.toRow());
        }
    }

    /**
     * Add the vaccination information  to the file "Vaccinati_ceneterName.csv"
     *
     * @param centerName The name of the center to add the vaccination to
     * @param info       The vaccination information
     * @throws IOException If data could not be added to the file for any reason
     */
    public static void add(String centerName, VaxInfo info) throws IOException {
        //We have to update the old size as we have not loaded data into the HashMap
        //Read size
        int newSize;
        try (FileReader reader = new FileReader(getFileFromCenter(centerName))) {
            //Read
            char[] size = new char[10];
            reader.read(size, 0, 10);
            newSize = Integer.parseInt(new String(size)) + 1;
        }
        //Write new size and info
        try (RandomAccessFile rFile = new RandomAccessFile(getFileFromCenter(centerName), "rw");
             BufferedWriter writer = new BufferedWriter(new FileWriter(rFile.getFD()))) {
            String size = Integer.toString(newSize);
            writer.write(Strings.padStart(size, 10, '0'));
            writer.flush();
            rFile.seek(rFile.length());

            //Info
            CsvWriter cw = CsvWriter.dsl().to(writer);
            cw.appendRow(info.toRow());
        }
    }

    //Creates a file from centerName
    public static File getFileFromCenter(String centerName) {
        return new File(dataDirectory, "Vaccinati_" + centerName + ".csv");
    }

    /**
     * Searches for the citizen by means of unique id and return his information if he exists else returns null
     *
     * @param uID Unique id associated with a vaccinated person
     */
    public static VaxInfo find(Long uID) {
        return vaxinfo.get(uID);
    }

    /**
     * Creates an empty vaccination file for the center <code>centerName</code> if and only if it doesn't exist, named Vaccinati_<code>centerName</code>.csv.
     * The first line contains the number of vaccinated users.
     * This method uses {@link File#createNewFile()}.
     *
     * @param centerName The name of the center to create the file of
     * @throws IOException If the file could not be created
     */
    public static boolean createNewFile(String centerName) throws IOException {
        File newFile = getFileFromCenter(centerName);
        if (newFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(newFile)) {
                writer.write("0000000000\n");
            }
            return true;
        }
        return false;
    }
}
