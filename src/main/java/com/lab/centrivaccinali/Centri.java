package com.lab.datamanager;

import com.lab.data.CenterType;
import com.lab.data.PostalAddress;

import java.io.*;
import java.util.LinkedList;
import java.util.LinkedHashMap;

/**
 *
 * class used for center data management
 *
 * @author Luca Perfetti
 **/

public class Centri {
        private String name;
        private PostalAddress address;
        private LinkedList<Center> centers;
        private CenterType type;

        public Centri(String name, PostalAddress address, CenterType type){
                this.name = name;
                this.address = address;
                this.type = type;
        }

        /**
         * method for adding centers
         * @param centri
         */
        public void addCenter(Center centri){
            centers.add(centri);
        }

        /**
        * method to save the center
        * @param centri
        */
        public void saveCenter(Center centri){
            try{
                FileWriter fileout = new FileWriter("nome.csv", true);
                fileout.append(name + " ");
                fileout.write(address + " ");
                fileout.append(type + " ");

                fileout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * method used to read the data from the file and insert it into the LinkedHashMap
         * @return
         */
        public LinkedHashMap<String, Center> loadCenter(){
                File fileout = new File("nome.csv");

                 try{
                        BufferedReader br = new BufferedReader(new FileReader(fileout));

                        String riga = br.readLine();
                        while((riga = br.readLine()) != null){
                            System.out.println(riga);
                        }
                 } catch (FileNotFoundException e){
                    e.printStackTrace();
                 } catch(IOException e){
                    e.printStackTrace();
                 }

                LinkedHashMap<String, Center> tabellaCentri = new LinkedHashMap<String, Center>();
                /**
                * in line 76 it gives me an error because it says that is expected ";"
                * but it seems correct to me written this way
                */
                public LinkedHashMap<String, Center> getCentri(){
                        return tabellaCentri;
                }
        }
}

