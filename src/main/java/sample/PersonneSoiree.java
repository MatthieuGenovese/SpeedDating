package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class PersonneSoiree extends Personne{
    //private Date retard;
    private ArrayList<Affinite> listeConflits;

    public PersonneSoiree (Personne personne){
        super(personne.getIdSite(), personne.getNom(), personne.getPrenom(), personne.getGenre(), personne.getAge(), personne.getAgeMin(), personne.getAgeMax(), personne.getReleaseDate());
        listeConflits = new ArrayList<>();
        //retard = new Date();
    }


    /*public void setRetard(Date r) {this.retard = r;}

    public Date getRetard() {return this.retard;}*/

    public ArrayList<Affinite> calculerConflits(Iterable<Personne> listeP){
        //on boucle sur la liste des personnes
        for(Personne p : listeP){
            //on s'exclue soit même (cela n'a pas de sens de calculer un conflit entre this et this
            if(p.getIdSite() != this.getIdSite()){
                //si le critère d'age correspont, on ajoute la pair (p, 1)
                if(p.getAge() >= this.getAgeMin() && p.getAge() <= this.getAgeMax() && !p.getGenre().equalsIgnoreCase(this.getGenre())){
                    listeConflits.add(new Affinite(p, 1));
                }
                //sinon on ajoute la pair (p,0)
                else if(!p.getGenre().equalsIgnoreCase(this.getGenre())){
                    listeConflits.add(new Affinite(p, 0));
                }
            }
        }
        return listeConflits;
    }

    public ArrayList<Affinite> getConflits(){
        return listeConflits;
    }

}
