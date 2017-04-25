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

    Personne historiqueH;
    Personne historiqueF;

    public DoubleTabNode(String s1, double posx1, double posy1, String s2, double posx2, double posy2){
        hommesList = new TableauPersonnes(s1,posx1,posy1);
        femmesList = new TableauPersonnes(s2,posx2,posy2);

        femmesList.afficheFemme();

        this.getChildren().add(hommesList);
        this.getChildren().add(femmesList);

        historiqueH = null;
        historiqueF = null;

        initListeners();

    }

    public void initListeners(){
        //ecouteur du clic sur le tableview d'hommes
        hommesList.getList().setOnMouseClicked(event -> {
            Personne personneFocus = hommesList.getList().getSelectionModel().getSelectedItem();
            if(personneFocus != null) {
                faireRetard(personneFocus, hommesList);


                historiqueF = femmesList.getList().getSelectionModel().getSelectedItem();
                femmesList.getList().getSelectionModel().clearSelection();

                faireMatriceConflit(personneFocus, femmesList);

            }
        });
        //ecouteur du clic sur le tableview des femmes
        femmesList.getList().setOnMouseClicked(event -> {
            Personne personneFocus = femmesList.getList().getSelectionModel().getSelectedItem();
            if(personneFocus != null) {
                faireRetard(personneFocus,femmesList);

                historiqueH = hommesList.getList().getSelectionModel().getSelectedItem();
                hommesList.getList().getSelectionModel().clearSelection();

                faireMatriceConflit(personneFocus,hommesList);
            }
        });
    }

    private void faireMatriceConflit(Personne personneFocus, TableauPersonnes tp) {
        ArrayList<Pair<Personne, Integer>> matriceConflits = personneFocus.getPersonneSoiree().getConflits();
        for(Pair<Personne, Integer> couple : matriceConflits){
            if(couple.getValue() > 0){
                tp.getList().getSelectionModel().select(couple.getKey());
            }
        }
    }


    @Override
    public void updated(Obs o) {
        System.out.println("-----------");
        if(o instanceof ImportNode){
            ImportNode in =(ImportNode)o;
            hommesList.getList().setItems(in.getHommes());
            femmesList.getList().setItems(in.getFemmes());
        }
        if(o instanceof RetardNode){
            int index = hommesList.getList().getSelectionModel().getSelectedIndex();
        }

        if(o instanceof PreferenceNode){
            System.out.println("MODIFPREFBUTTON ---");
            int value = ((PreferenceNode) o).getValue();
            System.out.println(value);
            System.out.println(historiqueF + " " + historiqueH);
            if(historiqueF != null && historiqueH != null){
                for(Pair<Personne, Integer> pp : historiqueH.getPersonneSoiree().getConflits()){
                    if(pp.getKey().getId() == historiqueF.getId()){
                        historiqueH.getPersonneSoiree().getConflits().remove(pp);
                        break;
                    }
                }
                Pair<Personne, Integer> newP = new Pair<Personne,Integer>(historiqueF,value);
                historiqueH.getPersonneSoiree().getConflits().add(newP);

                for(Pair<Personne, Integer> pp : historiqueF.getPersonneSoiree().getConflits()){
                    if(pp.getKey().getId() == historiqueH.getId()){
                        historiqueF.getPersonneSoiree().getConflits().remove(pp);
                        break;
                    }
                }
                Pair<Personne,Integer> newP2 = new Pair<Personne,Integer>(historiqueH,value);
                historiqueF.getPersonneSoiree().getConflits().add(newP2);

                faireMatriceConflit(historiqueF,hommesList);
                faireMatriceConflit(historiqueH,femmesList);
            }
        }


    }

    public void unselectall() {
        hommesList.getList().getSelectionModel().clearSelection();
        femmesList.getList().getSelectionModel().clearSelection();
    }

    public void faireRetard(Personne personnefocus, TableauPersonnes tp){
        if(RetardNode.getValidRetard()){
            personnefocus.setRetard(RetardNode.getRetard());
            tp.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<Personne,String>("retard"));
            RetardNode.setValidRetard(false);
            tp.getList().refresh();
        }
    }
}
