package com.lab.cittadini;

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
}
