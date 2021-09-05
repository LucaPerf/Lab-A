package com.lab.datamanager;

import com.google.common.collect.LinkedListMultimap;
import com.lab.data.Center;
import com.lab.data.CenterType;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Class used for center data management.
 * <p>Centers data is stored into a {@link LinkedListMultimap} with district name as key, and {@link Center} as values. In this way centers are grouped by district for fast search operations.
 * Districts' names are converted to lowercase to allow for case-insensitive search.<br>
 * A {@link HashSet} provides fast center existence check capability.<br>
 * A {@link File} represents the file data will be saved to.<br>
 * To optimize the maps' sizes, a header is written into the first line of the csv file. It contains two digits, padded to reach a length of 10 characters ({@link Integer#MAX_VALUE} length).
 * The first 10 digits represents the number of centers, the second one the number of districts.
 * The '\n' separator is appended after the digits.
 * This class provides static methods to load and save centers to a CSV file, named "CentriVaccinali.csv".
 *
 * @author Luigi Ciceri
 * @author Luca Perfetti
 **/

public class Centers extends Data {
    /**
     * Private constructor to avoid class instantiation
     */
    private Centers() {
    }

    /**
     * Represents the file data will be saved to.
     */
    private static File file = new File(dataDirectory, "CentriVaccinali.csv");
    /**
     * Stores centers grouped by district.
     * <p>This data structure allows keys to be mapped to multiple values and uses a {@link java.util.HashMap} as underlying structure.
     */
    private static LinkedListMultimap<String, Center> centers = LinkedListMultimap.create();
    /**
     * Map needed for fast center existence check.
     */
    private static HashSet<String> centerNames = new HashSet<>();

    /**
     * Adds a center to {@link #centers} and saves it into the {@link #file}.
     * <p>The header is also updated.
     *
     * @param center The center to add
     * @throws IOException If data cannot be added to the file for any reason
     */
    public static void add(Center center) throws IOException {
        centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
        centerNames.add(center.getName());
        //This will close the file whether an exception is thrown or not
        try (RandomAccessFile rFile = new RandomAccessFile(file, "rw");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rFile.getFD()), StandardCharsets.UTF_8))) {
            writeHeader(bufferedWriter, centerNames.size(), centers.keySet().size());
            rFile.seek(rFile.length());
            //Save new center
            CsvWriter writer = CsvWriter.dsl().to(bufferedWriter);
            writer.appendRow(center.toRow());
            Vaccinated.createNewFile(center.getName());
        }
    }


    /**
     * Saves all {@link #centers} into the {@link #file}.
     * <p>This is needed because specific parts of the file can't be overwritten without saving all content again.<br>
     * The header remain untouched.
     *
     * @throws IOException If data cannot be saved to the file for any reason
     */
    public static void save() throws IOException {
        try (RandomAccessFile rFile = new RandomAccessFile(file, "rw");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rFile.getFD()), StandardCharsets.UTF_8))) {
            //Delete everything after header as we are overwriting, 1 char = 1 byte
            rFile.setLength(21);
            //Go to file end
            rFile.seek(21);
            //Save centers
            CsvWriter cw = CsvWriter.dsl().to(writer);
            for (Center center : centers.values())
                cw.appendRow(center.toRow());
        }
    }

    /**
     * Read the data from the file and insert it into {@link #centers} and {@link #centerNames}.
     * <p>It also creates the vaccination file for each center if it doesn't exist, as well as maps of the correct size.<br>
     * If the file does not exist, it is created and nothing else is done.
     *
     * @throws IOException If data cannot be loaded from the file for any reason
     */
    public static void load() throws IOException {
        if (!createNewFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                //Create maps
                int[] sizes = readHeader(reader, 2);
                centerNames = new HashSet<>(getMapSize(0.75f, sizes[0]));
                centers = LinkedListMultimap.create(sizes[1]);
                //Read centers
                Iterator<String[]> iter = CsvParser.iterator(reader);
                while (iter.hasNext()) {
                    String[] row = iter.next();
                    Center center = new Center(row);
                    centers.put(center.getAddress().getDistrict().toLowerCase(Locale.ROOT), center);
                    centerNames.add(center.getName());
                    Vaccinated.createNewFile(center.getName());
                }
            }
        }
    }

    /**
     * Searches a center by name.
     *
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
     * Searches a center by district and type.
     *
     * @param type   The type of center to search for
     * @param district The name of the district to search in
     * @return A list of {@link Center} located in <code>district</code>, of <code>type</code> (case-insensitive)
     */
    public static LinkedList<Center> find(CenterType type, String district) {
        LinkedList<Center> list = new LinkedList<>();
        String key = district.toLowerCase(Locale.ROOT);

        if (centers.containsKey(key)) {
            for (Center fr : centers.get(key))
                if (type.equals(fr.getType()))
                    list.add(fr);
        }
        return list;
    }

    /**
     * @param n The number of centers to add to the list.
     *          <p>The order is guaranteed by the {@link LinkedListMultimap} implementation.
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
     * Checks if a center exists.
     *
     * @param center The center to check the existence of
     * @return True if and only if <code>center</code> exists. The center name is used to check its existence with {@link HashSet#contains(Object)}
     */
    public static boolean contains(Center center) {
        return centerNames.contains(center.getName());
    }

    /**
     * Creates a new file.
     * <p>Header is written with values of 0 and 0.
     * {@link BufferedOutputStream} is not used as only a small amount of data has to be written.
     *
     * @return True if the file didn't exist and was created successfully, false otherwise.
     * <p>This method uses {@link File#createNewFile()}
     * @throws IOException If the file could not be created for any reason
     */
    private static boolean createNewFile() throws IOException {
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                writeHeader(writer, 0, 0);
            }
            return true;
        }
        return false;
    }
}
