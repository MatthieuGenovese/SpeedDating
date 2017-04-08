package CustomNodes;

import cplex.CalculMatrice;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.CSVManager;
import sample.Personne;
import sample.PersonneSoiree;

import java.io.File;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class ImportNode extends Parent implements Obs {

    //obs
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    //Position de ce noeud dans la fenetre
    double posx;
    double posy;
    int nbCol;

    //Propriete metier
    CSVManager csvManager;
    ArrayList<Personne> listeChargee;
    ArrayList<PersonneSoiree> listePersonneSoiree;
    ObservableList<Personne> hommes = observableArrayList();
    CalculMatrice calculateur;
    ObservableList<Personne> femmes = observableArrayList();

    //Propriete graphique
    Text textImport;
    TextField textFilePath;

    //le bouton ... qui ouvre l'explorer pour chercher les fichiers csv
    Button btnImport;
    Button btnValiderImport;

    public ImportNode(double posx, double posy){
        this.posx = posx;
        this.posy = posy;
        initsElementsGrapiques();
        initPositionElementsGraphiques();
        initListeners();
        nbCol = 0;
        this.getChildren().add(getBtnImport());
        this.getChildren().add(getBtnValiderImport());
        this.getChildren().add(getTextImport());
        this.getChildren().add(getTextFilePath());
    }

    public void initsElementsGrapiques(){
        textImport  = new Text("Fichier à importer :");
        textFilePath = new TextField();

        btnImport = new Button("...");
        btnValiderImport = new Button("Ouvrir");
    }

    public void initPositionElementsGraphiques(){
        textImport.setLayoutX(posx * 20 / 100);
        textImport.setLayoutY(posy * 10 / 100);
        textFilePath.setMaxWidth(posx * 40 / 100);
        textFilePath.setEditable(false);
        textFilePath.setPrefWidth(200);
        textFilePath.setPrefHeight(20);
        textFilePath.setLayoutX(textImport.getLayoutX() + 100);
        textFilePath.setLayoutY(textImport.getLayoutY() - textFilePath.getPrefHeight());
        btnImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
        btnImport.setLayoutY(textFilePath.getLayoutY());
        btnValiderImport.setLayoutX(btnImport.getLayoutX() + btnImport.getPrefWidth() + 30);
        btnValiderImport.setLayoutY(textFilePath.getLayoutY());
        btnValiderImport.setVisible(false);
    }

    public void initListeners(){
        //Ajout d'un ecouteur sur le bouton import, il ouvre le gestionnaire de fichiers de l'os en mode "open"
        //Ne prends en compte que les .csv, charge le fichier CSV et extrait les données pour créer une liste de personnes
        btnImport.setOnAction(actionEvent -> {
            final FileChooser dialog = new FileChooser();
            //on ajoute au bouton "..." une action : Un file chooser (cela ouvre l'explorer), puis on lui applique un filtre pour qu'il n'affiche que les csv
            dialog.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers csv", "*.csv"));
            final File file = dialog.showOpenDialog(btnImport.getScene().getWindow());
            if (file != null) {
                textFilePath.setText(file.getAbsolutePath());
                btnValiderImport.setVisible(true);
            }
        });

        btnValiderImport.setOnAction(actionEvent -> {
            try {
                csvManager = new CSVManager(textFilePath.getText());
                listeChargee = new ArrayList<>();
                listeChargee = csvManager.getPersonnesFromCSV();
                remplir();
                calculateur.calculerMatriceCPLEX(listePersonneSoiree);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void remplir() throws Throwable{
        int cptHomme = 0;
        int cptFemme = 0;
        listePersonneSoiree = new ArrayList<>();
        for(Personne p : listeChargee){
            p.getPersonneSoiree().calculerConflits(listeChargee);
            listePersonneSoiree.add(p.getPersonneSoiree());
            if(p.getGenre().equals("M")){
                p.setId(cptHomme);
                hommes.add(p);
                cptHomme++;
            }
            else if(p.getGenre().equals("F")) {
                p.setId(cptFemme);
                femmes.add(p);
                cptFemme++;
            }
            else {
                throw new Throwable("Genre illisible ou incorrect !");
            }
            System.out.println(p.toString());
        }
        nbCol = cptFemme;
        calculateur = new CalculMatrice(csvManager, nbCol);
        notifier();
    }



    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public CSVManager getCsvManager() {
        return csvManager;
    }

    public void setCsvManager(CSVManager csvManager) {
        this.csvManager = csvManager;
    }

    public ArrayList<Personne> getListeChargee() {
        return listeChargee;
    }

    public ArrayList<PersonneSoiree> getListePersonneSoiree(){
        return listePersonneSoiree;
    }

    public void setListeChargee(ArrayList<Personne> listeChargee) {
        this.listeChargee = listeChargee;
    }

    public Text getTextImport() {
        return textImport;
    }

    public void setTextImport(Text textImport) {
        this.textImport = textImport;
    }

    public TextField getTextFilePath() {
        return textFilePath;
    }

    public void setTextFilePath(TextField textFilePath) {
        this.textFilePath = textFilePath;
    }

    public Button getBtnImport() {
        return btnImport;
    }

    public void setBtnImport(Button btnImport) {
        this.btnImport = btnImport;
    }

    public Button getBtnValiderImport() {
        return btnValiderImport;
    }

    public void setBtnValiderImport(Button btnValiderImport) {
        this.btnValiderImport = btnValiderImport;
    }

    public ObservableList<Personne> getHommes() {
        return hommes;
    }

    public ObservableList<Personne> getFemmes() {
        return femmes;
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    @Override
    public void notifier() {
        for(Observateur o : observateurs){
            o.updated(this);
        }
    }
}
