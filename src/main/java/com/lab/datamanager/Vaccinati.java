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

public class Vaccinati {
    public static LinkedHashMap<Integer, VaxInfo> vaxinfo = new LinkedHashMap<>();

    /**
     * Checks if vaccination files exist and creates them.
     */
    public static void load() {
        Collection<LinkedList<Center>> data = Centri.getCenters().values();

        try {
            for (LinkedList<Center> it : data)
                for (Center l : it) {
                    File fl = getFileFromCenter(l.getName());
                    fl.createNewFile();
                }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Loads all vaccination information of <code>centerName<code/> into {@link #vaxinfo}
     *
     * @param centerName The name of the center whose vaccination data will be loaded
     */
    public static void load(String centerName) {
        try {
            FileReader fr = new FileReader(getFileFromCenter(centerName));

            Iterator<String[]> iter = CsvParser.iterator(fr);

            while (iter.hasNext()) {
                String[] row = iter.next();

                vaxinfo.put(Integer.parseInt(row[5]), new VaxInfo(row));
            }

            fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Save all vaccination information to the file Vaccinati_centerName.csv
     *
     * @param uID Unique id associated with a vaccinated person
     * @param centerName The name of the center to add the information of vaccinated
     */

    public static void save(Integer uID, String centerName) {
        // Get the information of the vaccinated
        VaxInfo vi = vaxinfo.get(uID);

        //Add the citizen's information to the file "Vaccinati_centerName.csv"
        Vaccinati.add(centerName, vi);
    }

    /**
     * Add the vaccination information  to the file "Vaccinati_ceneterName.csv"
     *
     * @param centerName The name of the center to add the vaccination to
     * @param info       The vaccination information
     */
    public static void add(String centerName, VaxInfo info) {
        try {
            FileWriter fw = new FileWriter(getFileFromCenter(centerName), true);
            CsvWriter cw = CsvWriter.dsl().to(fw);

            String[] columns = info.toRow();

            cw.appendRow(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Creates a file from centerName
    public static File getFileFromCenter(String centerName) {
        return new File("Vaccinati_" + centerName + ".csv");
    }

    /**
     * Searches for the citizen by means of unique id and return his information if he exists else returns null
     *
     * @param uID Unique id associated with a vaccinated person
     */
    public static VaxInfo find(Integer uID){
        Collection<VaxInfo> rt = vaxinfo.values();

        for (VaxInfo it : rt)
            if(uID.equals(it.getuID()))
                return it;

        return null;
    }
}
