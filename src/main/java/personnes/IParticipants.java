package personnes;

import java.util.Date;

/**
 * Created by Elia on 29/04/2017.
 */
public interface IParticipants {

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
