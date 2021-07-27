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

public class Registrati {
    private static LinkedHashMap<User, User> registracittadino= new LinkedHashMap();

    /**
     * Add a citizen to the LinkedHahMap, using userID as key
     *
     * @param cittadino
     */
    public static void addCittadini(User cittadino){

        addToHashMapByUserID(cittadino);
    }

    public static void addToHashMapByUserID(User cittadino){
        if(registracittadino.containsKey(cittadino.getUserID()))
            registracittadino.get(cittadino.getUserID()).add(cittadino);
        else{
            registracittadino.get(cittadino.getCcf()).add(cittadino);
        }
    }

    /**
     * Save citizens in a file, named "Cittadini.csv"
     *
     * @param cittadino
     */
    public static void saveCittadini(User cittadino){
        addCittadini(cittadino);

        try{
            FileWriter filew = new FileWriter("Cittadini.csv", true);
                CsvWriter boh = CsvWriter.dsl().to(filew);

                String[] columns = cittadino.toRow();

                boh.appendRow(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6]);
                filew.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Read the citizens' data from the file and write them in a LinkedHahMap
     */
    public static void loadCittadino(){
        try {
            FileReader filer = new FileReader("Cittadini.csv");

            Iterator<String[]> it = CsvParser.iterator(filer);

            while(it.hasNext()){
                String[] row = it.next();

                User cittadino = new User(row);

                addToHashMapByUserID(cittadino);
            }

            Collection<User> cit = registracittadino.values();
            for(User y : cit)
                    System.out.println(Arrays.toString(y.toRow()));

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
