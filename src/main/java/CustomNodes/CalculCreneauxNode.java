package CustomNodes;


import javafx.scene.control.Button;
import utilitaire.SpeedDating;

import java.util.ArrayList;

public class CalculCreneauxNode extends CustomNode implements Obs {

    //partie logique
    private double posX;
    private double posY;
    SpeedDating speedating;
    ArrayList<Observateur> observateurs;

    //Partie graphique
    Button calcul;


    public CalculCreneauxNode(double posx, double posy, SpeedDating sd){
        this.posX = posx;
        this.posY = posy;
        this.speedating = sd;
        this.observateurs = new ArrayList<Observateur>();

        initElementsGraphiques();
        initListeners();

        this.getChildren().add(calcul);

    }

    private void initListeners(){
        calcul.setOnAction(actionEvent->{
            speedating.getCalculateur().calculerMatriceCPLEX();
            notifier();
        });
    }

    private void initElementsGraphiques() {
        calcul = new Button("Calcul des créneaux");
        calcul.setLayoutX(posX);
        calcul.setLayoutY(posY);

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
            o.updated(this);
        }
    }
}
