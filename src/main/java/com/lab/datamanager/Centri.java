package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.PostalAddress;
import com.lab.data.CenterType;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.util.*;
import java.io.*;

/**
 * class used for center data management
 *
 * @author Luigi Ciceri
 **/

public class Centri {
    private static LinkedHashMap<String, LinkedList<Center>> centers = new LinkedHashMap<>();

    /**
     * method for adding centers
     *
     * @param center
     */
    private static void addCenter(Center center) {

        addToHashMapByComune(center);
    }

    private static void addToHashMapByComune(Center center) {
        //if the provincia is there, add to the list
        if (centers.containsValue(center.getAddress().getComune()))
            centers.get(center.getAddress().getComune()).add(center);

            //if there is no provincia, create a new list then add the center and put the list into the HashMap
        else {
            LinkedList<Center> l = new LinkedList<>();
            l.add(center);
            centers.put(center.getAddress().getComune(), l);
        }
    }


    /**
     * method to save the center
     *
     * @param center
     */
    public static void saveCenter(Center center) {
        //Add to the HashMap
        addCenter(center);

        //Write to file
        try {
            //Open file
            FileWriter fw = new FileWriter("CentriVaccinali.csv", true);
            CsvWriter writer = CsvWriter.dsl().to(fw);

            //Each element of the array is a cell
            String[] columns = center.csvColumns();

            //Write row
            writer.appendRow(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * method used to read the data from the file and insert it into the LinkedHashMap
     */
    public static void loadCenter(Center center) {
        try {
            FileReader fr = new FileReader("CentriVaccinali.csv");

            //Create iterator
            Iterator<String[]> iter = CsvParser.iterator(fr);

            //Parse all rows
            while (iter.hasNext()) {
                //Parse one row which equals to one center object
                String[] row = iter.next();

                //Create the object
                PostalAddress address = new PostalAddress(row[3], row[2], row[4], Integer.parseInt(row[3]));
                Center centro = new Center(row[0], address, CenterType.valueOf(row[5].toUpperCase(Locale.ROOT)));

                //Add to LinkedHashMap with  a list of centers for each comune
                addToHashMapByComune(center);
            }

            Collection<LinkedList<Center>> data = centers.values();
            for (LinkedList<Center> l : data) {
                for (Center t : l)
                    System.out.println(Arrays.toString(t.csvColumns()));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

