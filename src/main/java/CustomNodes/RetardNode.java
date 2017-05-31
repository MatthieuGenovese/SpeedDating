package CustomNodes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import utilitaire.SpeedDating;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class RetardNode extends CustomNode implements Obs{
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();
    //Partie metier

    SpeedDating utilitaire;
    private static boolean validRetard = false;
    private int retardInt = 0;
    //Partie Graphique
    Button btnValiderRetard = new Button("OK");

    static Slider retard;

    Label labelR;
    Label labelT;
    Label labelT2;

    public RetardNode(double posx, double posy,SpeedDating u){
        this.utilitaire = u;
        this.posx = posx;
        this.posy = posy;
        initElementsGraphiques();
        initPositionElementsGraphiques();
        initListeners();

        this.getChildren().add(btnValiderRetard);
        this.getChildren().add(retard);
        this.getChildren().add(labelR);
        this.getChildren().add(labelT);
        this.getChildren().add(labelT2);
    }

    public void initElementsGraphiques(){
        btnValiderRetard = new Button("OK");
        labelR = new Label("Retard");
        labelT = new Label("0");
        labelT2 = new Label("min");

        retard  = new Slider(1, 3, 1);
    }

    public void initPositionElementsGraphiques(){
        //Gestion du retard
        retard.setOrientation(Orientation.HORIZONTAL);
        retard.setLayoutX(posx * 39 / 100);
        retard.setLayoutY(posy * 80 / 100);
        //le label au dessus du slider retard
        labelR.setLayoutX(posx * 45 / 100);
        labelR.setLayoutY(posy * 75 / 100);
        //la valeur du slider retard
        labelT.setLayoutX(posx * 44 / 100);
        labelT.setLayoutY(posy * 85 / 100);
        labelT2.setLayoutX(posx * 47 / 100);
        labelT2.setLayoutY(posy * 85 / 100);
        btnValiderRetard.setLayoutX(posx * 52 / 100);
        btnValiderRetard.setLayoutY(posy * 85 / 100);

        retard.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                retardInt = (int) retard.getValue();
                labelT.textProperty().setValue(
                        String.valueOf((int) retard.getValue()));
            }
        });
    }

    public static void setValidRetard(boolean b){validRetard = b;}

    public static boolean getValidRetard(){return validRetard;}

    public void initListeners(){
        btnValiderRetard.setOnAction(actionEvent -> {
            validRetard = true;
            utilitaire.getCurrent().setRetard(getRetard());
            //((TimeWindow) personneFocus.getTimeWindow()).setArrivalSlot(((RetardNode) o).getRetardInt());

            notifier();
        });
    }

    public static int getRetard(){return (int)retard.getValue();}

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

    public int getRetardInt() {
        return retardInt;
    }
}
