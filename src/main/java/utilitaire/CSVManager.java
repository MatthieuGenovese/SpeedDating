package utilitaire;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import personnes.IParticipants;
import personnes.Personne;
import recontres.ITimeWindows;
import recontres.TimeWindow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    }


    public ArrayList<IParticipants> getPersonnesFromCSV () {
        ArrayList<IParticipants> listePersonnes = new ArrayList<>();
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                try {
                    listePersonnes.add(new Personne(Integer.parseInt(nextLine[0]), nextLine[1], nextLine[2], nextLine[3], Integer.parseInt(nextLine[4]), Integer.parseInt(nextLine[5]), Integer.parseInt(nextLine[6]), new TimeWindow(Integer.parseInt(nextLine[7]), Integer.parseInt(nextLine[8]))));
                } catch (IllegalFormatException e) {
                    System.out.println("Mauvaise entrée, personne illisible ! \n");
                    //System.out.println(nextLine[0] +  nextLine[1] + nextLine[2]+  nextLine[3] + nextLine[4]+ nextLine[5]+ nextLine[6] +nextLine[7] +nextLine[8]);
                } catch (IllegalArgumentException e) {
                    //System.out.println("Mauvaise entrée, personne illisible ! \n");
                    //System.out.println(nextLine[0] +  nextLine[1] + nextLine[2]+  nextLine[3] + nextLine[4]+ nextLine[5]+ nextLine[6] +nextLine[7] +nextLine[8]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //System.out.println("Mauvaise entrée, personne illisible ! \n");
                    //System.out.println(nextLine[0] +  nextLine[1] + nextLine[2]+  nextLine[3] + nextLine[4]+ nextLine[5]+ nextLine[6] +nextLine[7] +nextLine[8]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listePersonnes;
    }

    public void ecrireDispos(ArrayList<ITimeWindows> dispoH, ArrayList<ITimeWindows> dispoF){
        try {
            CSVWriter writerH = new CSVWriter(new FileWriter("dispoH.csv"), ',');
            CSVWriter writerF = new CSVWriter(new FileWriter("dispoF.csv"), ',');
            List<String[]> listeSArray = new ArrayList<String[]>();
            for(int i = 0; i < dispoH.size(); i++){
                String[] s = {Integer.toString(dispoH.get(i).getArrivalSlot()), Integer.toString(dispoH.get(i).getDepartureSlot())};
                listeSArray.add(s);
            }
            writerH.writeAll(listeSArray, false);
            writerH.close();
            listeSArray = new ArrayList<String[]>();
            for(int i = 0; i < dispoF.size(); i++){
                String[] s = {Integer.toString(dispoF.get(i).getArrivalSlot()), Integer.toString(dispoF.get(i).getDepartureSlot())};
                listeSArray.add(s);
            }
            writerF.writeAll(listeSArray, false);
            writerF.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ecrireMatriceCPLEX(ArrayList<Integer> conflits, int nbCol){

        try {
            writer = new CSVWriter(new FileWriter("matriceConflits.csv"), ',');
            List<String> entries = new ArrayList<>();
            List<String[]> listeSArray = new ArrayList<String[]>();
            int cpt = 0;
            for(int j = 0; j <= conflits.size(); j++) {
                if(cpt < nbCol){
                    entries.add(Integer.toString(conflits.get(j)));
                }
                else{
                    String[] s = new String[cpt];
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
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
