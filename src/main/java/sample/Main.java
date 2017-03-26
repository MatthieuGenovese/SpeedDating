package sample;

import com.sun.javafx.scene.text.TextLine;
import cplex.Carseq;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class Main extends Application {
    CSVManager csvManager;

    // le groupe principale
    final StackPane root = new StackPane();
    //le groupe de l'onglet import, on ajoutera tous les éléments de l'onglet a ce groupe, et on ajoutera le groupe a l'onglet import
    final Pane groupImport = new Pane();
    final Scene scene = new Scene(root, 800, 600);
    //l'onglet import
    final Tab ongletImport = new Tab("Import");
    //le gestionnaire d'onglet (tous les onglets seront ajoutés sur lui
    final TabPane gestionnaireDonglet = new TabPane();
    //le bouton ... qui ouvre l'explorer pour chercher les fichiers csv
    final Button btnImport= new Button("...");
    final Button btnValiderImport = new Button("Valider");

    final Text textImport = new Text("Fichier à importer :");

    final TextField textFilePath = new TextField();


    final Text textHommes = new Text("Hommes");
    final Text textFemmes = new Text("Femmes");
    //Je crée mes talesviews
    final TableView<Personne> hommesList = new TableView();
    final TableView<Personne> femmesList = new TableView();
    //Je crée mes colonnes
    TableColumn colNomsHommes = new TableColumn("Noms");
    TableColumn colNomsFemmes = new TableColumn("Noms");


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Dating");
        textImport.setLayoutX(scene.getWidth() * 20 / 100);
        textImport.setLayoutY(scene.getHeight() * 10 / 100);
        textFilePath.setMaxWidth(scene.getWidth() * 40 / 100);
        textFilePath.setEditable(false);
        textFilePath.setPrefWidth(200);
        textFilePath.setPrefHeight(20);
        textFilePath.setLayoutX(textImport.getLayoutX() + 100);
        textFilePath.setLayoutY(textImport.getLayoutY() - textFilePath.getPrefHeight());
        btnImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
        btnImport.setLayoutY(textFilePath.getLayoutY());
        btnValiderImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
        btnValiderImport.setLayoutY(textFilePath.getLayoutY() + textFilePath.getPrefHeight()*2);
        btnValiderImport.setVisible(false);

        //Je place le texte du nom de mes deux tableaux hommes et femmes
        textHommes.setLayoutX(scene.getWidth() * 12 / 100);
        textHommes.setLayoutY(scene.getWidth() * 20 / 100);
        textFemmes.setLayoutX(scene.getWidth() * 62 / 100);
        textFemmes.setLayoutY(scene.getWidth() * 20 / 100);

        //Je definis la taille de mes colonnes, multiply par 1 = 100% de la taille du tableview, 0.9 = 90% ...
        colNomsHommes.prefWidthProperty().bind(hommesList.widthProperty().multiply(1));
        colNomsFemmes.prefWidthProperty().bind(femmesList.widthProperty().multiply(1));

        //Je récupère l'élement nom de la classe Personne
        colNomsHommes.setCellValueFactory(new PropertyValueFactory<Personne,String>("nom"));
        colNomsFemmes.setCellValueFactory(new PropertyValueFactory<Personne,String>("nom"));

        //Je règle les propriétés de mon tableview d'hommes
        hommesList.setLayoutX(scene.getWidth() * 10 / 100);
        hommesList.setLayoutY(scene.getHeight() * 30 / 100);
        hommesList.setMaxWidth(200.0);
        hommesList.setMaxHeight(300.0);
        hommesList.setEditable(false);


        //Je règle les propriétés de mon tableview d'hommes
        femmesList.setLayoutX(scene.getWidth() * 60 / 100);
        femmesList.setLayoutY(scene.getHeight() * 30 / 100);
        femmesList.setMaxWidth(200.0);
        femmesList.setMaxHeight(300.0);
        femmesList.setEditable(false);

        //hommesList.

        //on ajoute au bouton "..." une action : Un file chooser (cela ouvre l'explorer), puis on lui applique un filtre pour qu'il n'affiche que les csv

        //ajout de l'onglet "import" au gestionnaire d'onglet
        gestionnaireDonglet.getTabs().setAll(ongletImport);
        //Ajout du bouton "..." et du textImport au groupe import
        groupImport.getChildren().add(textImport);
        groupImport.getChildren().add(textFilePath);
        groupImport.getChildren().add(btnValiderImport);
        groupImport.getChildren().add(btnImport);
        groupImport.getChildren().add(textHommes);
        groupImport.getChildren().add(textFemmes);
        groupImport.getChildren().add(hommesList);
        groupImport.getChildren().add(femmesList);
        //J'ajoute mes colonnes dans les tablesviews
        hommesList.getColumns().add(colNomsHommes);
        femmesList.getColumns().add(colNomsFemmes);
        //assignation du groupe "groupImport" a l'onglet Import
        ongletImport.setContent(groupImport);
        ongletImport.setClosable(false);

        //ajout du gestionnaire d'onglet au root
        root.getChildren().add(gestionnaireDonglet);
        initListeners();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initListeners(){
        //Ajout d'un ecouteur sur le bouton import, il ouvre le gestionnaire de fichiers de l'os en mode "open"
        //Ne prends en compte que les .csv, charge le fichier CSV et extrait les données pour créer une liste de personnes
        btnImport.setOnAction(actionEvent -> {
            final FileChooser dialog = new FileChooser();
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
                ArrayList<Personne> listeChargee = csvManager.getPersonnesFromCSV();
                ObservableList<Personne> hommes = observableArrayList();
                ObservableList<Personne> femmes = observableArrayList();

                for(Personne p : listeChargee){
                    System.out.println(p);
                    if(p.getGenre().equals("M")){
                        hommes.add(p);
                    }
                    else if(p.getGenre().equals("F")) {
                        femmes.add(p);
                    }
                    else {
                        throw new Throwable("Genre illisible ou incorrect !");
                    }
                    hommesList.setItems(hommes);
                    femmesList.setItems(femmes);
                    System.out.println("H : " + hommes + "\nF : " + femmes + "\n");
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        //ajout d'un listener sur le resize de la scene, ecoute le resize du width, mets a jour la position du texte en fonction
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                textImport.setLayoutX((Double) newSceneWidth * 20 / 100);
                textFilePath.setLayoutX(textImport.getLayoutX() + 100);
                btnImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
                btnValiderImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
            }
        });
        //ajout d'un listener sur le resize de la scene, ecoute le resize du height, mets a jour la position du texte en fonction
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                textImport.setLayoutY((Double) newSceneHeight * 10 / 100);
                textFilePath.setLayoutY(textImport.getLayoutY() - textFilePath.getPrefHeight());
                btnImport.setLayoutY(textFilePath.getLayoutY());
                btnValiderImport.setLayoutY(textFilePath.getLayoutY() + textFilePath.getPrefHeight()*2);
            }
        });
    }


    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
