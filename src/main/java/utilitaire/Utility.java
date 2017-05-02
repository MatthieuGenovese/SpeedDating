package utilitaire;

import CustomNodes.TableauPersonnes;
import conflits.ICompatibility;
import cplex.CalculMatrice;
import javafx.collections.ObservableList;
import personnes.IParticipants;
import personnes.PersonneSoiree;

import java.util.ArrayList;

/**
 * Created by Matthieu on 02/05/2017.
 */
public class Utility {
    private CalculMatrice calculateur;

    public void initCalculateur(CSVManager manager, int nbLigne, int nbCol, ObservableList<IParticipants> hommes, ObservableList<IParticipants> femmes){
        calculateur = new CalculMatrice(manager, nbLigne, nbCol, hommes, femmes);
    }
    public CalculMatrice getCalculateur() {
        return calculateur;
    }

    public void faireMatriceConflit(IParticipants personneFocus, TableauPersonnes tp) {
        ArrayList<ICompatibility> matriceConflits = ((PersonneSoiree) personneFocus).getConflits();
        for(ICompatibility couple : matriceConflits){
            if(couple.getAffinite() > 0){
                tp.getList().getSelectionModel().select(couple.getPersonneSoiree());
            }
        }
    }
}
