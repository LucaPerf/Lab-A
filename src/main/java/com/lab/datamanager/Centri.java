package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.CenterType;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.util.*;
import java.io.*;

/**
 * Class used for center data management.
 * Centers data is stored into a LinkedHashMap with the comune name as key, and a LinkedList as values. In this way centers are grouped by comune.
 * This class provides static methods to load and save centers to a CSV file, named "CentriVaccinali.csv".
 *
 * @author Luigi Ciceri
 **/

public class Centri extends Data{
    private static File file = new File(dataDirectory,"CentriVaccinali.csv");
    private static LinkedHashMap<String, LinkedList<Center>> centers = new LinkedHashMap<>();

    /**
     * Adds a center to the LinkedHashMap, using the comune as key
     *
     * @param center The center to add
     */

    //Adds center to the centers LinkedHashMap
    private static void addCenter(Center center) {

        addToHashMapByComune(center);
    }

    /**
     * Adds a center to the LinkedHashMap by putting it into its LinkedList which is associated to the center own comune.
     * Keys are lowercase.
     *
     * @param center The center to add
     */
    private static void addToHashMapByComune(Center center) {
        String comune = center.getAddress().getDistrict().toLowerCase(Locale.ROOT);

        //if the comune is there, add the center to the list
        if (centers.containsKey(comune))
            centers.get(comune).add(center);

            //if there is no comune, create a new list then add the center and put the list into the HashMap
        else {
            LinkedList<Center> l = new LinkedList<>();
            l.add(center);
            centers.put(comune, l);
        }
    }


    /**
     * Writes a center to the file, in CSV format
     *
     * @param center The center to write
     */
    public static void saveCenter(Center center) {
        //Add to the HashMap
        addCenter(center);

        //Write to file
        try {
            //Open file
            FileWriter fw = new FileWriter(file, true);
            CsvWriter writer = CsvWriter.dsl().to(fw);

            //Write row
            writer.appendRow(center.toRow());
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Method used to read the data from the file and insert it into the LinkedHashMap
     */
    public static void load() {
        try {
            //Create the file if it does not exist
            file.createNewFile();

            FileReader fr = new FileReader(file);
            //Create iterator
            Iterator<String[]> iter = CsvParser.iterator(fr);

            //Parse all rows
            while (iter.hasNext()) {
                //Parse one row which equals to one center object
                String[] row = iter.next();

                //Create the object
                Center centro = new Center(row);

                //Add to LinkedHashMap with  a list of centers for each comune
                addToHashMapByComune(centro);
            }

            fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @return A {@link LinkedHashMap} of {@link Center} containing all centers grouped by comune
     */
    public static LinkedHashMap<String, LinkedList<Center>> getCenters() {

        return centers;
    }

    /**
     * @param centerName The name of the center to search for
     * @return A list of {@link Center} whose names contain <code>nomeCentro</code> (case-insensitive)
     */

    public static LinkedList<Center> find(String centerName) {
        LinkedList<Center> cent = new LinkedList<>();
        String key = centerName.toLowerCase(Locale.ROOT);
        String name;

        //scroll through the list of centers and check if "centerName" is the same as elements in the Hashmap
        for (LinkedList<Center> it : centers.values())
            for (Center l : it) {
                name = l.getName().toLowerCase(Locale.ROOT);
                if (name.contains(key))
                    cent.add(l);
            }
        return cent;
    }

    /**
     * @param type   The type of center to search for
     * @param comune The name of the comune to search in
     * @return A list of {@link Center} located in <code>comune</code>, of <code>type</code> (case-insensitive)
     */
    public static LinkedList<Center> find(CenterType type, String comune) {
        LinkedList<Center> ll = new LinkedList<>();
        String key = comune.toLowerCase(Locale.ROOT);

        if (centers.containsKey(key)) {
            for (Center fr : centers.get(key))
                if (type.equals(fr.getType()))
                    ll.add(fr);
        }
        return ll;
    }
}
