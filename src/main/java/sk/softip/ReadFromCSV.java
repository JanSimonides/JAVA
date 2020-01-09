package sk.softip;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

//funkcia na citanie vstupu
public class ReadFromCSV {
    private final static Logger logger = Logger.getLogger(App.class);
    public static ArrayList<Property> readProperty(String fileName) throws FileNotFoundException {
        ArrayList<Property> properties = new ArrayList<>();
        BufferedReader br= null;
        String line;
        String[] parameters;
        int i;
        {
            //for (i=0; i < fileName.size(); i++) {
                //System.out.println(fileName);
            String path = System.getProperty("user.dir");
            path = path.substring(0,path.length()-6);
                Path pathToFile = Paths.get(path+"Files/"+ fileName);
                //kontrola vstupneho suboru
                File file = new File(String.valueOf(pathToFile));
                //System.out.println(!file.exists());
                if (!file.exists()){
                    logger.error("Zadany subor neexistuje: "+fileName);
                }

                else {
                    try {
                        br = new BufferedReader(Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8));
                        //nacitanie prveho riadku s udajmi hlavicky
                        String firstLine = br.readLine();
                        line = br.readLine();
                        while (line != null) {

                            parameters = line.split(";");

                            Property property = new Property(parameters);
                            /*if (!Property.stateValidation(property)) {
                                logger.error("Zla hodnota property_state: " + property.getPropertyState() + " v subore: " + fileName.get(i));
                            }
                            if (!Property.typeValidation(property)) {
                                logger.error("Zla hodnota property_type: " + property.getPropertyType() + " v subore: " + fileName.get(i));
                            }*/
                            properties.add(property);

                            line = br.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        return properties;
    }
}
