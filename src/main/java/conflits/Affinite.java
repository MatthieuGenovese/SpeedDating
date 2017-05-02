package conflits;

import personnes.IParticipants;

/**
 * Created by Matthieu on 25/04/2017.
 */
public class Affinite implements ICompatibility{
    IParticipants personne;
    private int affinite;

    public IParticipants getPersonneSoiree() {
        return personne;
    }

    public void setPersonneSoiree(IParticipants personne) {
        this.personne = personne;
    }

    public int getAffinite() {
        return affinite;
    }

    public void setAffinite(int affinite) {
        this.affinite = affinite;
    }

    public Affinite(IParticipants personne, int affinite){
        this.affinite = affinite;
        this.personne = personne;

    }


    public String toString(){
        return personne.getPrenom() + personne.getId() + " ";
    }
}
