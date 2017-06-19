package utilitaire;

import CustomNodes.TableauPersonnes;
import conflits.ICompatibility;
import cplex.CalculMatrice;
import javafx.collections.ObservableList;
import personnes.IParticipants;
import personnes.PersonneSoiree;
import recontres.Creneau;
import recontres.GestionnaireCreneaux;
import recontres.IEventMeetings;
import recontres.IMeeting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import static javafx.collections.FXCollections.observableArrayList;


public class SpeedDating implements ISpeedDating {

    CSVManager csvManager;
    ArrayList<IParticipants> listeChargee;
    ArrayList<IParticipants> listePersonneSoiree ;
    ObservableList<IParticipants> hommes = observableArrayList();
    ObservableList<IParticipants> femmes = observableArrayList();
    GestionnaireCreneaux gc;



    IParticipants selectHomme;
    IParticipants selectF;
    IParticipants current;

    private CalculMatrice calculateur;
    String nomFic;
    String txtDate;


    private String log;

    public SpeedDating(){
        listePersonneSoiree = new ArrayList<>();
        listeChargee = new ArrayList<>();

        selectF = null;
        selectHomme = null;
        current = null;
        log = "";
        txtDate=new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(new Date());
        nomFic = "Rencontres" + txtDate + ".csv";



    }

    public void initCalculateur( int nbLigne, int nbCol){
        calculateur = new CalculMatrice(csvManager, nbLigne, nbCol, hommes, femmes,gc);
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

    public void setLog(String s){
        log+= s;
    }

    public void exporterLog(){
        System.out.println(nomFic);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(nomFic));
            pw.write(log);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNbCreneaux(int nb){
        gc = new GestionnaireCreneaux(nb);
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

    @Override
    public List<IMeeting> getMeetings() {
        return gc.getAllMeetings();
    }

    @Override
    public ArrayList<IParticipants> getParticipants() {
        return this.listePersonneSoiree;
    }

    @Override
    public IEventMeetings getSlots() {
        return this.gc;
    }

    public IParticipants getSelectHomme() {
        return selectHomme;
    }

    public IParticipants getSelectF() {
        return selectF;
    }

    public IParticipants getCurrent() {
        return current;
    }

    public void setSelectHomme(IParticipants selectHomme) {
        this.selectHomme = selectHomme;
    }

    public void setSelectF(IParticipants selectF) {
        this.selectF = selectF;
    }

    public void setCurrent(IParticipants current) {
        this.current = current;
    }

    public GestionnaireCreneaux getGc() {
        return gc;
    }

    public String getNomFic() {
        return nomFic;
    }

    public String getTxtDate() {
        return txtDate;
    }
}
