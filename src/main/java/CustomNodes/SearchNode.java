package CustomNodes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import personnes.IParticipants;
import utilitaire.Utility;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Jeremy on 19/04/2017.
 */
public class SearchNode extends CustomNode implements Obs {

    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    //Partie metier
    Utility utilitaire;
    DoubleTabNode tableaux;

    //Partie graphique
    TextField txt = new TextField();
    ListView<IParticipants> list = new ListView();


    public SearchNode(double posx, double posy, Utility u){
        this.posx = posx;
        this.posy = posy;
        initElementsGraphiques();
        initListeners();

        this.getChildren().add(txt);
        this.getChildren().add(list);
        cacherTexte();
        cacherlist();

        this.utilitaire = u;
    }

    public void setTableaux(DoubleTabNode tableaux){
        this.tableaux = tableaux;
    }

    private void initListeners() {
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                list.getSelectionModel().clearSelection();
                //tableaux.unselectall();
                handleSearchByKey(oldValue,newValue);
            }
        });

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IParticipants>() {
            int i = 0;
            @Override
            public void changed(ObservableValue<? extends IParticipants> observable, IParticipants oldValue, IParticipants newValue) {
                if(list.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(list.getSelectionModel().getSelectedItem().getPrenom());
                    if(list.getSelectionModel().getSelectedItem().getGenre().equalsIgnoreCase("m")){
                        for(IParticipants p : utilitaire.getHommes()){
                            if(p.equals(list.getSelectionModel().getSelectedItem())){
                                break;
                            }
                            i++;
                        }
                        Platform.runLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                tableaux.hommesList.getList().requestFocus();
                                tableaux.hommesList.getList().getSelectionModel().clearAndSelect(i);
                                tableaux.hommesList.getList().getFocusModel().focus(i);
                                int focus = tableaux.hommesList.getList().getSelectionModel().getFocusedIndex();
                                tableaux.hommesList.getList().scrollTo(focus);
                                list.getSelectionModel().clearSelection();
                                tableaux.femmesList.getList().getSelectionModel().clearSelection();
                                cacherlist();
                                i = 0;
                            }
                        });
                    }
                    else{
                        for(IParticipants p : utilitaire.getFemmes()){
                            if(p.equals(list.getSelectionModel().getSelectedItem())){
                                break;
                            }
                            i++;
                        }
                        Platform.runLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                tableaux.femmesList.getList().requestFocus();
                                tableaux.femmesList.getList().getSelectionModel().clearAndSelect(i);
                                tableaux.femmesList.getList().getFocusModel().focus(i);
                                int focus = tableaux.femmesList.getList().getSelectionModel().getFocusedIndex();
                                tableaux.femmesList.getList().scrollTo(focus);
                                list.getSelectionModel().clearSelection();
                                tableaux.hommesList.getList().getSelectionModel().clearSelection();
                                cacherlist();
                                i = 0;
                            }
                        });
                    }
                }
            }
        });

        list.setCellFactory(lv -> new ListCell<IParticipants>(){
            @Override
            public void updateItem(IParticipants p, boolean empty){
                super.updateItem(p,empty);
                if(!empty){
                    if(p.getGenre().equals("M")){
                        setText("\u2642" + p.affichageNomPrenom());
                    }else{
                        setText("\u2640" + p.affichageNomPrenom());
                    }
                }else{
                    setText(null);
                }
            }
        });

    }

    private void handleSearchByKey(String oldValue, String newValue) {
        if(!utilitaire.getListePersonneSoiree().isEmpty()) {
            if (newValue.length() == 0) {
                cacherlist();
            } else {
                afficherlist();
            }
            if (oldValue != null || (newValue.length() < oldValue.length())) {
                list.setItems((ObservableList<IParticipants>)utilitaire.getListePersonneSoiree());
            }

            newValue = newValue.toLowerCase();

            ObservableList<IParticipants> subentries = observableArrayList();
            for (IParticipants p : list.getItems()) {
                String nom = p.getNom();
                String prenom = p.getPrenom();
                if (nom.toLowerCase().contains(newValue) || prenom.toLowerCase().contains(newValue))
                    subentries.add(p);
            }
            list.setItems(subentries);
        }
    }

    private void initElementsGraphiques() {
        txt.setPromptText("Rechercher...");
        txt.setLayoutX(posx);
        txt.setLayoutY(posy);
        list.setLayoutX(posx);
        list.setLayoutY(posy + 25);
        list.setMaxHeight(80);
        list.maxHeight(5);
    }

    public void cacherlist(){
        list.setDisable(true);
        list.setVisible(false);
    }

    public void afficherlist(){
        list.setDisable(false);
        list.setVisible(true);
    }

    public void cacherTexte(){
        txt.setVisible(false);
    }

    public void afficherTexte(){
        txt.setVisible(true);
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
            o.updated(this); // Idée : Sortir le doubleNode de SearchNode
        }
    }
}
