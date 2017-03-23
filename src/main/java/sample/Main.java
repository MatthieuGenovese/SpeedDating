package sample;

import cplex.Carseq;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    CSVManager csvManager;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Dating");
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
        final TextInputDialog dialogImport = new TextInputDialog();

        //on ajoute au bouton "..." une action : Un file chooser (cela ouvre l'explorer), puis on lui applique un filtre pour qu'il n'affiche que les csv
        btnImport.setOnAction(actionEvent -> {
            final FileChooser dialog = new FileChooser();
            dialog.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers csv", "*.csv"));
            final File file = dialog.showOpenDialog(btnImport.getScene().getWindow());
            if (file != null) {
                try {
                    csvManager = new CSVManager(file.getAbsolutePath());
                    ArrayList<Personne> listeChargee = new ArrayList<>();
                    listeChargee = csvManager.getPersonnesFromCSV();
                    for(Personne p : listeChargee){
                        System.out.println(p);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //ajout de l'onglet "import" au gestionnaire d'onglet
        gestionnaireDonglet.getTabs().setAll(ongletImport);
        //Ajout du bouton "..." au groupe import
        groupImport.getChildren().add(btnImport);
        //assignation du groupe "groupImport" a l'onglet Import
        ongletImport.setContent(groupImport);
        ongletImport.setClosable(false);

        //ajout du gestionnaire d'onglet au root
        root.getChildren().add(gestionnaireDonglet);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
