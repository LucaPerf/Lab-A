package com.lab.datamanager;

import com.google.common.collect.LinkedListMultimap;
import com.lab.data.Center;
import com.lab.data.CenterType;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

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
    //This is needed only to check if a center exists
    private static HashSet<String> centerNames = new HashSet<>();

    /**
     * Adds a center to the LinkedHashMap, using the comune as key
     *
     * @param center The center to add
     * @throws IOException If data cannot be added to the file for any reason
     */

    //Adds center to the centers LinkedHashMap
    public static void add(Center center) throws IOException {
        centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
        centerNames.add(center.getName());
        //This will close the file whether an exception is thrown or not
        try (RandomAccessFile rFile = new RandomAccessFile(file, "rw");
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(rFile.getFD()))) {
            writeHeader(bufferedWriter, centerNames.size(), centers.keySet().size());
            rFile.seek(rFile.length());
            //Save new center
            CsvWriter writer = CsvWriter.dsl().to(bufferedWriter);
            writer.appendRow(center.toRow());
            Vaccinati.createNewFile(center.getName());
        }
    }


    /**
     * Writes a center to the file, in CSV format
     *
     * @throws IOException If data cannot be saved to the file for any reason
     */
    public static void save() throws IOException {
        try (RandomAccessFile fr = new RandomAccessFile(file, "rw");
             BufferedWriter fw = new BufferedWriter(new FileWriter(fr.getFD()))) {
            //Delete everything after header as we are overwriting, 1 char = 1 byte
            fr.setLength(21);
            //Go to file end
            fr.seek(21);
            //Save centers
            CsvWriter cw = CsvWriter.dsl().to(fw);
            for (Center center : centers.values())
                cw.appendRow(center.toRow());
        }
    }

    /**
     * Method used to read the data from the file and insert it into the LinkedListMultiMap. It also creates the vaccination file for each center if it doesn't exist.
     *
     * @throws IOException If data cannot be loaded from the file for any reason
     */
    public static void load() throws IOException {
        if (!createNewFile()) {
            try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
                //Create maps
                int[] sizes = readHeader(fr, 2);
                centerNames = new HashSet<>(getMapSize(0.75f, sizes[0]));
                centers = LinkedListMultimap.create(sizes[1]);
                //Read centers
                Iterator<String[]> iter = CsvParser.iterator(fr);
                while (iter.hasNext()) {
                    String[] row = iter.next();
                    Center center = new Center(row);
                    centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
                    centerNames.add(center.getName());
                    Vaccinati.createNewFile(center.getName());
                }
            }
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


    /**
     * @param center The center to check the existence of
     * @return True if and only if <code>center</code> exists. The center name is used to check its existence with the method {@link HashSet#contains(Object)}
     */
    public static boolean contains(Center center) {
        return centerNames.contains(center.getName());
    }

    /**
     * Creates a new file. The first 10 digits in the first line represents the number of centers, the next 10 the number of districts
     *
     * @return True if a file didn't exist and was created successfully, false otherwise. Thi method uses {@link File#createNewFile()}
     * @throws IOException If the file could not be created for any reason
     */
    private static boolean createNewFile() throws IOException {
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file)) {
                writeHeader(writer, 0);
            }
            return true;
        }
        return false;
    }
}
