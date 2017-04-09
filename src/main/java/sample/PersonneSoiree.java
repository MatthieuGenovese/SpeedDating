package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class PersonneSoiree {
    //private Date retard;
    private ArrayList<Pair<Personne, Integer>> listeConflits;
    private Personne personne;

    public PersonneSoiree(Personne personne){
        this.personne = personne;
        listeConflits = new ArrayList<>();
        //retard = new Date();
    }

    public Personne getPersonne(){
        return personne;
    }


    /*public void setRetard(Date r) {this.retard = r;}

    public Date getRetard() {return this.retard;}*/

    public ArrayList<Pair<Personne, Integer>> calculerConflits(ArrayList<Personne> listeP){
        //on boucle sur la liste des personnes
        for(Personne p : listeP){
            //on s'exclue soit même (cela n'a pas de sens de calculer un conflit entre this et this
            if(p.getIdSite() != this.personne.getIdSite()){
                //si le critère d'age correspont, on ajoute la pair (p, 1)
                if(p.getAge() >= this.personne.getAgeMin() && p.getAge() <= this.personne.getAgeMax() && !p.getGenre().equalsIgnoreCase(this.personne.getGenre())){
                    listeConflits.add(new Pair<Personne, Integer>(p, 1));
                }
                //sinon on ajoute la pair (p,0)
                else if(!p.getGenre().equalsIgnoreCase(this.personne.getGenre())){
                    listeConflits.add(new Pair<Personne, Integer>(p, 0));
                }
            }
        }
        return listeConflits;
    }

    public ArrayList<Pair<Personne, Integer>> getConflits(){
        return listeConflits;
    }

}
