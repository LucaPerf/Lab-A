package com.lab.datamanager;

import com.lab.data.Center;
import com.lab.data.CenterType;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.util.*;
import java.io.*;

import com.google.common.collect.LinkedListMultimap;

/**
 * Class used for center data management.
 * Centers data is stored into a LinkedHashMap with the comune name as key, and a LinkedList as values. In this way centers are grouped by comune.
 * This class provides static methods to load and save centers to a CSV file, named "CentriVaccinali.csv".
 *
 * @author Luigi Ciceri
 **/

public class Centri extends Data {
    private Centri() {
    }

    private static File file = new File(dataDirectory, "CentriVaccinali.csv");
    private static LinkedListMultimap<String, Center> centers = LinkedListMultimap.create();

    /**
     * Adds a center to the LinkedHashMap, using the comune as key
     *
     * @param center The center to add
     * @throws IOException If data cannot be added to the file for any reason
     */

    //Adds center to the centers LinkedHashMap
    public static void add(Center center) throws IOException {
        centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
        //This will close the file wheter an axception is thrown or not
        try (FileWriter fw = new FileWriter(file, true)) {
            CsvWriter writer = CsvWriter.dsl().to(fw);
            writer.appendRow(center.toRow());
            Vaccinati.createNewFile(center.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Writes a center to the file, in CSV format
     *
     * @throws IOException If data cannot be saved to the file for any reason
     */
    public static void save() throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            CsvWriter cw = CsvWriter.dsl().to(fw);
            for (Center center : centers.values())
                cw.appendRow(center.toRow());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Method used to read the data from the file and insert it into the LinkedListMultiMap. It also creates the vaccination file for each center if it doesn't exist.
     *
     * @throws IOException If data cannot be loaded from the file for any reason
     */
    public static void load() throws IOException {
        //Try to create the file
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        //Actual file reading
        try (FileReader fr = new FileReader(file)) {
            Iterator<String[]> iter = CsvParser.iterator(fr);
            //Parse all rows
            while (iter.hasNext()) {
                String[] row = iter.next();
                Center center = new Center(row);
                centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
                Vaccinati.createNewFile(center.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @return A {@link LinkedListMultimap} of {@link Center} containing all centers grouped by comune
     */
    public static LinkedListMultimap<String, Center> getCenters() {
        return centers;
    }

    /**
     * @param centerName The name of the center to search for
     * @return A list of {@link Center} whose names contain <code>nomeCentro</code> (case-insensitive)
     */

    public static LinkedList<Center> find(String centerName) {
        LinkedList<Center> list = new LinkedList<>();
        String key = centerName.toLowerCase(Locale.ROOT);
        String name;

        //scroll through the list of centers and check if "centerName" is the same as elements in the Hashmap
        for (Center center : centers.values()) {
            name = center.getName().toLowerCase(Locale.ROOT);
            if (name.contains(key))
                list.add(center);
        }
        return list;
    }

    /**
     * @param type   The type of center to search for
     * @param comune The name of the comune to search in
     * @return A list of {@link Center} located in <code>comune</code>, of <code>type</code> (case-insensitive)
     */
    public static LinkedList<Center> find(CenterType type, String comune) {
        LinkedList<Center> list = new LinkedList<>();
        String key = comune.toLowerCase(Locale.ROOT);

        if (centers.containsKey(key)) {
            for (Center fr : centers.get(key))
                if (type.equals(fr.getType()))
                    list.add(fr);
        }
        return list;
    }

    /**
     * @param n The number of centers to add to the list. The order is guaranteed by the {@link LinkedListMultimap} implementation
     * @return A {@link LinkedList} containing the last <code>n</code> registered centers
     */
    public static LinkedList<Center> getRecent(int n) {
        LinkedList<Center> list = new LinkedList<>();

        int i = 0;
        for (Center center : centers.values()) {
            if (i < n) {
                list.addFirst(center);
                i++;
            } else break;
        }
        return list;
    }
}
