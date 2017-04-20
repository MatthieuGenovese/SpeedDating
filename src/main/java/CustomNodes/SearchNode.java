package CustomNodes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.Personne;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Jeremy on 19/04/2017.
 */
public class SearchNode extends Parent implements Observateur {

    double posx;
    double posy;

    //Partie metier
    ObservableList<Personne> entries = observableArrayList();
    ListView<Personne> list = new ListView();
    DoubleTabNode tableaux;

    //Partie graphique
    TextField txt = new TextField();

    public SearchNode(double posx, double posy){
        this.posx = posx;
        this.posy = posy;
        initElementsGraphiques();
        initListeners();

        this.getChildren().add(txt);
        this.getChildren().add(list);

        cacherlist();
    }

    public void setTableaux(DoubleTabNode tableaux){
        this.tableaux = tableaux;
    }

    private void initListeners() {
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                list.getSelectionModel().clearSelection();
                handleSearchByKey(oldValue,newValue);
            }
        });

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Personne>() {
            int i = 0;
            @Override
            public void changed(ObservableValue<? extends Personne> observable, Personne oldValue, Personne newValue) {
                if(list.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(list.getSelectionModel().getSelectedItem().getPrenom());
                    if(list.getSelectionModel().getSelectedItem().getGenre().equalsIgnoreCase("m")){
                        for(Personne p : tableaux.hommesList.getList().getItems()){
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
                                tableaux.hommesList.getList().getSelectionModel().select(i);
                                tableaux.hommesList.getList().getFocusModel().focus(i);
                                i = 0;
                            }
                        });
                    }
                    else{
                        for(Personne p : tableaux.femmesList.getList().getItems()){
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
                                tableaux.femmesList.getList().getSelectionModel().select(i);
                                tableaux.femmesList.getList().getFocusModel().focus(i);
                                i = 0;
                            }
                        });
                    }
                }
            }
        });
    }

    private void handleSearchByKey(String oldValue, String newValue) {
        if(!entries.isEmpty()) {
            if (newValue.length() == 0) {
                cacherlist();
            } else {
                afficherlist();
            }
            if (oldValue != null || (newValue.length() < oldValue.length())) {
                list.setItems(entries);
            }

            newValue = newValue.toLowerCase();

            ObservableList<Personne> subentries = observableArrayList();
            for (Personne p : list.getItems()) {
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

    @Override
    public void updated(Obs o) {
        ImportNode in = (ImportNode) o;
        entries.setAll(in.getHommes());
        entries.addAll(in.getFemmes());
    }

    public void cacherlist(){
        list.setDisable(true);
        list.setVisible(false);
    }

    public void afficherlist(){
        list.setDisable(false);
        list.setVisible(true);
    }
}
