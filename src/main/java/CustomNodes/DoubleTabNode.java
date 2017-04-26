package CustomNodes;

import cplex.CalculMatrice;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import sample.Affinite;
import sample.Personne;
import sample.PersonneSoiree;

import java.util.ArrayList;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class DoubleTabNode extends Parent implements Observateur {

    TableauPersonnes hommesList;
    TableauPersonnes femmesList;
    Button calcul;
    Personne historiqueH;
    Personne historiqueF;
    CalculMatrice calculateur;
    ArrayList<PersonneSoiree> listePersonnesSoiree;

    public DoubleTabNode(String s1, double posx1, double posy1, String s2, double posx2, double posy2){
        hommesList = new TableauPersonnes(s1,posx1,posy1);
        femmesList = new TableauPersonnes(s2,posx2,posy2);
        calcul = new Button("Calcul des crénaux");

        calcul.setLayoutX(240);
        calcul.setLayoutY(540);
        femmesList.afficheFemme();

        this.getChildren().add(hommesList);
        this.getChildren().add(femmesList);
        this.getChildren().add(calcul);
        cacherCalcul();

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

                historiqueH = personneFocus;
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

                historiqueF = personneFocus;
                historiqueH = hommesList.getList().getSelectionModel().getSelectedItem();
                hommesList.getList().getSelectionModel().clearSelection();

                faireMatriceConflit(personneFocus,hommesList);
            }
        });

        calcul.setOnAction(actionEvent->{
            calculateur.calculerMatriceCPLEX(listePersonnesSoiree);
        });
    }

    private void faireMatriceConflit(Personne personneFocus, TableauPersonnes tp) {
        ArrayList<Affinite> matriceConflits = personneFocus.getPersonneSoiree().getConflits();
        for(Affinite couple : matriceConflits){
            if(couple.getAffinite() > 0){
                tp.getList().getSelectionModel().select(couple.getPersonne());
            }
        }
    }


    @Override
    public void updated(Obs o) {
        System.out.println("-----------");
        if(o instanceof ImportNode){
            ImportNode in =(ImportNode)o;
            afficherCalcul();
            calculateur = new CalculMatrice(in.getCsvManager(), in.getNbLigne(), in.getNbCol());
            listePersonnesSoiree = in.getListePersonneSoiree();
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
            System.out.println(historiqueF);
            System.out.println(historiqueH);
            if(historiqueF != null && historiqueH != null){


                for(Affinite pp : historiqueH.getPersonneSoiree().getConflits()){
                    if(pp.getPersonne().getId() == historiqueF.getId()){
                        pp.setAffinite(value);
                        break;
                    }
                }

                for(Affinite pp : historiqueF.getPersonneSoiree().getConflits()){
                    if(pp.getPersonne().getId() == historiqueH.getId()){
                        pp.setAffinite(value);
                        break;
                    }
                }

            }
        }


    }

    private void afficherCalcul(){
        calcul.setVisible(true);
    }

    private void cacherCalcul(){
        calcul.setVisible(false);
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
