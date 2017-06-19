package personnes;

import recontres.ITimeWindows;

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

    void setRetard(int r);

    ITimeWindows getTimeWindow();

    void setTimeWindow(ITimeWindows timeWindow);

    int getRetard();

    int getId();

    void setId(int id);

    String affichageNomPrenom();
}
