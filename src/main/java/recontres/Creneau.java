package recontres;

import java.util.ArrayList;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Creneau {
    private ArrayList<IMeeting> listeRencontres;
    private int nbRencontres;
    private int numeroCreneau;

    public int getNumeroCrenau() {
        return numeroCreneau;
    }

    public Creneau(int numeroCreneau){
        this.numeroCreneau = numeroCreneau;
        listeRencontres = new ArrayList<>();
    }

    public void ajouterRencontre(IMeeting r){
        listeRencontres.add(r);
        nbRencontres++;
    }

    public int getNbRencontres(){
        return nbRencontres;
    }

    public IMeeting getRencontre(int i){
        return listeRencontres.get(i);
    }

    public String toString(){
        String res = "Creneau " + getNumeroCrenau() + " \n";
        for(int i = 0; i < listeRencontres.size(); i++){
            res+= listeRencontres.get(i).toString();
        }
        return res;
    }
}
