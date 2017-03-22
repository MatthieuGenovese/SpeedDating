package sample;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matthieu on 22/03/2017.
 */
public class CSVManager {
    private CSVReader reader;

    public CSVManager (String filePath) throws Exception{
        reader = new CSVReader(new FileReader(filePath),'\t');
    }


    public ArrayList<Personne> getPersonnesFromCSV (){
        ArrayList<Personne> listePersonnes = new ArrayList<>();
        String[] nextLine;
        try {
            while((nextLine = reader.readNext()) != null){
                try {
                    listePersonnes.add(new Personne(nextLine[0], nextLine[1], Integer.parseInt(nextLine[2]), Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[4]), new Date()));
                }
                catch(NumberFormatException e){
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listePersonnes;
    }
}
