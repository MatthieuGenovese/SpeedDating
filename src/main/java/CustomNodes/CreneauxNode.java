package CustomNodes;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import personnes.IParticipants;
import recontres.GestionnaireCreneaux;
import recontres.IMeeting;
import utilitaire.SpeedDating;

import java.io.File;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Aurélien on 01/05/2017.
 */
public class CreneauxNode extends CustomNode implements Observateur {
    //Partie Graphique
    Text titleCreneauTab;
    Label numCreneau;

    //Je crée mes talesviews
    TableView<IParticipants> listHommes;
    TableView<IParticipants> listFemmes;

    ObservableList<IParticipants> hommesCreneau = observableArrayList();
    ObservableList<IParticipants> femmesCreneau = observableArrayList();
    //Je crée mes colonnes
    GestionnaireCreneaux gc;
    TableColumn prenomh;
    TableColumn prenomf;
    SpeedDating utilitaire;
    Button allCreneaux, creneauCourant, creneauSuivant, creneauPrecedent, validerCreneau;
    int creneauActuel, creneauGraphiqueActuel;

    public CreneauxNode(double posx, double posy, SpeedDating utilitaire){
        this.posx = posx;
        this.posy = posy;
        initGraphique();
        initPositionElementsGraphiques();
        initListeners();
        //J'ajoute mes colonnes dans les tablesviews
        listHommes.getColumns().add(prenomh);
        listFemmes.getColumns().add(prenomf);
        this.utilitaire = utilitaire;
        this.getChildren().add(listHommes);
        this.getChildren().add(listFemmes);
        this.getChildren().add(getTitle());
        this.getChildren().add(getNumCreneau());
        this.getChildren().add(allCreneaux);
        this.getChildren().add(creneauCourant);
        this.getChildren().add(creneauSuivant);
        this.getChildren().add(creneauPrecedent);
        this.getChildren().add(validerCreneau);
    }

    public void initGraphique(){
        titleCreneauTab = new Text("Liste des creneaux");
        numCreneau = new Label("");

        listHommes = new TableView();
        listFemmes = new TableView();

        prenomh = new TableColumn("Hommes");
        prenomf = new TableColumn("Femmes");
        prenomh.setSortable(false);
        prenomf.setSortable(false);


        allCreneaux = new Button("Afficher tous les créneaux");
        creneauCourant = new Button("Créneau courant");
        creneauPrecedent = new Button("Créneau précédent");
        creneauSuivant = new Button("Créneau suivant");
        validerCreneau = new Button("Valider créneau");
    }

