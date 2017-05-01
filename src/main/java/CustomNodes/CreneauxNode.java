package CustomNodes;

import cplex.CalculMatrice;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import sample.*;

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

    TableColumn prenomh;
    TableColumn prenomf;

    CalculMatrice calculateur;

    public CreneauxNode(double posx, double posy){
        this.posx = posx;
        this.posy = posy;
        initGraphique();
        initPositionElementsGraphiques();
        //J'ajoute mes colonnes dans les tablesviews
        listHommes.getColumns().add(prenomh);
        listFemmes.getColumns().add(prenomf);

        this.getChildren().add(listHommes);
        this.getChildren().add(listFemmes);
        this.getChildren().add(getTitle());
        this.getChildren().add(getNumCreneau());
    }

    public void initGraphique(){
        titleCreneauTab = new Text("Liste des creneaux");
        numCreneau = new Label("");

        listHommes = new TableView();
        listFemmes = new TableView();

        prenomh = new TableColumn("Hommes");
        prenomf = new TableColumn("Femmes");
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
        //J'autorise la multi selection des items
        //listCreneaux.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public Text getTitle(){return this.titleCreneauTab;}

    public Label getNumCreneau(){return this.numCreneau;}

    @Override
    public void updated(Obs o) {
        System.out.println("---------");
        if(o instanceof DoubleTabNode){
            GestionnaireCreneaux gc = ((DoubleTabNode) o).getCalculMatrice().getGestionnaireCrenaux();
            numCreneau.setText("Numéro du creneau : " + gc.getCurrentMeetings().get(0).getCrenau());
            for(IMeeting c : gc.getCurrentMeetings()){
                hommesCreneau.add(c.getHomme());
                femmesCreneau.add(c.getFemme());
            }
            listHommes.setItems(hommesCreneau);
            listFemmes.setItems(femmesCreneau);
        }
    }
}
