package sample;


import conflits.Affinite;
import conflits.ICompatibility;
import conflits.ICompatibility;

import java.util.ArrayList;

/**
 * Created by Matthieu on 08/04/2017.
 */
public class PersonneSoiree extends Personne{
    //private Date retard;
    ArrayList<ICompatibility> listeConflits;
    int id;


    public PersonneSoiree (IParticipants personne, int id){
        super(personne.getIdSite(), personne.getNom(), personne.getPrenom(), personne.getGenre(), personne.getAge(), personne.getAgeMin(), personne.getAgeMax(), personne.getReleaseDate());
        listeConflits = new ArrayList<ICompatibility>();

        this.id = id;
        //retard = new Date();
    }

    /*public void setRetard(Date r) {this.retard = r;}

    public Date getRetard() {return this.retard;}*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ICompatibility> calculerConflits(Iterable<IParticipants> listeP){
        //on boucle sur la liste des personnes
        for(IParticipants p : listeP){
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

    public ArrayList<ICompatibility> getConflits(){
        return listeConflits;
    }

}
