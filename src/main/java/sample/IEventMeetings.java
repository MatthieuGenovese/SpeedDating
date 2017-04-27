package sample;

import java.util.List;

/**
 * Created by Matthieu on 27/04/2017.
 */
public interface IEventMeetings {
    boolean hasMet(PersonneSoiree part1, PersonneSoiree part2);

    boolean metAt(PersonneSoiree part1, PersonneSoiree part2, int slot);

    boolean isPresent(IMeeting meeting);

    List<IMeeting> getPastMeetings();

    List<IMeeting> getCurrentMeetings();

    List<IMeeting> getNextMeetings();

    //List<IMeeting> getScheduledMeetings();

    List<IMeeting> getAllMeetings();
}
