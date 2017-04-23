package CustomNodes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Created by Jeremy on 23/04/2017.
 */
public class PreferenceNode extends Parent {

    double posx;
    double posy;

    //Partie metier

    //Partie graphique
    Slider slider;

    Label pdt;
    Label unpeu;
    Label bcp;
    Label passionement;
    Label folie;

    public PreferenceNode(double posx, double posy){
        this.posx = posx;
        this.posy = posy;

        initElementsGraphiques();
        initPositionElementsGraphiques();
        initListeners();

        this.getChildren().add(slider);
        this.getChildren().add(pdt);
        this.getChildren().add(unpeu);
        this.getChildren().add(bcp);
        this.getChildren().add(passionement);
        this.getChildren().add(folie);

    }

    private void initElementsGraphiques() {
        slider = new Slider();
        slider.setMin(0);
        slider.setMax(4);
        //setValue doit prendre la valeur de preference entre deux personne !! TODO
        slider.setValue(0);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(10);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);

        pdt = new Label("PAS DU TOUT");
        unpeu = new Label("Un peu");
        bcp = new Label("Beaucoup");
        passionement = new Label("Passionement");
        folie = new Label("A la folie");

    }

    private void initPositionElementsGraphiques(){
        int ydiff = 10;
        int xdiff = 10;

        slider.setLayoutX(posx);
        slider.setLayoutY(posy);

        pdt.setLayoutX(posx);
        pdt.setLayoutY(posy- ydiff);

        unpeu.setLayoutX(posx + xdiff);
        unpeu.setLayoutY(posy - ydiff);

        bcp.setLayoutX(posx + 2*xdiff);
        bcp.setLayoutY(posy - ydiff);

        passionement.setLayoutX(posx + 3 * xdiff);
        passionement.setLayoutY(posy - ydiff);

        folie.setLayoutX(posx + 4 * xdiff);
        folie.setLayoutY(posy - ydiff);

        cacher();
        afficherL0();

    }

    private void initListeners(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                long d =  Math.round(slider.getValue());
                slider.setValue(d);
                cacher();
                switch ((int)d){
                    case 0 :
                        afficherL0();
                        break;
                    case 1 :
                        afficherL1();
                        break;
                    case 2 :
                        afficherL2();
                        break;
                    case 3 :
                        afficherL3();
                        break;
                    case 4 :
                        afficherL4();
                        break;
                }

            }
        });
    }

    private void cacher(){
        pdt.setVisible(false);
        unpeu.setVisible(false);
        bcp.setVisible(false);
        passionement.setVisible(false);
        folie.setVisible(false);
    }

    private void afficherL0(){
        pdt.setVisible(true);
    }

    private void afficherL1(){
        unpeu.setVisible(true);
    }

    private void afficherL2(){
        bcp.setVisible(true);
    }

    private void afficherL3(){
        passionement.setVisible(true);
    }

    private void afficherL4(){
        folie.setVisible(true);
    }


}
