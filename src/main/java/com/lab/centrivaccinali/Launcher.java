package com.lab.centrivaccinali;

/**
 * This class launches the application.
 * <p>This is needed due to an OpenJFX module incompatibility. The only purpose of this class is to call {@link CentriVaccinali#main(String[])}
 */
public class Launcher {
    /**
     * Private constructor to make ht eclass not instantiable
     */
    private Launcher() {
    }

    /**
     * The main method called by the JVM.
     * <p>Invokes {@link CentriVaccinali#main(String[])}
     *
     * @param args The arguments passed from the command line
     */
    public static void main(String[] args) {
        CentriVaccinali.main(args);
    }
}
