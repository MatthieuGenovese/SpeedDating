package cplex;

import conflits.ICompatibility;
import javafx.collections.ObservableList;
import personnes.IParticipants;
import personnes.Personne;
import personnes.PersonneSoiree;
import recontres.GestionnaireCreneaux;
import recontres.ITimeWindows;
import recontres.Rencontre;
import utilitaire.CSVManager;

import java.util.ArrayList;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class CalculMatrice {
    private int nbCol,nbLigne;
    private CSVManager manager;
    private int[][][] matriceResultat;
    private int creneaux = 3;
    private ObservableList<IParticipants> hommeListe, femmeListe;
    private GestionnaireCreneaux gestionnaireCrenaux = new GestionnaireCreneaux(3);
    public void setHommeListe(ObservableList<IParticipants> hommeListe) {
        this.hommeListe = hommeListe;
    }

    public void setFemmeListe(ObservableList<IParticipants> femmeListe) {
        this.femmeListe = femmeListe;
    }

    public CalculMatrice(CSVManager manager, int nbLigne, int nbCol, ObservableList<IParticipants> hommes, ObservableList<IParticipants> femmes) {
        this.nbCol = nbCol;
        this.nbLigne = nbLigne;
        this.manager = manager;
        this.hommeListe = hommes;
        this.femmeListe = femmes;
    }

    public void calculerMatriceCPLEX() {
        ArrayList<Integer> matrice = new ArrayList<>();
        ArrayList<ITimeWindows> dispoF = new ArrayList<>();
        ArrayList<ITimeWindows> dispoH = new ArrayList<>();
        for (IParticipants femme : femmeListe) {
            dispoF.add(((PersonneSoiree) femme).getTimeWindow());
            for (ICompatibility p : ((PersonneSoiree) femme).getConflits()) {
                for (ICompatibility p2 : ((PersonneSoiree) p.getPersonneSoiree()).getConflits()) {
                    if (p2.getPersonneSoiree().getId() ==  femme.getId()) {
                        matrice.add(Math.min(p2.getAffinite(),p.getAffinite()));
                    }
                }
            }
        }
        for(IParticipants homme : hommeListe){
            dispoH.add(((PersonneSoiree) homme).getTimeWindow());
        }
        manager.ecrireMatriceCPLEX(matrice, nbCol);
        manager.ecrireDispos(dispoH,dispoF);
        dispoF.clear();
        dispoH.clear();
        matrice.clear();
        Solver s = new Solver("src\\main\\opl\\modele2P.lp",nbCol,nbLigne,creneaux);
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
        for(int femme = 0; femme < nbLigne; femme++){
            for(int homme = 0; homme < nbCol; homme++){
                for(int k = 0; k < creneaux; k++) {
                    if(matriceResultat[femme][homme][k] == 1 ) {
                        gestionnaireCrenaux.ajouterRencontre(new Rencontre(hommeListe.get(homme), femmeListe.get(femme), gestionnaireCrenaux.getCrenau(k).getNumeroCrenau()),k);
                    }
                }
            }
        }
        for(int cpt = 0; cpt < gestionnaireCrenaux.getNbCrenaux(); cpt++){
            System.out.println("Crenaux numero : " + gestionnaireCrenaux.getCrenau(cpt).getNumeroCrenau()+ gestionnaireCrenaux.getCrenau(cpt));
        }
    }

    public GestionnaireCreneaux getGestionnaireCrenaux(){
        return this.gestionnaireCrenaux;
    }
}