    public void initPositionElementsGraphiques(){
        //Je place le texte du nom de mes deux tableaux hommes et femmes
        titleCreneauTab.setLayoutX(posx * 12 / 100);
        titleCreneauTab.setLayoutY(posy * 20 / 100);

        numCreneau.setLayoutX(posx * 100 / 100);
        numCreneau.setLayoutY(posy * 30 / 100);

        //Je definis la taille de mes colonnes, multiply par 1 = 100% de la taille du tableview, 0.9 = 90% ...
        prenomh.prefWidthProperty().bind(listHommes.widthProperty().multiply(1));
        prenomf.prefWidthProperty().bind(listFemmes.widthProperty().multiply(1));
        prenomh.setCellValueFactory(new PropertyValueFactory<IParticipants,String>("prenom"));
        prenomf.setCellValueFactory(new PropertyValueFactory<IParticipants,String>("prenom"));


        //Je règle les propriétés de mon tableview de creneaux
        listHommes.setLayoutX(posx * 64 / 100);
        listHommes.setLayoutY(posy * 75 / 100);
        listHommes.setMaxWidth(250.0);
        listHommes.setMaxHeight(500.0);
        listHommes.setEditable(false);

        listFemmes.setLayoutX(posx * 162 / 100);
        listFemmes.setLayoutY(posy * 75 / 100);
        listFemmes.setMaxWidth(250.0);
        listFemmes.setMaxHeight(500.0);
        listFemmes.setEditable(false);

        allCreneaux.setLayoutX(posx );
        allCreneaux.setLayoutY(posy * 5);
        creneauCourant.setLayoutX(allCreneaux.getLayoutX());
        creneauCourant.setLayoutY(allCreneaux.getLayoutY()+ 30);
        creneauSuivant.setLayoutX(creneauCourant.getLayoutX()+200);
        creneauSuivant.setLayoutY(creneauCourant.getLayoutY());
        creneauPrecedent.setLayoutX(allCreneaux.getLayoutX()+200);
        creneauPrecedent.setLayoutY(allCreneaux.getLayoutY());
        validerCreneau.setLayoutX(creneauPrecedent.getLayoutX()+200);
        validerCreneau.setLayoutY(creneauPrecedent.getLayoutY());
        //J'autorise la multi selection des items
        //listCreneaux.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void initListeners(){
        allCreneaux.setOnAction(actionEvent -> {
            hommesCreneau.clear();
            femmesCreneau.clear();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            for(IMeeting c : gc.getAllMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
        });
        creneauCourant.setOnAction(actionEvent -> {
            hommesCreneau.clear();
            femmesCreneau.clear();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            gc.setCreneauCourant(creneauActuel);
            for(IMeeting c : gc.getCurrentMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
        });
        creneauSuivant.setOnAction(actionEvent -> {
            hommesCreneau.clear();
            femmesCreneau.clear();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            if(gc.getNbCrenaux() >= creneauGraphiqueActuel + 1){
                creneauGraphiqueActuel++;
                gc.setCreneauCourant(creneauGraphiqueActuel);
                for(IMeeting c : gc.getCurrentMeetings()){
                    hommesCreneau.add(c.getHomme());
                    femmesCreneau.add(c.getFemme());
                }
                numCreneau.setText("Numéro du creneau : " + creneauGraphiqueActuel);
            }
        });
        creneauPrecedent.setOnAction(actionEvent -> {
            hommesCreneau.clear();
            femmesCreneau.clear();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            if(creneauGraphiqueActuel - 1 >= 1){
                creneauGraphiqueActuel--;
                gc.setCreneauCourant(creneauGraphiqueActuel);
                for(IMeeting c : gc.getCurrentMeetings()){
                    hommesCreneau.add(c.getHomme());
                    femmesCreneau.add(c.getFemme());
                }
                numCreneau.setText("Numéro du creneau : " + creneauGraphiqueActuel);
            }
        });
        validerCreneau.setOnAction(actionEvent -> {
            if(creneauActuel <= gc.getNbCrenaux()) {
                gc.setCreneauCourant(creneauActuel);
                utilitaire.setLog(gc.getCreneau(creneauActuel - 1) + "\n");
                creneauActuel++;
                creneauGraphiqueActuel++;
                utilitaire.exporterLog();
                hommesCreneau.clear();
                femmesCreneau.clear();
                listFemmes.getItems().clear();
                listHommes.getItems().clear();
                listHommes.refresh();
                gc.setCreneauCourant(creneauActuel);
                for(IMeeting c : gc.getCurrentMeetings()){
                    hommesCreneau.add(c.getHomme());
                    femmesCreneau.add(c.getFemme());
                }
                numCreneau.setText("Numéro du creneau : " + creneauActuel);
            }
        });

    }

    public Text getTitle(){return this.titleCreneauTab;}

    public Label getNumCreneau(){return this.numCreneau;}

    @Override
    public void updated(Obs o) {
        System.out.println("---------");
        if(o instanceof CalculCreneauxNode){
            gc = utilitaire.getCalculateur().getGestionnaireCrenaux();
            numCreneau.setText("Numéro du creneau : " + gc.getCreneauCourant());
            creneauActuel = gc.getCreneauCourant();
            creneauGraphiqueActuel = gc.getCreneauCourant();
            for(IMeeting c : gc.getCurrentMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
            listHommes.setItems(hommesCreneau);
            listFemmes.setItems(femmesCreneau);
        }
    }
}
