package CustomNodes;

import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import sample.Personne;
import sample.PersonneSoiree;

import java.util.ArrayList;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class DoubleTabNode extends Parent implements Observateur {

    TableauPersonnes hommesList;
    TableauPersonnes femmesList;

    public DoubleTabNode(String s1, double posx1, double posy1, String s2, double posx2, double posy2){
        hommesList = new TableauPersonnes(s1,posx1,posy1);
        femmesList = new TableauPersonnes(s2,posx2,posy2);

        femmesList.afficheFemme();

        this.getChildren().add(hommesList);
        this.getChildren().add(femmesList);
        initListeners();

    }

    public void initListeners(){
        //ecouteur du clic sur le tableview d'hommes
        hommesList.getList().setOnMouseClicked(event -> {
            Personne personneFocus = hommesList.getList().getSelectionModel().getSelectedItem();
            if(personneFocus != null) {
                if(RetardNode.getValidRetard()){
                    personneFocus.setRetard(RetardNode.getRetard());
                    hommesList.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<Personne,String>("retard"));
                    RetardNode.setValidRetard(false);
                }
                femmesList.getList().getSelectionModel().clearSelection();
                ArrayList<Pair<Personne, Integer>> matriceConflits = personneFocus.getPersonneSoiree().getConflits();
                for(Pair<Personne, Integer> couple : matriceConflits){
                    if(couple.getValue().equals(1)){
                        femmesList.getList().getSelectionModel().select(couple.getKey());
                    }
                }
            }
        });

        //ecouteur du clic sur le tableview des femmes
        femmesList.getList().setOnMouseClicked(event -> {
            Personne personneFocus = femmesList.getList().getSelectionModel().getSelectedItem();
            if(personneFocus != null) {
                if(RetardNode.getValidRetard()){
                    personneFocus.setRetard(RetardNode.getRetard());
                    femmesList.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<Personne,String>("retard"));
                    RetardNode.setValidRetard(false);
                }
                hommesList.getList().getSelectionModel().clearSelection();
                ArrayList<Pair<Personne, Integer>> matriceConflits = personneFocus.getPersonneSoiree().getConflits();
                for (Pair<Personne, Integer> couple : matriceConflits) {
                    if (couple.getValue().equals(1)) {
                        hommesList.getList().getSelectionModel().select(couple.getKey());
                    }
                }
            }
        });
    }


    @Override
    public void updated(Obs o) {
        System.out.println("-----------");
        ImportNode in =(ImportNode)o;

        hommesList.getList().setItems(in.getHommes());
        femmesList.getList().setItems(in.getFemmes());

        for(Personne p : hommesList.getList().getItems()){
            System.out.println(p.toString());
        }

    }
}
