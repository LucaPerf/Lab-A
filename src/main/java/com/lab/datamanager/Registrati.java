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

public class Registrati extends Data{
    private Registrati(){}

    private static File file = new File(dataDirectory,"Cittadini.csv");
    private static HashMap<String, User> users = new HashMap<>();

    /**
     * Add a citizen to the LinkedHahMap, using userID as key
     *
     * @param cittadino The citizen to add
     */
    public static void add(User cittadino) {

        users.put(cittadino.getUserID(), cittadino);
    }

    /**
     * Save citizens in a file, named "Cittadini.csv"
     *
     * @param cittadino The citizen to save
     */
    public static void save(User cittadino) {
        add(cittadino);

        try {
            FileWriter filew = new FileWriter(file, true);
            CsvWriter cw = CsvWriter.dsl().to(filew);

            cw.appendRow(cittadino.toRow());
            filew.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the citizens' data from the file and write them in a LinkedHahMap
     */
    public static void load() {
        try {
            //Create the file if it does not exist
            file.createNewFile();

            FileReader filer = new FileReader(file);
            Iterator<String[]> it = CsvParser.iterator(filer);

            while (it.hasNext()) {
                String[] row = it.next();
                add(new User(row));
            }

            filer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param username The username to search for
     * @return The {@link User} whos username is <code>username</code>, <code>null</code> if no user with such name exists
     */
    public static User find(String username) {
        return users.get(username);
    }
}
