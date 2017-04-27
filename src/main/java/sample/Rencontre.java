package sample;

/**
 * Created by Matthieu on 26/04/2017.
 */
public class Rencontre implements IMeeting{
    private PersonneSoiree homme,femme;
    private int numeroCrenau;
    public PersonneSoiree getHomme() {
        return homme;
    }

    public void setHomme(PersonneSoiree homme) {
        this.homme = homme;
    }

    public PersonneSoiree getFemme() {
        return femme;
    }

    public void setFemme(PersonneSoiree femme) {
        this.femme = femme;
    }

    public int getCrenau(){
        return numeroCrenau;
    }

    public Rencontre(PersonneSoiree homme, PersonneSoiree femme, int numeroCrenau){
        this.homme = homme;
        this.femme = femme;
        this.numeroCrenau = numeroCrenau;

    }

    public String toString(){
        return "Rencontre : " + homme.getPrenom() + " " + femme.getPrenom();
    }
}
