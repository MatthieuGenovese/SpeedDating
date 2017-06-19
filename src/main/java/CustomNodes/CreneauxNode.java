package CustomNodes;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import personnes.IParticipants;
import recontres.GestionnaireCreneaux;
import recontres.IMeeting;
import utilitaire.SpeedDating;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Aurélien on 01/05/2017.
 */
public class CreneauxNode extends CustomNode implements Observateur {
    //Partie Graphique
    Text titleCreneauTab;
    Label numCreneau;
    ArrayList<Text> legendes;
    ArrayList<Rectangle> rectangles;
    ArrayList<Color> couleurs;
    //Je crée mes talesviews
    TableView<IParticipants> listHommes;
    TableView<IParticipants> listFemmes;

    Alert alertPrecedent, alertSuivant, alertValiderCreneau;
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
        legendes = new ArrayList<>();
        rectangles = new ArrayList<>();
        couleurs = new ArrayList<>();
        initGraphique();
        initPositionElementsGraphiques();
        initListeners();
        initDialogBox();
        generateColorList();
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
            creneauPrecedent.setVisible(false);
            creneauSuivant.setVisible(false);
            validerCreneau.setVisible(false);
            setColorFactory();
            afficherLegende();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            for(IMeeting c : gc.getAllMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
            numCreneau.setText("Tous les créneaux de la soirée");
        });
        creneauCourant.setOnAction(actionEvent -> {
            hommesCreneau.clear();
            femmesCreneau.clear();

            creneauPrecedent.setVisible(true);
            creneauSuivant.setVisible(true);
            validerCreneau.setVisible(true);
            setNoneColorFactory();
            cacherLegende();
            listFemmes.getItems().clear();
            listHommes.getItems().clear();
            listHommes.refresh();
            gc.setCreneauCourant(creneauActuel);
            for(IMeeting c : gc.getCurrentMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
            numCreneau.setText("Numéro du creneau : " + creneauActuel);
        });
        creneauSuivant.setOnAction(actionEvent -> {
            if(gc.getNbCrenaux() >= creneauGraphiqueActuel + 1){
                hommesCreneau.clear();
                femmesCreneau.clear();
                setNoneColorFactory();
                cacherLegende();
                listFemmes.getItems().clear();
                listHommes.getItems().clear();
                listHommes.refresh();
                creneauGraphiqueActuel++;
                gc.setCreneauCourant(creneauGraphiqueActuel);
                for(IMeeting c : gc.getCurrentMeetings()){
                    hommesCreneau.add(c.getHomme());
                    femmesCreneau.add(c.getFemme());
                }
                numCreneau.setText("Numéro du creneau : " + creneauGraphiqueActuel);
            }
            else{
                alertSuivant.show();
            }
        });
        creneauPrecedent.setOnAction(actionEvent -> {
            if(creneauGraphiqueActuel - 1 >= 1){
                hommesCreneau.clear();
                femmesCreneau.clear();
                setNoneColorFactory();
                cacherLegende();
                listFemmes.getItems().clear();
                listHommes.getItems().clear();
                listHommes.refresh();
                creneauGraphiqueActuel--;
                gc.setCreneauCourant(creneauGraphiqueActuel);
                for(IMeeting c : gc.getCurrentMeetings()){
                    hommesCreneau.add(c.getHomme());
                    femmesCreneau.add(c.getFemme());
                }
                numCreneau.setText("Numéro du creneau : " + creneauGraphiqueActuel);
            }
            else{
                alertPrecedent.show();
            }
        });
        validerCreneau.setOnAction(actionEvent -> {
            setNoneColorFactory();
            cacherLegende();
            if(creneauActuel <= gc.getNbCrenaux()) {
                gc.setCreneauCourant(creneauActuel);
                utilitaire.setLog(gc.getCreneau(creneauActuel - 1) + "\n");
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
                alertValiderCreneau.setContentText("Le créneau " + creneauActuel + " a été validé avec succès !");
                alertValiderCreneau.show();
                creneauActuel++;
                creneauGraphiqueActuel = creneauActuel-1;
            }
            else{
                alertValiderCreneau.setContentText("Tous les créneaux ont été validés !" + "\n" +
                "Le fichier " +utilitaire.getNomFic() +" est prêt.");
                alertValiderCreneau.show();
            }
        });

    }

    public void initDialogBox(){
        alertSuivant = new Alert(Alert.AlertType.INFORMATION);
        alertPrecedent = new Alert(Alert.AlertType.INFORMATION);
        alertValiderCreneau = new Alert(Alert.AlertType.INFORMATION);
        alertSuivant.setTitle("SpeedDating");
        alertSuivant.setHeaderText("Informations");
        alertSuivant.setContentText("Il n'y a plus de créneaux suivants !");
        alertPrecedent.setTitle("SpeedDating");
        alertPrecedent.setHeaderText("Informations");
        alertPrecedent.setContentText("Il n'y a plus de créneaux précédents !");
        alertValiderCreneau.setTitle("SpeedDating");
        alertValiderCreneau.setHeaderText("Informations");

    }

    public void initLegende(){
        try {
            Class couleur = Class.forName("javafx.scene.paint.Color");
            if (couleur != null) {
                Field[] field = couleur.getFields();
                for (int i = 0; i < field.length; i++) {
                    Field f = field[i];
                    Object obj = f.get(null);
                   /* if(obj instanceof Color){
                        couleurs.add((Color) obj);
                    }*/
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < gc.getNbCrenaux(); i++){
            legendes.add(new Text("Creneau " + (i+1)));
            if(i == 0){
                legendes.get(i).setLayoutX(listHommes.getLayoutX()-100);
                legendes.get(i).setLayoutY(listHommes.getLayoutY()+50);
            }
            else{
                legendes.get(i).setLayoutX(legendes.get(0).getLayoutX());
                legendes.get(i).setLayoutY(legendes.get(i-1).getLayoutY()+20);
            }
            rectangles.add(new Rectangle(20,20));
            rectangles.get(i).setFill(couleurs.get(((i+11)%couleurs.size())));
            rectangles.get(i).setLayoutX(legendes.get(i).getLayoutX()+70);
            rectangles.get(i).setLayoutY(legendes.get(i).getLayoutY()-10);
            this.getChildren().add(legendes.get(i));
            this.getChildren().add(rectangles.get(i));
        }
    }

    public void afficherLegende(){
        if(!legendes.get(0).isVisible()){
            for(int i = 0; i < legendes.size(); i++){
                legendes.get(i).setVisible(true);
                rectangles.get(i).setVisible(true);
            }
        }
    }

    public void cacherLegende(){
        if(legendes.get(0).isVisible()){
            for(int i = 0; i < legendes.size(); i++){
                legendes.get(i).setVisible(false);
                rectangles.get(i).setVisible(false);
            }
        }
    }

    public Text getTitle(){return this.titleCreneauTab;}

    public void setColorFactory(){
        ArrayList<IMeeting> allMettings = (ArrayList<IMeeting>) gc.getAllMeetings();
        prenomf.setCellFactory(column -> {
            return new TableCell<IParticipants, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        int index = getTableRow().getIndex();
                        for(int i = 0; i < gc.getNbCrenaux(); i++){
                            if(allMettings.get(index).getCrenau() == i+1){
                                setTextFill(couleurs.get((i+11)%couleurs.size()));
                            }
                        }
                        setText(item);
                    }
                }
            };
        });
        prenomh.setCellFactory(column -> {
            return new TableCell<IParticipants, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        int index = getTableRow().getIndex();
                        for(int i = 0; i < gc.getNbCrenaux(); i++){
                            if(allMettings.get(index).getCrenau() == i+1){
                                setTextFill(couleurs.get((i+11)%couleurs.size()));
                            }
                        }
                        setText(item);
                    }
                }
            };
        });
    }

    public void setNoneColorFactory(){
        prenomf.setCellFactory(column -> {
            return new TableCell<IParticipants, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                    }
                }
            };
        });
        prenomh.setCellFactory(column -> {
            return new TableCell<IParticipants, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                    }
                }
            };
        });
    }

    public Label getNumCreneau(){return this.numCreneau;}

    private void generateColorList(){
        couleurs.add(Color.DARKRED);
        couleurs.add(Color.LIMEGREEN);
        couleurs.add(Color.DODGERBLUE);
        couleurs.add(Color.MAGENTA);
        couleurs.add(Color.ORANGERED);
        couleurs.add(Color.DARKSEAGREEN);
        couleurs.add(Color.ORANGE);
        couleurs.add(Color.CHOCOLATE);
        couleurs.add(Color.GOLDENROD);
        couleurs.add(Color.INDIGO);
    }

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
            initLegende();
            cacherLegende();
        }
    }
}
