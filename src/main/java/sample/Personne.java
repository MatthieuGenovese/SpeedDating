package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matthieu on 21/03/2017.
 */
public class Personne {
    private String nom;
    private String prenom;
    private int id;
    private int age;
    private int ageMin;
    private int ageMax;
    private String genre;
    private Date releaseDate;
    private ArrayList<Pair<Personne, Integer>> listeConflits;

    public Personne(int id, String nom, String prenom, String genre, int age, int ageMin, int ageMax, Date releaseDate){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.releaseDate = releaseDate;
        listeConflits = new ArrayList<>();
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public String getPrenom(){
        return prenom;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return genre;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public void setAgeMin(int ageMin){
        this.ageMin = ageMin;
    }

    public int getAgeMin(){
        return ageMin;
    }

    public void setAgeMax(int ageMax){
        this.ageMax = ageMax;
    }

    public int getAgeMax(){
        return ageMax;
    }

    public void setReleaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate(){
        return releaseDate;
    }

    public ArrayList<Pair<Personne, Integer>> calculerConflits(ArrayList<Personne> listeP){
        //on boucle sur la liste des personnes
        for(Personne p : listeP){
            //on s'exclue soit même (cela n'a pas de sens de calculer un conflit entre this et this
            if(p.getId() != this.getId()){
                //si le critère d'age correspont, on ajoute la pair (p, 1)
                if(p.getAge() >= this.getAgeMin() && p.getAge() <= this.getAgeMax() && !p.getGenre().equalsIgnoreCase(this.getGenre())){
                    listeConflits.add(new Pair<Personne, Integer>(p, 1));
                }
                //sinon on ajoute la pair (p,0)
                else if(!p.getGenre().equalsIgnoreCase(this.getGenre())){
                    listeConflits.add(new Pair<Personne, Integer>(p, 0));
                }
            }
        }
        return listeConflits;
    }

    public ArrayList<Pair<Personne, Integer>> getConflits(){
        return listeConflits;
    }

    public String toString(){
        return "Nom : " + nom + " Prenom : " + prenom + " " + genre + " Age : " + Integer.toString(age) + " Age Min : " + Integer.toString(ageMin) + " Age Max : " + Integer.toString(ageMax) + " " + releaseDate.toString();
     }
}
