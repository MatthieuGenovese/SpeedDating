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
    private ObservableList<PersonneSoiree> hommeListe, femmeListe;

    public void setHommeListe(ObservableList<PersonneSoiree> hommeListe) {
        this.hommeListe = hommeListe;
    }

    public void setFemmeListe(ObservableList<PersonneSoiree> femmeListe) {
        this.femmeListe = femmeListe;
    }

    public CalculMatrice(CSVManager manager, int nbLigne, int nbCol, ObservableList<PersonneSoiree> hommes, ObservableList<PersonneSoiree> femmes) {
        this.nbCol = nbCol;
        this.nbLigne = nbLigne;
        this.manager = manager;

        this.hommeListe = hommes;
        this.femmeListe = femmes;
    }

    public void calculerMatriceCPLEX() {
        ArrayList<Integer> matrice = new ArrayList<>();
        for (PersonneSoiree homme : hommeListe) {
            for (Affinite p : homme.getConflits()) {
                System.out.println("Affinité : " + p.toString());
                for (Affinite p2 : p.getPersonneSoiree().getConflits()) {
                    System.out.println("Affinité : " + p2.toString());
                    if (p2.getPersonneSoiree().getId() == homme.getId()) {
                        System.out.print(Math.min(p2.getAffinite(),p.getAffinite()) + " ,");
                        matrice.add(Math.min(p2.getAffinite(),p.getAffinite()));
                    }
                }
            }
        }
        manager.ecrireMatriceCPLEX(matrice, nbCol);
        Solver s = new Solver("src\\main\\opl\\modele2P.lp",nbCol,nbLigne);
        try {
            matriceResultat = s.exec();
            if(matriceResultat[0][0][0] == -1){
                System.out.println("le cplex n'a pas trouvé de solution !");
            }
            else {
                organiserCrenaux();
            }
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
