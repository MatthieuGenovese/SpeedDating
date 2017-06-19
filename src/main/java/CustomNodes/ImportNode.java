package CustomNodes;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import recontres.TimeWindow;
import utilitaire.CSVManager;
import personnes.IParticipants;
import personnes.PersonneSoiree;
import utilitaire.SpeedDating;

import java.io.File;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class ImportNode extends CustomNode implements Obs {

    //obs
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    //Position de ce noeud dans la fenetre

    int nbCol,nbLigne;

    //Propriete metier
    SpeedDating utilitaire;

    //Propriete graphique
    Text textImport;
    TextField textFilePath;

    //le bouton ... qui ouvre l'explorer pour chercher les fichiers csv
    Button btnImport;
    Button btnValiderImport;

    public ImportNode(double posx, double posy, SpeedDating u){
        this.posx = posx;
        this.posy = posy;
        initsElementsGrapiques();
        initPositionElementsGraphiques();
        initListeners();
        nbCol = 0;
        nbLigne = 0;
        this.getChildren().add(getBtnImport());
        this.getChildren().add(getBtnValiderImport());
        this.getChildren().add(getTextImport());
        this.getChildren().add(getTextFilePath());

        this.utilitaire = u;
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
                utilitaire.setCsvManager(new CSVManager(textFilePath.getText()));
                utilitaire.setListeChargee(utilitaire.getCsvManager().getPersonnesFromCSV());
                remplir(utilitaire);
                utilitaire.initCalculateur(this.getNbLigne(), this.getNbCol());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void remplir(SpeedDating u) throws Throwable{
        int cptHomme = 0;
        int cptFemme = 0;
        int maxCreneaux = 0;
        for(IParticipants p : u.getListeChargee()){
            if(p.getTimeWindow().getDepartureSlot() > maxCreneaux){
                maxCreneaux = p.getTimeWindow().getDepartureSlot();
            }
            if(p.getGenre().equals("M")){
                p.initPersonneSoiree(cptHomme);
                u.getListePersonneSoiree().add(p.getPersonneSoiree());
                u.getHommes().add(p.getPersonneSoiree());
                cptHomme++;
            }
            else if(p.getGenre().equals("F")) {
                p.initPersonneSoiree(cptFemme);
                u.getListePersonneSoiree().add(p.getPersonneSoiree());
                u.getFemmes().add(p.getPersonneSoiree());
                cptFemme++;
            }
            else {
                throw new Throwable("Genre illisible ou incorrect !");
            }
            System.out.println(p.toString());
        }
        for(IParticipants p : u.getFemmes()){
            ((PersonneSoiree) p).calculerConflits(u.getHommes());
        }
        for(IParticipants p : u.getHommes()){
            ((PersonneSoiree) p).calculerConflits(u.getFemmes());
        }
        nbCol = cptHomme;
        nbLigne = cptFemme;
        utilitaire.setNbCreneaux(maxCreneaux);
        notifier();
    }

    public int getNbCol() {
        return nbCol;
    }

    public int getNbLigne() {
        return nbLigne;
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
