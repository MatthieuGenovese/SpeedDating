package CustomNodes;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.Date;

/**
 * Created by Jeremy on 06/04/2017.
 */
public class RetardNode extends Parent {

    double posx;
    double posy;

    //Partie metier
    private static boolean validRetard = false;

    //Partie Graphique
    Button btnValiderRetard = new Button("OK");

    static Slider retard;

    Label labelR;
    Label labelT;
    Label labelT2;

    public RetardNode(double posx, double posy){
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

        retard  = new Slider(0, 120, 0);
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
    }

    public static void setValidRetard(boolean b){validRetard = b;}

    public static boolean getValidRetard(){return validRetard;}

    public void initListeners(){
        btnValiderRetard.setOnAction(actionEvent -> {
            validRetard = true;
        });
    }
    public static long getRetard(){return (long)retard.getValue();}
}
