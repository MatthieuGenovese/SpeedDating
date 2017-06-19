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
    private int retard;
    private String genre;
    private Date releaseDate;
    private ITimeWindows timeWindow;
    private PersonneSoiree pSoiree;

    public Personne(int idSite, String nom, String prenom, String genre, int age, int ageMin, int ageMax, ITimeWindows timeWindow){
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.idSite = idSite;
        this.retard = 0;
        this.timeWindow = timeWindow;
    }

    public void initPersonneSoiree(int id){
        this.pSoiree = new PersonneSoiree(this,id);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }

    public ITimeWindows getTimeWindow(){
        return timeWindow;
    }

    public void setTimeWindow(ITimeWindows time){
        this.timeWindow = time;
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

    public void setRetard(int r) {this.retard = r;}

    public int getRetard() {return this.retard;}

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }


    public String toString(){
        return "Nom : " + nom + " Prenom : " + prenom + " " + genre + " Age : " + Integer.toString(age) + " Age Min : " + Integer.toString(ageMin) + " Age Max : " + Integer.toString(ageMax) + " Arrivée : " + getTimeWindow().getArrivalSlot() + " Départ :" + getTimeWindow().getDepartureSlot();
     }

     public String affichageNomPrenom(){
         return nom + " " + prenom;
     }


}
