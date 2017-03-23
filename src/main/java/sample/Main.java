package sample;

import com.sun.javafx.scene.text.TextLine;
import cplex.Carseq;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

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

    final Text textImport = new Text();

    final TextField textFilePath = new TextField();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Dating");
        textImport.setText("Fichier à importer :");
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

        //on ajoute au bouton "..." une action : Un file chooser (cela ouvre l'explorer), puis on lui applique un filtre pour qu'il n'affiche que les csv

        //ajout de l'onglet "import" au gestionnaire d'onglet
        gestionnaireDonglet.getTabs().setAll(ongletImport);
        //Ajout du bouton "..." et du textImport au groupe import
        groupImport.getChildren().add(textImport);
        groupImport.getChildren().add(textFilePath);
        groupImport.getChildren().add(btnValiderImport);
        groupImport.getChildren().add(btnImport);
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
                ArrayList<Personne> listeChargee = new ArrayList<>();
                listeChargee = csvManager.getPersonnesFromCSV();
                for(Personne p : listeChargee){
                    System.out.println(p);
                }
            } catch (Exception e) {
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
