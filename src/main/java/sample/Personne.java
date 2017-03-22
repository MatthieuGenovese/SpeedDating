package sample;

import java.util.Date;

/**
 * Created by Matthieu on 21/03/2017.
 */
public class Personne {
    private String nom;
    private int age;
    private int ageMin;
    private int ageMax;
    private String genre;
    private Date releaseDate;

    public Personne(String nom, String genre, int age, int ageMin, int ageMax, Date releaseDate){
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.releaseDate = releaseDate;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
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

    public String toString(){
        return nom + " " + genre + " " + Integer.toString(age) + " " + Integer.toString(ageMin) + " " + Integer.toString(ageMax) + " " + releaseDate.toString();
     }
}
