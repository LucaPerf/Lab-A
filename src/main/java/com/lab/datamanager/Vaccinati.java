package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.VaxInfo;
import org.simpleflatmapper.lightningcsv.CsvParser;
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
    public static LinkedHashMap<String, File> files = new LinkedHashMap<>();

    /**
     * Create a HashMap of all files in the center
     *
     */

    public static void load() {
        Collection<LinkedList<Center>> data = Centri.getCenters().values();

        try {
            for(LinkedList<Center> it : data)
                for(Center l : it) {
                    File fl = new File("Vaccinati " + l.getName() + ".csv");
                    fl.createNewFile();
                    files.put(l.getName(), fl);
                }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     *Upload the file specified file in the center we need
     *
     * @param centerName
     * @throws IOException
     */
    public static void load(String centerName) {
       try{
           FileReader fr = new FileReader(files.get(centerName));

           Iterator<String[]> iter = CsvParser.iterator(fr);

           while(iter.hasNext()){
               String[] row = iter.next();

               vaxinfo.put(Integer.parseInt(row[5]), new VaxInfo(row));
           }
       }catch (IOException e){
           System.out.println(e);
       }
    }

    /**
     * Save the center name and vaccine information in a file
     *
     * @param centerName
     * @param info
     */

    public static void save(String centerName, VaxInfo info){

    }

    /**
     * Add the center name and vaccine information to the HashMap named "vaccini.csv"
     *
     * @param centerName
     * @param info
     */
    public static void add(String centerName, VaxInfo info){
        try{
            FileWriter fw = new FileWriter(files.get(centerName), true);
            CsvWriter cw = CsvWriter.dsl().to(fw);

            String[] columns = info.toRow();

            cw.appendRow(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
            fw.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
