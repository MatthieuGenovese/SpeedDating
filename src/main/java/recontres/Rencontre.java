package recontres;

import personnes.IParticipants;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Rencontre implements IMeeting{
    private IParticipants homme,femme;
    private int numeroCrenau;
    public IParticipants getHomme() {
        return homme;
    }

    public void setHomme(IParticipants homme) {
        this.homme = homme;
    }

    public IParticipants getFemme() {
        return femme;
    }

    public void setFemme(IParticipants femme) {
        this.femme = femme;
    }

    public int getCrenau(){
        return numeroCrenau;
    }

    public Rencontre(IParticipants homme, IParticipants femme, int numeroCrenau){
        this.homme = homme;
        this.femme = femme;
        this.numeroCrenau = numeroCrenau;

    }

    public String toString(){
        return homme.getPrenom() + " " + homme.getNom() + " // " + femme.getPrenom() + " " + femme.getNom() + "\n";
    }
}
