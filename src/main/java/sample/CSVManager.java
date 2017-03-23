package sample;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;

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
                    listePersonnes.add(new Personne(nextLine[0], nextLine[1], Integer.parseInt(nextLine[2]), Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[4]), new Date(nextLine[5])));
                }
                catch(IllegalFormatException e){
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
                catch(IllegalArgumentException e){
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listePersonnes;
    }
}