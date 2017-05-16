package utilitaire;

import CustomNodes.TableauPersonnes;
import conflits.ICompatibility;
import cplex.CalculMatrice;
import javafx.collections.ObservableList;
import personnes.IParticipants;
import personnes.PersonneSoiree;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;


public class Utility {

    CSVManager csvManager;
    ArrayList<IParticipants> listeChargee;
    ArrayList<IParticipants> listePersonneSoiree ;
    ObservableList<IParticipants> hommes = observableArrayList();
    ObservableList<IParticipants> femmes = observableArrayList();

    private CalculMatrice calculateur;

    public Utility(){
        listePersonneSoiree = new ArrayList<>();
        listeChargee = new ArrayList<>();


    }

    public void initCalculateur( int nbLigne, int nbCol){
        calculateur = new CalculMatrice(csvManager, nbLigne, nbCol, hommes, femmes);
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

    public CSVManager getCsvManager() {
        return csvManager;
    }

    public void setCsvManager(CSVManager csvManager) {
        this.csvManager = csvManager;
    }

    public ArrayList<IParticipants> getListeChargee() {
        return listeChargee;
    }

    public ArrayList<IParticipants> getListePersonneSoiree() {
        return listePersonneSoiree;
    }

    public ObservableList<IParticipants> getHommes() {
        return hommes;
    }

    public void setListeChargee(ArrayList<IParticipants> listeChargee) {
        this.listeChargee = listeChargee;
    }

    public void setListePersonneSoiree(ArrayList<IParticipants> listePersonneSoiree) {
        this.listePersonneSoiree = listePersonneSoiree;
    }

    public void setHommes(ObservableList<IParticipants> hommes) {
        this.hommes = hommes;
    }

    public void setFemmes(ObservableList<IParticipants> femmes) {
        this.femmes = femmes;
    }

    public ObservableList<IParticipants> getFemmes() {
        return femmes;
    }
}
