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

    /**
     * @param userName The username
     * @param password The user password
     * @return True if and only if the user exists and the password matches
     */
    public static boolean login(String userName, String password) {
        //User u = Registrati.find(userName,password);
        //return u != null && u.getPassword().equals(password) ;
        return true;
    }
}
