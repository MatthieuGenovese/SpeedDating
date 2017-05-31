package sample;

import CustomNodes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilitaire.ISpeedDating;
import utilitaire.SpeedDating;

import static javafx.collections.FXCollections.observableArrayList;

public class Main extends Application {

    // le groupe principale
    final StackPane root = new StackPane();
    //le groupe de l'onglet import, on ajoutera tous les éléments de l'onglet a ce groupe, et on ajoutera le groupe a l'onglet import
    final Pane groupImport = new Pane();
    final Pane groupCreneaux = new Pane();
    final Scene scene = new Scene(root, 800, 600);
    //l'onglet import
    final Tab ongletImport = new Tab("Import");
    //l'onglet Creneaux
    final Tab ongletCreneaux = new Tab("Creneaux");
    //le gestionnaire d'onglet (tous les onglets seront ajoutés sur lui
    final TabPane gestionnaireDonglet = new TabPane();
    SpeedDating utilitaire = new SpeedDating();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Dating");

        double sw = scene.getWidth();
        double sh = scene.getHeight();

        //ajout de l'onglet "import" au gestionnaire d'onglet
        gestionnaireDonglet.getTabs().setAll(ongletImport, ongletCreneaux);

        ImportNode importnode = new ImportNode(sw,sh,utilitaire);
        groupImport.getChildren().add(importnode);

        DoubleTabNode doubletab = new DoubleTabNode("Hommes",sw,sh,"Femmes",sw,sh, utilitaire);
        groupImport.getChildren().add(doubletab);

        RetardNode retardnode = new RetardNode(sw,sh,utilitaire);
        groupImport.getChildren().add(retardnode);

        SearchNode searchNode = new SearchNode(250,100, utilitaire);
        groupImport.getChildren().add(searchNode);

        PreferenceNode preferenceNode = new PreferenceNode(100,500,utilitaire);
        groupImport.getChildren().add(preferenceNode);

        CalculCreneauxNode calculCreneauxNode = new CalculCreneauxNode(540,540,utilitaire);
        groupImport.getChildren().add(calculCreneauxNode);

        //Ajout du creneauNode à l'interface
        CreneauxNode creneauxNode = new CreneauxNode(250, 100, utilitaire);
        groupCreneaux.getChildren().add(creneauxNode);

        //Ajout des observateurs
        importnode.ajouterObservateur(doubletab);
        importnode.ajouterObservateur(searchNode);
        retardnode.ajouterObservateur(doubletab);
        preferenceNode.ajouterObservateur(doubletab);

        calculCreneauxNode.ajouterObservateur(creneauxNode);



        //assignation du groupe "groupImport" a l'onglet Import
        ongletImport.setContent(groupImport);
        ongletImport.setClosable(false);
        ongletCreneaux.setContent(groupCreneaux);
        ongletCreneaux.setClosable(false);
        searchNode.setTableaux(doubletab);



        //ajout du gestionnaire d'onglet au root
        root.getChildren().add(gestionnaireDonglet);
        initListeners();
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private void initListeners(){
        //TODO ajout d'un listener sur le resize de la scene, ecoute le resize du width, mets a jour la position du texte en fonction
//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                textImport.setLayoutX((Double) newSceneWidth * 20 / 100);
//                textFilePath.setLayoutX(textImport.getLayoutX() + 100);
//                btnImport.setLayoutX(textFilePath.getLayoutX() + textFilePath.getPrefWidth() + 10);
//                btnValiderImport.setLayoutX(btnImport.getLayoutX() + btnImport.getPrefWidth() + 30);
//            }
//        });
//        //ajout d'un listener sur le resize de la scene, ecoute le resize du height, mets a jour la position du texte en fonction
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                textImport.setLayoutY((Double) newSceneHeight * 10 / 100);
//                textFilePath.setLayoutY(textImport.getLayoutY() - textFilePath.getPrefHeight());
//                btnImport.setLayoutY(textFilePath.getLayoutY());
//                btnValiderImport.setLayoutY(textFilePath.getLayoutY());
//            }
//        });

    }
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
