package sk.softip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class App
{
   //arraylist bez typu mozu sa ukladat stringy pre ROOMS a objekty majetku pre ostatne podmienky
    private static ArrayList valuesArray = new ArrayList<>();

    public static void main( String[] args ) throws FileNotFoundException {
        //inicializacia a nacitania udajov zo vstupneho suboru inventuravzor.csv
        ArrayList<Property> properties  = null;
        properties = ReadFromCSV.readProperty("inventuraVzor.csv");
        //prejdenie vsetkych objektov v arrayliste properties
        //osetrenie vstupu
        //String file = "removed";
        //file = file.toUpperCase();
        String file = args[0].toUpperCase();

         for (Property p : properties){
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
        }

        //zapisanie do subora
        try {
            WriteToFile.writeToFile(valuesArray, file+".txt",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("hotovo");
    }
}

