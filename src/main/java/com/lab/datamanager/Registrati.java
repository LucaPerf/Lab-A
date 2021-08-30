package com.lab.datamanager;

import com.lab.data.User;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.lightningcsv.CsvWriter;

import java.io.*;
import java.util.*;

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

        try (FileWriter filew = new FileWriter(file, true)) {
            CsvWriter cw = CsvWriter.dsl().to(filew);
            cw.appendRow(cittadino.toRow());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Read the citizens' data from the file and write them in a HashMap
     *
     * @throws IOException If data could not be loaded from the file for any reason
     */
    public static void load() throws IOException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        //Actual parsing
        try (FileReader filer = new FileReader(file)) {
            Iterator<String[]> it = CsvParser.iterator(filer);
            while (it.hasNext()) {
                String[] row = it.next();
                User user = new User(row);
                users.put(user.getUserID(), user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
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
}
