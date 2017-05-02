package recontres;

import personnes.IParticipants;

/**
 * Created by Matthieu on 27/04/2017.
 */
public interface IMeeting {

    public IParticipants getHomme();

    public IParticipants getFemme();

    public int getCrenau();
}
