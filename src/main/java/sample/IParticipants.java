package sample;

import conflits.Affinite;
import conflits.ICompatibility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Elia on 29/04/2017.
 */
public interface IParticipants {

    ArrayList<ICompatibility> calculerConflits(Iterable<IParticipants> listeP);

    ArrayList<ICompatibility> getConflits();

    void initPersonneSoiree(int id);

    void setNom(String nom);

    String getNom();

    void setPrenom(String prenom);

    String getPrenom();

    PersonneSoiree getPersonneSoiree();

    void setIdSite(int idSite);

    int getIdSite();

    void setGenre(String genre);

    String getGenre();

    void setAge(int age);

    int getAge();

    void setAgeMin(int ageMin);

    int getAgeMin();

    void setAgeMax(int ageMax);

    int getAgeMax();

    void setReleaseDate(Date releaseDate);

    Date getReleaseDate();

    void setRetard(long r);

    long getRetard();

    int getId();

    void setId(int id);

    String affichageNomPrenom();

}
