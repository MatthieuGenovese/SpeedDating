package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by Matthieu on 21/03/2017.
 */
public class Personne {
    private String nom;
    private String prenom;
    private int idSite;
    private int id = 0;
    private int age;
    private int ageMin;
    private int ageMax;
    private long retard;
    private String genre;
    private Date releaseDate;
    private PersonneSoiree pSoiree;

    public Personne(int idSite, String nom, String prenom, String genre, int age, int ageMin, int ageMax, Date releaseDate){
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.releaseDate = releaseDate;
        this.idSite = idSite;
        this.pSoiree = new PersonneSoiree(this);
        this.retard = 0;
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

    public PersonneSoiree getPersonneSoiree(){
        return pSoiree;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setIdSite(int idSite){
        this.idSite = idSite;
    }

    public int getIdSite(){
        return idSite;
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

    public void setRetard(long r) {this.retard = r;}

    public long getRetard() {return this.retard;}



    public String toString(){
        return "Nom : " + nom + " Prenom : " + prenom + " " + genre + " Age : " + Integer.toString(age) + " Age Min : " + Integer.toString(ageMin) + " Age Max : " + Integer.toString(ageMax) + " " + releaseDate.toString();
     }


}
