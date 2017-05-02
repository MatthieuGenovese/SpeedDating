package conflits;

import personnes.IParticipants;

/**
 * Created by Elia on 29/04/2017.
 */
public interface ICompatibility {

    IParticipants getPersonneSoiree();

    void setPersonneSoiree(IParticipants personne);

    int getAffinite();

    void setAffinite(int affinite);
}
