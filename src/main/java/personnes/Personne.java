package personnes;

import recontres.ITimeWindows;

import java.util.Date;

/**
 * Created by Matthieu on 21/03/2017.
 */
public class Personne implements IParticipants{
    private String nom;
    private String prenom;
    private int idSite;
    private int age;
    private int ageMin;
    private int ageMax;
    private long retard;
    private String genre;
    private Date releaseDate;
    private PersonneSoiree pSoiree;

    public Personne(int idSite, String nom, String prenom, String genre, int age, int ageMin, int ageMax){
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.idSite = idSite;
        this.retard = 0;
    }

    public void initPersonneSoiree(int id, ITimeWindows time){
        this.pSoiree = new PersonneSoiree(this,id,time);
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

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }


    public String toString(){
        return "Nom : " + nom + " Prenom : " + prenom + " " + genre + " Age : " + Integer.toString(age) + " Age Min : " + Integer.toString(ageMin) + " Age Max : " + Integer.toString(ageMax);
     }

     public String affichageNomPrenom(){
         return nom + " " + prenom;
     }


}
