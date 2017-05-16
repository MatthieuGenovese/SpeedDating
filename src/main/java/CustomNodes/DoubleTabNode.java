package CustomNodes;

import conflits.ICompatibility;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import personnes.IParticipants;
import personnes.Personne;
import personnes.PersonneSoiree;
import recontres.TimeWindow;
import utilitaire.Utility;

import java.util.ArrayList;


public class DoubleTabNode extends CustomNode implements Observateur, Obs {
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();
    TableauPersonnes hommesList;
    TableauPersonnes femmesList;
    Button calcul;
    IParticipants historiqueH;
    IParticipants historiqueF;
    Utility utilitaire;

    public DoubleTabNode(String s1, double posx1, double posy1, String s2, double posx2, double posy2, Utility utilitaire){
        hommesList = new TableauPersonnes(s1,posx1,posy1);
        femmesList = new TableauPersonnes(s2,posx2,posy2);
        calcul = new Button("Calcul des créneaux");
        this.utilitaire = utilitaire;
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
            //femmesList.getList().getFocusModel().
            IParticipants personneFocus = hommesList.getList().getSelectionModel().getSelectedItem();
            hommesList.setFocus(true);
            femmesList.setFocus(false);
            if(personneFocus != null) {
                //faireRetard(personneFocus, hommesList);
                historiqueH = personneFocus;
                historiqueF = femmesList.getList().getSelectionModel().getSelectedItem();
                femmesList.getList().getSelectionModel().clearSelection();
                utilitaire.faireMatriceConflit(personneFocus, femmesList);

            }
        });
        //ecouteur du clic sur le tableview des femmes
        femmesList.getList().setOnMouseClicked(event -> {
            IParticipants personneFocus = femmesList.getList().getSelectionModel().getSelectedItem();
            hommesList.setFocus(false);
            femmesList.setFocus(true);
            if(personneFocus != null) {
               // faireRetard(personneFocus,femmesList);
                historiqueF = personneFocus;
                historiqueH = hommesList.getList().getSelectionModel().getSelectedItem();
                hommesList.getList().getSelectionModel().clearSelection();
                utilitaire.faireMatriceConflit(personneFocus,hommesList);
            }
        });

        calcul.setOnAction(actionEvent->{
            utilitaire.getCalculateur().calculerMatriceCPLEX();
            notifier();
        });
    }


    @Override
    public void updated(Obs o) {
        System.out.println("-----------");
        if(o instanceof ImportNode){
            ImportNode in =(ImportNode)o;
            afficherCalcul();
            utilitaire.initCalculateur(in.getNbLigne(), in.getNbCol());
            hommesList.getList().setItems(utilitaire.getHommes());
            femmesList.getList().setItems(utilitaire.getFemmes());
        }
        if(o instanceof RetardNode){
            if(historiqueF != null || historiqueH != null) {
                if( hommesList.isFocus() && hommesList.getList().getFocusModel().getFocusedItem()!= null){
                    PersonneSoiree personneFocus = ((PersonneSoiree) hommesList.getList().getSelectionModel().getSelectedItem());
                    ((TimeWindow) personneFocus.getTimeWindow()).setArrivalSlot(((RetardNode) o).getRetardInt());
                    personneFocus.setRetard(RetardNode.getRetard());
                    hommesList.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<PersonneSoiree,String>("retard"));
                    hommesList.getList().refresh();
                }
                if( femmesList.isFocus() && femmesList.getList().getFocusModel().getFocusedItem()!= null){
                    PersonneSoiree personneFocus = ((PersonneSoiree) femmesList.getList().getSelectionModel().getSelectedItem());
                    ((TimeWindow) personneFocus.getTimeWindow()).setArrivalSlot(((RetardNode) o).getRetardInt());
                    personneFocus.setRetard(RetardNode.getRetard());
                    femmesList.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<PersonneSoiree,String>("retard"));
                    femmesList.getList().refresh();
                }
            }
        }

        if(o instanceof PreferenceNode){
            System.out.println("MODIFPREFBUTTON ---");
            int value = ((PreferenceNode) o).getValue();
            System.out.println(value);
            System.out.println(historiqueF);
            System.out.println(historiqueH);
            if(historiqueF != null && historiqueH != null){


                for(ICompatibility pp : ((PersonneSoiree) historiqueH).getConflits()){
                    if(pp.getPersonneSoiree().getId() == historiqueF.getId()){
                        pp.setAffinite(value);
                        break;
                    }
                }

                for(ICompatibility pp : ((PersonneSoiree) historiqueF).getConflits()){
                    if(pp.getPersonneSoiree().getId() == historiqueH.getId()){
                        pp.setAffinite(value);
                        break;
                    }
                }
                utilitaire.getCalculateur().setFemmeListe(femmesList.getList().getItems());
                utilitaire.getCalculateur().setHommeListe(hommesList.getList().getItems());
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

    /*public void faireRetard(IParticipants personnefocus, TableauPersonnes tp){
        if(RetardNode.getValidRetard()){
            personnefocus.setRetard(RetardNode.getRetard());
            tp.getColRetardHommes().setCellValueFactory(new PropertyValueFactory<PersonneSoiree,String>("retard"));
            RetardNode.setValidRetard(false);
            tp.getList().refresh();
        }
    }*/

    @Override
    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifier() {
        for(Observateur o : observateurs){
            o.updated(this);
        }
    }
}
