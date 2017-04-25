package sample;

/**
 * Created by Matthieu on 25/04/2017.
 */
public class Affinite {
    private Personne personne;
    private int affinite;

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public int getAffinite() {
        return affinite;
    }

    public void setAffinite(int affinite) {
        this.affinite = affinite;
    }

    public Affinite(Personne personne, int affinite){
        this.affinite = affinite;
        this.personne = personne;

    }
}
