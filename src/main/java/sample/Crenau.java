package sample;

import java.util.ArrayList;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Crenau {
    private ArrayList<Rencontre> listeRencontres;

    public Crenau(){
        listeRencontres = new ArrayList<>();
    }

    public void ajouterRencontre(Rencontre r){
        listeRencontres.add(r);
    }

    public Rencontre getRencontre(int i){
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
