package sk.softip;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {


   public static <T> void writeToFile (T element,String fileName, boolean append) throws IOException
    {

        FileWriter fileWriter = new FileWriter(fileName,append);
        fileWriter.write("ID;NAME;ROOM;TYPE;PRICE;IN_DATE;OUT_DATE;STATE\n");
        fileWriter.write(element.toString().replace("[","").replace(" ",";").replace(",","\n").replaceAll("\n;","\n").replace("]",""));
        fileWriter.close();
    }


}
