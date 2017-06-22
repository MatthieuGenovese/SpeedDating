package CustomNodes;


import javafx.scene.control.Alert;
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
    Alert alertCalcul;
    boolean dejaCalcule = false;


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
            if(!speedating.getFemmes().isEmpty()) {
                if (!dejaCalcule) {
                    int res = speedating.getCalculateur().calculerMatriceCPLEX();
                    if (res == -1) {
                        alertCalcul.setContentText("Aucune solution n'a été trouvée pour cette configuration !");
                    } else {
                        alertCalcul.setContentText("Les créneaux ont été calculés avec succès et sont disponibles dans l'onglet créneaux !");

                    }
                    alertCalcul.show();
                    dejaCalcule = true;
                    notifier();
                } else {
                    alertCalcul.setContentText("Les créneaux ont déjà été calculés !");
                    alertCalcul.show();
                }
            }
            else{
                alertCalcul.setContentText("Il n'y a aucune personne de chargée !");
                alertCalcul.show();
            }
        });
    }

    private void initElementsGraphiques() {
        calcul = new Button("Calcul des créneaux");
        calcul.setLayoutX(posX);
        calcul.setLayoutY(posY);
        alertCalcul = new Alert(Alert.AlertType.INFORMATION);
        alertCalcul.setTitle("SpeedDating");
        alertCalcul.setHeaderText("Informations");
        alertCalcul.setContentText("Les créneaux ont été calculés avec succès et sont disponibles dans l'onglet créneaux !");

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
