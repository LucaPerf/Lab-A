package com.lab.data;

/**
 * This class represents a registered user.
 *
 * @author Ciceri Luigi
 */
public class User {
    private String userID;
    private String password;
    private String name;
    private String surname;
    private String ccf;
    private String email;
    private Integer uID;


    /**
     * @return The username of this User
     * @author Luca Perfetti
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return The password of this user instance
     * @author Ciceri Luigi
     */
    public String getPassword() {
        return password;
    }

    /**
     * Creates a new user object. All string parameters are trimmed.
     *
     * @param name     The name of the user
     * @param surname  The surname of the user
     * @param ccf      The "codice fiscale" of the user
     * @param email    The user email
     * @param userID   "The user id "nickname"
     * @param password "The user password"
     * @param uID      "The unique id (16 bit) provided by the center"
     */
    public User(String name, String surname, String ccf, String email, String userID, String password, Integer uID) {
        this.name = name.trim();
        this.surname = surname.trim();
        this.ccf = ccf.trim();
        this.email = email.trim();
        this.userID = userID.trim();
        this.password = password;
        this.uID = uID;
    }

    /**
     * Creates a new user object from a csv row.
     *
     * @param row The row to get the data from, indexes mapped as follows:
     *            0: userid
     *            1: password
     *            2: name
     *            3: surname
     *            4: CCF
     *            5: email
     *            6: unique id
     * @throws ArrayIndexOutOfBoundsException If the array contains more or less than 7 elements
     * @throws NumberFormatException          If the uID format is invalid
     */
    public User(String[] row) throws ArrayIndexOutOfBoundsException, NumberFormatException {
        userID = row[0];
        password = row[1];
        name = row[2];
        surname = row[3];
        ccf = row[4];
        email = row[5];
        uID = Integer.parseInt(row[6]);
    }

    /**
     * @return A string array containing all information about the User object, indexes mapped as follows:
     * 0: userid
     * 1: password
     * 2: name
     * 3: surname
     * 4: CCF
     * 5: email
     * 6: unique id
     */
    public String[] toRow() {
        return new String[]{
                userID, password, name, surname, ccf, email, uID.toString()};
    }
}
