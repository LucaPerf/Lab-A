package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.VaxInfo;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.*;

/**
 *
 * This class represents the reading of the data of the centers and loads them in the Hashmap, creating a file for each read center
 *
 * @author Luca Perfetti
 */

public class Vaccinati {
    public static LinkedHashMap<Integer, VaxInfo> vaxinfo = new LinkedHashMap<>();
    public static LinkedHashMap<String, File> file = new LinkedHashMap<>();
    private static Collection<File> fl = file.values();

    /**
     * Create a HashMap of all files in the center
     *
     */

    public static void load() {
        Collection<LinkedList<Center>> data = Centri.getCenters().values();

        for(LinkedList<Center> it : data)
            for(Center l : it)
                System.out.println(file.get(l));
    }

    /**
     *Upload the file specified file in the center we need
     *
     * @param file
     * @throws IOException
     */
    public static void load(File file) throws IOException {
        for(File det : fl)
            System.out.println(det.createNewFile());
    }

    /**
     * Save the center name and vaccine information in a file
     *
     * @param centerName
     * @param info
     */

    public static void save(String centerName, VaxInfo info){
        add(centerName, info);

        try{
            FileWriter fw = new FileWriter((File) fl, true);
            CsvWriter cw = CsvWriter.dsl().to(fw);

            String[] columns = info.toRow();

            cw.appendRow(columns[0],columns[1],columns[2],columns[3], columns[4], columns[5]);
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * Add the center name and vaccine information to the HashMap named "vaccini.csv"
     *
     * @param centerName
     * @param info
     */
    public static void add(String centerName, VaxInfo info){

        vaxinfo.put(Integer.valueOf(centerName), info);
    }
}
