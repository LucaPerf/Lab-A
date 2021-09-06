package com.lab.cittadini;

import com.lab.data.*;
import com.lab.datamanager.Centers;
import com.lab.datamanager.Users;
import com.lab.datamanager.Vaccinations;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * This class manages user's operations
 *
 * @author Ciceri Luigi
 * @author Luca Perfetti
 */
public class Cittadini {
    /**
     * Privat constructor to prevent the class from being instantiated
     */
    private Cittadini() {
    }

    /**
     * Comparator used to sort the centers search result.
     * <p>It compares the centers' names,
     */
    private static Comparator<Center> centerNameComp = Comparator.comparing(Center::getName);

    /**
     * Registers a new user and saves its data.
     * <p>Data is saved into "Cittadini.csv"
     *
     * @param user The user to register
     * @return True if and only if the user doesn't exist and was successfully added
     * @throws IOException If the user could not be saved to the file for any reason
     */
    public static boolean registraCittadino(User user) throws IOException {
        if (!Users.contains(user)) {
            Users.add(user);
            return true;
        }
        return false;
    }

    /**
     * Used for users login.
     * <p>The <code>userName</code> string is trimmed.
     *
     * @param userName The username
     * @param password The user password
     * @return A user object if and only if <code>userName</code> exists and the <code>password</code> matches
     */
    public static User login(String userName, String password) {
        User u = Users.find(userName.trim());
        return ((u != null) && (u.getPassword().equals(password))) ? u : null;
    }

    /**
     * Searches for a center by name or by district and type.
     * <p>If <code>type<code/> is null <code>key</code> is the name of the center, otherwise it is the name of the district.
     * The <code>key</code> is  converted to lowercase by {@link Centers} and trimmed, the returned list is sorted in alphabetic order using {@link #centerNameComp} and {@link java.util.List#sort(Comparator)}.
     *
     * @param key  The name of the center or district to search for
     * @param type The center type to search for
     */
    public static LinkedList<Center> cercaCentroVaccinale(String key, CenterType type) {
        String trimmedKey = key.trim();
        LinkedList<Center> list;
        if (type == null)
            list = Centers.find(trimmedKey);
        else
            list = Centers.find(type, trimmedKey);

        list.sort(centerNameComp);
        return list;
    }

    /**
     * Reports a new event.
     *
     * @param uID    Unique id associated with the citizen who reported the event
     * @param event  The event to be added
     * @param center Center to update the statics of
     * @return False if the user hasn't been found or an event of the same type has already been reported
     * @throws IOException If data cannot be saved to the file for any reason
     */
    public static boolean inserisciEventiAvversi(long uID, Event event, Center center) throws IOException {
        Vaccinations.load(center.getName());
        VaxInfo vi = Vaccinations.find(uID);

        if (vi != null && vi.addEvent(event)) {
            center.updateStat(event);
            Vaccinations.save(center.getName());
            Centers.save();
            return true;
        }
        return false;
    }
}
