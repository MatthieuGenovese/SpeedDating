package recontres;

import personnes.IParticipants;

import java.util.List;

/**
 * Created by Matthieu on 27/04/2017.
 */
public interface IEventMeetings {
    boolean hasMet(IParticipants part1, IParticipants part2);

    boolean metAt(IParticipants part1, IParticipants part2, int slot);

    boolean isPresent(IMeeting meeting);

    List<IMeeting> getPastMeetings();

    List<IMeeting> getCurrentMeetings();

    List<IMeeting> getNextMeetings();

    //List<IMeeting> getScheduledMeetings();

    List<IMeeting> getAllMeetings();
}
