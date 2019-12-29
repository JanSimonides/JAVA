package sk.softip;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class App
{



     //arraylist bez typu mozu sa ukladat stringy pre ROOMS a objekty majetku pre ostatne podmienky
    private static ArrayList valuesArray = new ArrayList<>();
    private final static Logger logger = Logger.getLogger(Property.class.getName());
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softipbase");

    public static void main( String[] args ) throws FileNotFoundException, SQLException, ClassNotFoundException {
        if (args.length>10) {
            System.out.println("Chyba");
            System.exit(0);
        }

        InputStream stream = App.class.getResourceAsStream("META-INF/persistence.xml");
        System.out.println(stream != null);


        //rozdelenie vstupnych parametrov
       /* String[] input = String.join( " ", args ).split("[, ]");
        int i;
        for (i=0; i<input.length;i++){
            input[i] = input[i]+".csv";
            System.out.println(input[i]);
        }*/

       //docasne testovanie
       String[] input = new String[2];
        input[0] = "inventuraVzor1.csv";
        input[1] = "inventuraVzor2.csv";

       //inicializacia a nacitania udajov zo vstupneho suboru inventuravzor.csv
        ArrayList<Property> properties  = null;
        properties = ReadFromCSV.readProperty(input);
        //prejdenie vsetkych objektov v arrayliste properties
        //osetrenie vstupu
        //String file = "removed";
        //file = file.toUpperCase();
//        String file = args[0].toUpperCase();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        entityTransaction= em.getTransaction();
        entityTransaction.begin();

        for (Property p : properties){
            em.persist(p);
            entityTransaction.commit();
        }

        entityManagerFactory.close();
        //funkcionalita vyhladavania konkretnych udajov
         /*for (Property p : properties){
             switch (file) {
                 case "ROOMS":
                     if (!valuesArray.contains(p.getPropertyRoom())) {
                         valuesArray.add(p.getPropertyRoom());
                     }
                     break;
                 case "MISSING":
                     if (p.getPropertyState() == 'M') {
                         valuesArray.add(p);
                         valuesArray.sort(Comparator.comparing(x-> p.getPropertyPrice()));
                     }
                     break;
                 case "MOVED":
                     if (p.getPropertyState() == 'V') {
                         valuesArray.add(p);
                         valuesArray.sort(Comparator.comparing(x -> p.getPropertyInDate()));
                     }
                     break;
                 case "OK":
                     if (p.getPropertyState() == 'O') {
                         valuesArray.add(p);
                         valuesArray.sort(Comparator.comparing(x-> p.getPropertyPrice()));
                         Collections.reverse(valuesArray);
                     }
                     break;
                 case "REMOVED":
                     if (p.getPropertyOutDate() != null) {
                         valuesArray.add(p);
                     }
                     break;
                 default:
                     System.out.println("Zadal si zly subor vypinam...");
                     System.exit(0);
             }
        }*/

        /*zapisanie do subora
        try {
            WriteToFile.writeToFile(valuesArray, file+".txt",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        //System.out.println("hotovo");*/
    }
}

