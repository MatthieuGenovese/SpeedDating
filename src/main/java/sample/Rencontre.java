package sample;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Rencontre {
    private PersonneSoiree homme,femme;

    public PersonneSoiree getHomme() {
        return homme;
    }

    public void setHomme(PersonneSoiree homme) {
        this.homme = homme;
    }

    public Personne getFemme() {
        return femme;
    }

    public void setFemme(PersonneSoiree femme) {
        this.femme = femme;
    }

    public Rencontre(PersonneSoiree homme, PersonneSoiree femme){
        this.homme = homme;
        this.femme = femme;

    }

    public String toString(){
        return "Rencontre : " + homme.getPrenom() + " " + femme.getPrenom();
    }
}
