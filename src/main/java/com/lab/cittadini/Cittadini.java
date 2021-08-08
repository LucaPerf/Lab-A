package com.lab.cittadini;

import com.lab.data.CenterType;
import com.lab.data.User;
import com.lab.datamanager.Registrati;

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
        return (u != null && u.getPassword().equals(password)) ? u : null;
    }

    /**
     * Searches for a center by name or by "comune" and type. If <code>type<code/> is null <code>key</code> is the name of the center, otherwise it is the name of the "comune".
     *
     * @param key  The name of the center or "comune" to search for
     * @param type The center type to search for
     */
    public static void cercaCentroVaccinale(String key, CenterType type) {
        if (type == null)
            //Centri.find(key);
            System.out.println("Search by name " + key);
        else
            //Centri.find(key,type);
            System.out.println("Search in " + key + " of type " + type);
    }
}
