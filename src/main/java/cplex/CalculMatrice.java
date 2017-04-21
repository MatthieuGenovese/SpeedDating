package cplex;

import javafx.util.Pair;
import sample.CSVManager;
import sample.Personne;
import sample.PersonneSoiree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class CalculMatrice {
    private int nbCol,nbLigne;
    private CSVManager manager;

    public CalculMatrice(CSVManager manager, int nbLigne, int nbCol) {
        this.nbCol = nbCol;
        this.nbLigne = nbLigne;
        this.manager = manager;
    }

    public void calculerMatriceCPLEX(ArrayList<PersonneSoiree> listePs) {
        ArrayList<Integer> matrice = new ArrayList<>();
        for (PersonneSoiree Ps : listePs) {
            if (Ps.getPersonne().getGenre().equalsIgnoreCase("m")) {
                for (Pair<Personne, Integer> p : Ps.getConflits()) {
                    for (Pair<Personne, Integer> p2 : p.getKey().getPersonneSoiree().getConflits()) {
                        if (p2.getKey().getId() == Ps.getPersonne().getId()) {
                            matrice.add(Math.min(p2.getValue(),p.getValue()));

                        }
                    }
                }
            }
        }
        manager.ecrireMatriceCPLEX(matrice, nbCol);

        Solver s = new Solver("src\\main\\opl\\modele2P.lp",nbCol,nbLigne);
        try {
            s.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
