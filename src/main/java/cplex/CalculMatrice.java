package cplex;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import sample.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class CalculMatrice {
    private int nbCol,nbLigne;
    private CSVManager manager;
    private int[][][] matriceResultat;
    private int crenaux = 3;
    private ArrayList<PersonneSoiree> listePersonnesSoiree;
    private ObservableList<Personne> hommeListe, femmeListe;

    public CalculMatrice(CSVManager manager, int nbLigne, int nbCol, ObservableList<Personne> hommes, ObservableList<Personne> femmes, ArrayList<PersonneSoiree> listePersonnesSoiree) {
        this.nbCol = nbCol;
        this.nbLigne = nbLigne;
        this.manager = manager;
        this.listePersonnesSoiree = listePersonnesSoiree;
        this.hommeListe = hommes;
        this.femmeListe = femmes;
    }

    public void calculerMatriceCPLEX() {
        int[][][] resultat;
        ArrayList<Integer> matrice = new ArrayList<>();
        for (PersonneSoiree Ps : listePersonnesSoiree) {
            if (Ps.getPersonne().getGenre().equalsIgnoreCase("m")) {
                for (Affinite p : Ps.getConflits()) {
                    for (Affinite p2 : p.getPersonne().getPersonneSoiree().getConflits()) {
                        if (p2.getPersonne().getId() == Ps.getPersonne().getId()) {
                            matrice.add(Math.min(p2.getAffinite(),p.getAffinite()));

                        }
                    }
                }
            }
        }
        manager.ecrireMatriceCPLEX(matrice, nbCol);
        Solver s = new Solver("src\\main\\opl\\modele2P.lp",nbCol,nbLigne);
        try {
            matriceResultat = s.exec();
            organiserCrenaux();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void organiserCrenaux(){
        ArrayList<Crenau> listeCrenaux = new ArrayList<>();
        for(int l = 0; l < crenaux; l ++){
            listeCrenaux.add(new Crenau());
        }
        for(int homme = 0; homme < nbLigne; homme++){
            for(int femme = 0; femme < nbCol; femme++){
                for(int k = 0; k < crenaux; k++) {
                    if(matriceResultat[homme][femme][k] == 1 ) {
                        listeCrenaux.get(k).ajouterRencontre(new Rencontre(hommeListe.get(homme), femmeListe.get(femme)));
                    }
                }
            }
        }
        for(int cpt = 0; cpt < listeCrenaux.size(); cpt++){
            System.out.println("Crenaux numero : " + cpt + listeCrenaux.get(cpt));
        }
    }
}
