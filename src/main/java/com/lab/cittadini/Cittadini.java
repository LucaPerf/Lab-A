package com.lab.cittadini;

import com.lab.data.Center;
import com.lab.data.CenterType;
import com.lab.data.User;
import com.lab.datamanager.Centri;
import com.lab.datamanager.Registrati;
import com.lab.datamanager.Vaccinati;

import java.util.LinkedList;

/**
 * This class manages the user actions
 *
 * @author Ciceri Luigi
 */
public class Cittadini {

    /**
     * Add a new user and save its data into "Cittadini_Registrati.csv"
     *
     * @param user The user to add
     */
    public static void registraCittadino(User user) {
        Registrati.save(user);
    }

    /**
     * @param userName The username
     * @param password The user password
     * @return The user object if and only if the user exists and the password matches
     */
    public static User login(String userName, String password) {
        User u = Registrati.find(userName);
        return ((u != null) && (u.getPassword().equals(password))) ? u : null;
    }

    /**
     * Searches for a center by name or by "comune" and type. If <code>type<code/> is null <code>key</code> is the name of the center, otherwise it is the name of the "comune".
     *
     * @param key  The name of the center or "comune" to search for
     * @param type The center type to search for
     */
    public static LinkedList<Center> cercaCentroVaccinale(String key, CenterType type) {
        if (type == null)
            return Centri.find(key);
        else
            return Centri.find(type, key);
    }

    /**
     * Check if the event hash been added in the center file
     *
     * @param uID Unique id associated with a vaccinated person
     * @param event Adverse events to be added or have already been added in the file
     * @param centerName The name of the center to add its data to the file
     * @return
     */
    public static boolean inserisciEventiAvversi(Integer uID, Event event, String centerName){
        Vaccinati.load(centerName);

        VaxInfo vi = Vaccinati.find(uID);

        if (vi == null)
            return false;
        else
            return VaxInfo.addEvent(event);
    }
}
