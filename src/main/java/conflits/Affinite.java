package conflits;

import sample.PersonneSoiree;

/**
 * Created by Matthieu on 25/04/2017.
 */
public class Affinite{
    private PersonneSoiree personne;
    private int affinite;

    public PersonneSoiree getPersonneSoiree() {
        return personne;
    }

    public void setPersonneSoiree(PersonneSoiree personne) {
        this.personne = personne;
    }

    public int getAffinite() {
        return affinite;
    }

    public void setAffinite(int affinite) {
        this.affinite = affinite;
    }

    public Affinite(PersonneSoiree personne, int affinite){
        this.affinite = affinite;
        this.personne = personne;

    }


    public String toString(){
        return personne.getPrenom() + personne.getId() + " ";
    }
}
