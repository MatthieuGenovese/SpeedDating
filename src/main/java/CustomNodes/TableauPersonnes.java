package CustomNodes;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.IParticipants;
import sample.Personne;
import sample.PersonneSoiree;

import java.util.ArrayList;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class TableauPersonnes extends CustomNode {

    //Partie metier


    //Partie Graphique
    Text textHommes;

    //Je crée mes talesviews
    TableView<IParticipants> list;
    //Je crée mes colonnes
    TableColumn colNomsHommes;
    TableColumn colPrenomsHommes;
    TableColumn colRetardHommes;



    public TableauPersonnes(String genre, double posx, double posy){
        this.posx = posx;
        this.posy = posy;
        initGraphique(genre);
        initPositionElementsGraphiques();
        //J'ajoute mes colonnes dans les tablesviews
        list.getColumns().add(colNomsHommes);
        list.getColumns().add(colPrenomsHommes);
        list.getColumns().add(colRetardHommes);

        //list.setItems(ol);

        this.getChildren().add(list);
        this.getChildren().add(getTextHommes());
    }

    public void initGraphique(String genre){
        textHommes = new Text(genre);
        list = new TableView();
        colNomsHommes = new TableColumn("Noms");
        colPrenomsHommes = new TableColumn("Prenoms");
        colRetardHommes = new TableColumn("Retard");
    }

    public void initPositionElementsGraphiques(){
        //Je place le texte du nom de mes deux tableaux hommes et femmes
        textHommes.setLayoutX(posx * 12 / 100);
        textHommes.setLayoutY(posy * 20 / 100);


        //Je definis la taille de mes colonnes, multiply par 1 = 100% de la taille du tableview, 0.9 = 90% ...
        colNomsHommes.prefWidthProperty().bind(list.widthProperty().multiply(0.5));
//            colPrenomsHommes.prefWidthProperty().bind(femmesList.widthProperty().multiply(0.5));
//            colRetardHommes.prefWidthProperty().bind(femmesList.widthProperty().multiply(0.5));

        //Je récupère l'élement nom de la classe Personne
        colNomsHommes.setCellValueFactory(new PropertyValueFactory<IParticipants,String>("nom"));
        colPrenomsHommes.setCellValueFactory(new PropertyValueFactory<IParticipants,String>("prenom"));
        //Je récupère l'élement retard de la classe Personne
        colRetardHommes.setCellValueFactory(new PropertyValueFactory<IParticipants,String>("retard"));

        //Je règle les propriétés de mon tableview d'hommes
        list.setLayoutX(posx * 10 / 100);
        list.setLayoutY(posy * 30 / 100);
        list.setMaxWidth(200.0);
        list.setMaxHeight(300.0);
        list.setEditable(false);
        //J'autorise la multi selection des items
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Je change la couleur CSS de background de selection
        list.setStyle("-fx-selection-bar-non-focused: #21c3ff;");

        //Je règle les propriétés de mon tableview de femmes

    }

    public double getPosx() {
        return posx;
    }

    public double getPosy() {
        return posy;
    }

    public Text getTextHommes() {
        return textHommes;
    }

    public TableView<IParticipants> getList() {
        return list;
    }

    public TableColumn getColNomsHommes() {
        return colNomsHommes;
    }

    public TableColumn getColPrenomsHommes() {
        return colPrenomsHommes;
    }

    public TableColumn getColRetardHommes() {
        return colRetardHommes;
    }

    public void afficheFemme(){
        textHommes.setLayoutX(posx * 62 / 100);
        textHommes.setLayoutY(posy * 20 / 100);
        list.setLayoutX(posx * 60 / 100);
        list.setLayoutY(posy * 30 / 100);
        list.setMaxWidth(200.0);
        list.setMaxHeight(300.0);
        list.setEditable(false);
        //J'autorise la multiselection des items
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Je change la couleur CSS de background de selection
        list.setStyle("-fx-selection-bar-non-focused: pink;");
    }
}
