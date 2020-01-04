package sk.softip;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class App
{
    //arraylist bez typu mozu sa ukladat stringy pre ROOMS a objekty majetku pre ostatne podmienky
    //private static ArrayList valuesArray = new ArrayList<>();

    private final static Logger logger = Logger.getLogger(App.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softipbase");

    public static void main( String[] args ) throws FileNotFoundException, SQLException, ClassNotFoundException {

        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        //rozdelenie vstupnych parametrov
        String[] input = String.join( " ", args ).split("[, ]");
        if (input.length > 10) {
            logger.error("Chyba zadal si prilis vela parametrov: "+input.length);
            //System.out.println("Chyba zadal si prilis vela parametrov: "+input.length);
            System.exit(0);
        }
        //pridanie pripny csv a kontrola duplikatov
        ArrayList<String> inputs = new ArrayList<String>();
        int i;
        for (i=0; i<input.length;i++){
            input[i]= input[i]+".csv";
            if (!inputs.contains(input[i])){
                inputs.add(input[i]);
            }
            else {
                logger.error("Pokus o opakovany import: "+input[i]);
                System.out.println(input[i] + " bol zadany viackrat (nacital sa len raz)");
            }
        }


        //inicializacia a nacitania udajov zo vstupnych suborov inventuravzor[i].csv
        ArrayList<Property> properties = null;
        properties = ReadFromCSV.readProperty(inputs);

        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        entityTransaction = em.getTransaction();

        for (Property p : properties) {
            if(Property.propertyValidation(p)) {
                entityTransaction.begin();
                em.persist(p);
                entityTransaction.commit();
            }
            else {
                logger.warn("Udaj nebol pridany do databazy: "+p.toString());
            }
        }

        //list na nacitanie hodnot z databazy
        List<Property> propertyList;

        propertyList = Property.getProperties(em);
        System.out.println(propertyList);


        entityManagerFactory.close();

        // knotrola a zoradenie a zapis udajov
        ArrayList<Property> missing = new ArrayList<Property>();
        ArrayList<Property> moved = new ArrayList<Property>();
        ArrayList<Property> ok = new ArrayList<Property>();
        ArrayList<Property> removed = new ArrayList<Property>();


        for (Property p : propertyList) {
            if (p.getPropertyState() == 'M') {

                missing.add(p);
                missing.sort(Comparator.comparing(x -> p.getPropertyPrice()));

                try {
                    WriteToFile.writeToFile(missing, "Files/MISSING.txt", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (p.getPropertyState() == 'V') {
                moved.add(p);
                moved.sort(Comparator.comparing(x -> p.getPropertyInDate()));

                try {
                    WriteToFile.writeToFile(moved, "Files/MOVED.txt", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (p.getPropertyState() == 'O') {
                ok.add(p);
                ok.sort(Comparator.comparing(x -> p.getPropertyPrice()));
                Collections.reverse(ok);
                try {
                    WriteToFile.writeToFile(ok, "Files/OK.txt", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (p.getPropertyOutDate() != null) {
                removed.add(p);
                try {
                    WriteToFile.writeToFile(removed, "Files/REMOVED.txt", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}

