package CustomNodes;

import conflits.ICompatibility;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import personnes.IParticipants;
import personnes.PersonneSoiree;
import recontres.TimeWindow;
import utilitaire.SpeedDating;

import java.util.ArrayList;


public class DoubleTabNode extends CustomNode implements Observateur {
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();
    TableauPersonnes hommesList;
    TableauPersonnes femmesList;
    SpeedDating utilitaire;

    public DoubleTabNode(String s1, double posx1, double posy1, String s2, double posx2, double posy2, SpeedDating utilitaire){
        hommesList = new TableauPersonnes(s1,posx1,posy1);
        femmesList = new TableauPersonnes(s2,posx2,posy2);
        this.utilitaire = utilitaire;

        femmesList.afficheFemme();

        this.getChildren().add(hommesList);
        this.getChildren().add(femmesList);

        initListeners();

    }

    public void initListeners(){
        //ecouteur du clic sur le tableview d'hommes
        hommesList.getList().setOnMouseClicked(event -> {
            IParticipants personneFocus = hommesList.getList().getSelectionModel().getSelectedItem();
            utilitaire.setCurrent(personneFocus);
            hommesList.setFocus(true);
            femmesList.setFocus(false);
            if(personneFocus != null) {
                //faireRetard(personneFocus, hommesList);
                utilitaire.setSelectHomme(personneFocus);
                utilitaire.setSelectF(femmesList.getList().getSelectionModel().getSelectedItem());
                femmesList.getList().getSelectionModel().clearSelection();
                utilitaire.faireMatriceConflit(personneFocus, femmesList);
            }
        });
        //ecouteur du clic sur le tableview des femmes
        femmesList.getList().setOnMouseClicked(event -> {
            IParticipants personneFocus = femmesList.getList().getSelectionModel().getSelectedItem();
            utilitaire.setCurrent(personneFocus);
            hommesList.setFocus(false);
            femmesList.setFocus(true);
            if(personneFocus != null) {
               // faireRetard(personneFocus,femmesList);
                utilitaire.setSelectF(personneFocus);
                utilitaire.setSelectHomme(hommesList.getList().getSelectionModel().getSelectedItem());
                hommesList.getList().getSelectionModel().clearSelection();
                utilitaire.faireMatriceConflit(personneFocus,hommesList);
            }
        });


    }


    @Override
    public void updated(Obs o) {
        System.out.println("-----------");
        hommesList.getList().setItems(utilitaire.getHommes());
        femmesList.getList().setItems(utilitaire.getFemmes());
        //hommesList.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<PersonneSoiree,String>("retard"));

        hommesList.getList().refresh();
        femmesList.getList().refresh();

    }


    public void unselectall() {
        hommesList.getList().getSelectionModel().clearSelection();
        femmesList.getList().getSelectionModel().clearSelection();
    }

    /*public void faireRetard(IParticipants personnefocus, TableauPersonnes tp){
        if(RetardNode.getValidRetard()){
            personnefocus.setRetard(RetardNode.getRetard());
            tp.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<PersonneSoiree,String>("retard"));
            RetardNode.setValidRetard(false);
            tp.getList().refresh();
        }
    }*/

}
