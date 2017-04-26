package sample;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Rencontre {
    private Personne homme,femme;

    public Personne getHomme() {
        return homme;
    }

    public void setHomme(Personne homme) {
        this.homme = homme;
    }

    public Personne getFemme() {
        return femme;
    }

    public void setFemme(Personne femme) {
        this.femme = femme;
    }

    public Rencontre(Personne homme, Personne femme){
        this.homme = homme;
        this.femme = femme;

    }

    public String toString(){
        return "Rencontre : " + homme.getPrenom() + " " + femme.getPrenom();
    }
}
