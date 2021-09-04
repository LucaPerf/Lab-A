package com.lab.datamanager;

import com.lab.data.User;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class manages registered users' data.
 * <p> All data is stored into "Cittadini.csv".
 * The header contains the number of registered users.<br>
 * Users' data is loaded into a {@link HashMap} with username as key.<br>
 *
 * @author Luca Perfetti
 * @author Luigi Ciceri
 */

public class Registrati extends Data {
    /**
     * Private contructor to avoid class instantiation.
     */
    private Registrati() {
    }

    /**
     * {@link File} all data is saved into.
     */
    private static File file = new File(dataDirectory, "Cittadini.csv");
    /**
     * Stores users' information by username.
     */
    private static HashMap<String, User> users = new HashMap<>();

    /**
     * Adds a user to {@link #users} and saves it to the {@link #file}.
     * <p>The header is updated with the new map size.
     *
     * @param cittadino The citizen to add
     * @throws IOException If data could not be added for any reason
     */
    public static void add(User cittadino) throws IOException {
        users.put(cittadino.getUsername(), cittadino);
        try (RandomAccessFile rf = new RandomAccessFile(file, "rw");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rf.getFD()), StandardCharsets.UTF_8))) {
            writeHeader(writer, users.size());
            //Write user
            rf.seek(rf.length());
            CsvWriter cw = CsvWriter.dsl().to(writer);
            cw.appendRow(cittadino.toRow());
        }
    }

    /**
     * Loads all data from {@link #file} into {@link #users}.
     * <p>If the file does not exist it is created and nothing else is done.<br>
     * Otherwise the {@link #users} map is assigned a new instance with the correct size parsed from the header.
     *
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load() throws IOException {
        if (!createNewFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                //Create hashmap
                int size = readHeader(reader, 1)[0];
                users = new LinkedHashMap<>(getMapSize(0.75f, size));
                //Load users
                Iterator<String[]> it = CsvParser.iterator(reader);
                while (it.hasNext()) {
                    String[] row = it.next();
                    User user = new User(row);
                    users.put(user.getUsername(), user);
                }
            }
        }
    }

    /**
     * Searches a user by <code>username</code>.
     *
     * @param username The username to search for
     * @return The {@link User} whose username is <code>username</code> or <code>null</code> if no user with such name exists
     */
    public static User find(String username) {
        return users.get(username);
    }

    /**
     * Checks if a user exists.
     *
     * @param user The user to check the existence of
     * @return True if and only if a user with {@link User#getUsername()} exists.
     * <p>This method uses {@link Map#containsKey(Object)}
     */
    public static boolean contains(User user) {
        return users.containsKey(user.getUsername());
    }

    /**
     * Creates a new file.
     * <p>Header is written with a value of 0.
     * {@link BufferedOutputStream} is not used as only a small amount of data has to be written.
     *
     * @return True if a file didn't exist and was created successfully, false otherwise.
     * <p>This method uses {@link File#createNewFile()}
     * @throws IOException If the file could not be created for any reason
     */
    private static boolean createNewFile() throws IOException {
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                writeHeader(writer, 0);
            }
            return true;
        }
        return false;
    }
}
