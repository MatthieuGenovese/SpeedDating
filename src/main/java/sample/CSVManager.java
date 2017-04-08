package sample;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by Matthieu on 22/03/2017.
 */
public class CSVManager {
    private CSVReader reader;
    private CSVWriter writer;

    public CSVManager (String filePath) throws Exception{
        reader = new CSVReader(new FileReader(filePath),'\t');
        writer = new CSVWriter(new FileWriter("matriceConflits.csv"), ';');
    }


    public ArrayList<Personne> getPersonnesFromCSV (){
        ArrayList<Personne> listePersonnes = new ArrayList<>();
        String[] nextLine;
        try {
            while((nextLine = reader.readNext()) != null){
                try {
                    listePersonnes.add(new Personne(Integer.parseInt(nextLine[0]), nextLine[1], nextLine[2], nextLine[3], Integer.parseInt(nextLine[4]), Integer.parseInt(nextLine[5]), Integer.parseInt(nextLine[6]), new Date(nextLine[7])));
                }
                catch(IllegalFormatException e){
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
                catch(IllegalArgumentException e){
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listePersonnes;
    }

    public void ecrireMatriceCPLEX(ArrayList<Integer> conflits, int nbCol){
        List<String> entries = new ArrayList<>();
        List<String[]> listeSArray = new ArrayList<String[]>() {
        };
        int cpt = 0;
        for(int j = 0; j <= conflits.size(); j++) {
            if(cpt < nbCol){
                entries.add(Integer.toString(conflits.get(j)));
            }
            else{
                entries.add("\n");
                String[] s = new String[cpt+2];
                s = entries.toArray(s);
                listeSArray.add(s);
                entries = new ArrayList<>();
                if(j != conflits.size()) {
                    entries.add(Integer.toString(conflits.get(j)));
                }
                cpt = 0;
            }
            cpt++;
        }
        writer.writeAll(listeSArray, false);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
