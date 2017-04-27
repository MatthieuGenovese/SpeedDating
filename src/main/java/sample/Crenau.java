package sample;

import java.util.ArrayList;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Crenau {
    private ArrayList<IMeeting> listeRencontres;
    private int nbRencontres;
    private int numeroCrenau;

    public int getNumeroCrenau() {
        return numeroCrenau;
    }

    public Crenau(int numeroCrenau){
        this.numeroCrenau = numeroCrenau;
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
        String res = "";
        for(int i = 0; i < listeRencontres.size(); i++){
            res+= "//" + listeRencontres.get(i).toString();
        }
        return res;
    }
}
