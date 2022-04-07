package com.lab.data;

/**
 * This class represents a registered user.
 * <p>Username, password, name, surname, "Codeice Fiscale", email and unique vaccination id are contained in this class.
 */
public class User {
    /**
     * The username
     */
    private String username;
    /**
     * The password of this account
     */
    private String password;
    /**
     * The name of the citizen
     */
    private String name;
    /**
     * The surname of the citizen
     */
    private String surname;
    /**
     * The "Codice Fiscale" of the citizen
     */
    private String ccf;
    /**
     * The email of the citizen
     */
    private String email;
    /**
     * The unique vaccination ID.
     * <p>It must have 16 digits.
     */
    private long uID;

    /**
     * @return The 16 digits unique vaccination identifier
     */
    public long getuID() {
        return uID;
    }


    /**
     * @return The username of this User
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password of this user instance
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
     * @param username The user id "nickname"
     * @param password The user password
     * @param uID      The unique id (16 digits)
     */
    public User(String name, String surname, String ccf, String email, String username, String password, long uID) {
        this.name = name.trim();
        this.surname = surname.trim();
        this.ccf = ccf.trim();
        this.email = email.trim();
        this.username = username.trim();
        this.password = password;
        this.uID = uID;
    }

    /**
     * Creates a new user object from a csv row.
     * <p>Each element of the array is a cell.
     *
     * @param row The row to get the data from, indexes mapped as follows:<br>
     *            0: {@link #username}<br>
     *            1: {@link #password}<br>
     *            2: {@link #name}<br>
     *            3: {@link #surname}<br>
     *            4: {@link #ccf}<br>
     *            5: {@link #email}<br>
     *            6: {@link #uID}<br>
     */
    public User(String[] row) {
        username = row[0];
        password = row[1];
        name = row[2];
        surname = row[3];
        ccf = row[4];
        email = row[5];
        uID = Long.parseLong(row[6]);
    }

    /**
     * @return A csv row representation of this object, indexes mapped as follows:<br>
     * 0: {@link #username}<br>
     * 1: {@link #password}<br>
     * 2: {@link #name}<br>
     * 3: {@link #surname}<br>
     * 4: {@link #ccf}<br>
     * 5: {@link #email}<br>
     * 6: {@link #uID}<br>
     */
    public String[] toRow() {
        return new String[]{
                username, password, name, surname, ccf, email, Long.toString(uID)};
    }
}
