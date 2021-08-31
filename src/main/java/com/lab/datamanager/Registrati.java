package com.lab.datamanager;

import com.google.common.base.Strings;
import com.lab.data.User;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class used for register citizens.
 *
 * @author Luca Perfetti
 */

public class Registrati extends Data {
    private Registrati() {
    }

    private static File file = new File(dataDirectory, "Cittadini.csv");
    private static HashMap<String, User> users = new HashMap<>();

    /**
     * Adds a suer to a file, named "Cittadini.csv"
     *
     * @param cittadino The citizen to add
     * @throws IOException If data could not be added for any reason
     */
    public static void add(User cittadino) throws IOException {
        users.put(cittadino.getUserID(), cittadino);
        try (RandomAccessFile rf = new RandomAccessFile(file, "rw");
             BufferedWriter writer = new BufferedWriter(new FileWriter(rf.getFD()))) {
            //Write HashMap size
            String mapSize = Integer.toString(users.size());
            writer.write(Strings.padStart(mapSize, 10, '0'));
            writer.flush();

            //Write user
            rf.seek(rf.length());
            CsvWriter cw = CsvWriter.dsl().to(writer);
            cw.appendRow(cittadino.toRow());
        }
    }

    /**
     * Read the citizens' data from the file and write them in a HashMap
     *
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load() throws IOException {
        if (!createNewFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                //Create hashmap with correct size
                char[] size = new char[10];
                reader.read(size, 0, 10);
                int mapSize = Integer.parseInt(new String(size));
                users = new HashMap<>(getMapSize(0.75f, mapSize));
                reader.skip(1);

                //Load users
                Iterator<String[]> it = CsvParser.iterator(reader);
                while (it.hasNext()) {
                    String[] row = it.next();
                    User user = new User(row);
                    users.put(user.getUserID(), user);
                }
            }
        }
    }

    /**
     * @param username The username to search for
     * @return The {@link User} whos username is <code>username</code>, <code>null</code> if no user with such name exists
     */
    public static User find(String username) {
        return users.get(username);
    }

    /**
     * @param user The user to check the existence of
     * @return True if and only if a user with {@link User#getUserID()} exists. This method uses {@link Map#containsKey(Object)}
     */
    public static boolean contains(User user) {
        return users.containsKey(user.getUserID());
    }

    /**
     * Creates a new file. The first line represents the number of objects (0).
     *
     * @return True if a file didn't exist and was created successfully, false otherwise. Thi method uses {@link File#createNewFile()}
     * @throws IOException If the file could not be created for any reason
     */
    private static boolean createNewFile() throws IOException {
        if (file.createNewFile()) {
            try (FileWriter fw = new FileWriter(file)) {
                fw.write("0000000000\n");
            }
            return true;
        }
        return false;
    }
}
