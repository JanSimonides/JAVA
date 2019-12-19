package sk.softip;

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
    public static ArrayList<Property> readProperty(String fileName) throws FileNotFoundException {
        ArrayList<Property> properties = new ArrayList<>();
        BufferedReader br= null;
        String line;
        String parameters[];
        Path pathToFile = Paths.get(fileName);
        //URL u = ReadFromCSV.class.getClassLoader().getResource(fileName);
        //System.out.println(u);
        {
            try {
                br = new BufferedReader(Files.newBufferedReader(pathToFile.toAbsolutePath(),StandardCharsets.UTF_8));
                //nacitanie prveho riadku s udajmi hlavicky
                line = br.readLine();
                line = br.readLine();
                while (line != null) {

                    parameters = line.split(";");

                    Property property = new Property(parameters);
                    properties.add(property);

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
