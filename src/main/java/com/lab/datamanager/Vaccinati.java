package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.VaxInfo;
import org.simpleflatmapper.lightningcsv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.*;

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
     * Checks if vaccination files exist and creates them.
     *
     * @throws IOException If files could not be created for any reason
     */
    public static void load() throws IOException {
        try {
            for (Center l : Centri.getCenters().values()) {
                File fl = getFileFromCenter(l.getName());
                fl.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Loads all vaccination information of <code>centerName<code/> into {@link #vaxinfo}
     *
     * @param centerName The name of the center whose vaccination data will be loaded
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load(String centerName) throws IOException {
        try (FileReader fr = new FileReader(getFileFromCenter(centerName))) {
            Iterator<String[]> iter = CsvParser.iterator(fr);

            while (iter.hasNext()) {
                String[] row = iter.next();
                vaxinfo.put(Long.parseLong(row[5]), new VaxInfo(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Save all vaccination information to the file Vaccinati_centerName.csv
     *
     * @throws IOException If data could not be saved to the file for any reason
     */
    public static void save(String centerName) throws IOException {
        try (FileWriter fw = new FileWriter(getFileFromCenter(centerName))) {
            CsvWriter cw = CsvWriter.dsl().to(fw);
            for (VaxInfo vax : vaxinfo.values())
                cw.appendRow(vax.toRow());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
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
        try (FileWriter fw = new FileWriter(getFileFromCenter(centerName), true)) {
            CsvWriter cw = CsvWriter.dsl().to(fw);
            cw.appendRow(info.toRow());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
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
}
