package sample;

import CustomNodes.DoubleTabNode;
import CustomNodes.ImportNode;
import CustomNodes.RetardNode;
import CustomNodes.TableauPersonnes;
import com.sun.javafx.scene.text.TextLine;
import cplex.Carseq;
import cplex.Solver;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Orientation;
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
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class Main extends Application {

    // le groupe principale
    final StackPane root = new StackPane();
    //le groupe de l'onglet import, on ajoutera tous les éléments de l'onglet a ce groupe, et on ajoutera le groupe a l'onglet import
    final Pane groupImport = new Pane();
    final Scene scene = new Scene(root, 800, 600);
    //l'onglet import
    final Tab ongletImport = new Tab("Import");
    //le gestionnaire d'onglet (tous les onglets seront ajoutés sur lui
    final TabPane gestionnaireDonglet = new TabPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Dating");
        Solver s = new Solver("src\\main\\opl\\model",3,3);
        double sw = scene.getWidth();
        double sh = scene.getHeight();
        try {
            s.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ajout de l'onglet "import" au gestionnaire d'onglet
        gestionnaireDonglet.getTabs().setAll(ongletImport);

        ImportNode importnode = new ImportNode(sw,sh);
        groupImport.getChildren().add(importnode);

        DoubleTabNode doubletab = new DoubleTabNode("Hommes",sw,sh,"Femmes",sw,sh);
        importnode.ajouterObservateur(doubletab);
        groupImport.getChildren().add(doubletab);

        RetardNode retardnode = new RetardNode(sw,sh);
        groupImport.getChildren().add(retardnode);


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
