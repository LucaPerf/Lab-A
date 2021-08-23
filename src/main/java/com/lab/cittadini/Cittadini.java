package com.lab.cittadini;

import com.lab.data.*;
import com.lab.datamanager.Centri;
import com.lab.datamanager.Registrati;
import com.lab.datamanager.Vaccinati;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * This class manages the user actions
 *
 * @author Ciceri Luigi
 */
public class Cittadini {

    private static Comparator<Center> centerNameComp = Comparator.comparing(Center::getName);

    /**
     * Add a new user and save its data into "Cittadini_Registrati.csv"
     *
     * @param user The user to add
     */
    public static void registraCittadino(User user) {
        Registrati.save(user);
    }

    /**
     * @param userName The trimmed username string
     * @param password The user password
     * @return The user object if and only if the user exists and the password matches
     */
    public static User login(String userName, String password) {
        User u = Registrati.find(userName.trim());
        return ((u != null) && (u.getPassword().equals(password))) ? u : null;
    }

    /**
     * Searches for a center by name or by "comune" and type. If <code>type<code/> is null <code>key</code> is the name of the center, otherwise it is the name of the "comune".
     * The <code>key</code> is  converted to lowercase by {@link Centri} and trimmed, the returned list is sorted in alphabetic order.
     *
     * @param key  The name of the center or "comune" to search for
     * @param type The center type to search for
     */
    public static LinkedList<Center> cercaCentroVaccinale(String key, CenterType type) {
        String trimmedKey = key.trim();
        LinkedList<Center> list;
        if (type == null)
            list = Centri.find(trimmedKey);
        else
            list = Centri.find(type, trimmedKey);

        list.sort(centerNameComp);
        return list;
    }

    /**
     * Check if the event hash been added in the center file
     *
     * @param uID        Unique id associated with a vaccinated person
     * @param event      Adverse events to be added or have already been added in the file
     * @param centerName The name of the center to add its data to the file
     * @return
     */
    public static boolean inserisciEventiAvversi(Integer uID, Event event, String centerName) {
        Vaccinati.load(centerName);

        VaxInfo vi = Vaccinati.find(uID);

        if (vi == null)
            return false;
        else
            return vi.addEvent(event);
    }
}
