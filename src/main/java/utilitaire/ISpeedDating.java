package utilitaire;

import personnes.IParticipants;
import recontres.IEventMeetings;
import recontres.IMeeting;

import java.util.ArrayList;
import java.util.List;


public interface ISpeedDating {
    //IEventSlots
    IEventMeetings getSlots();
    ArrayList<IParticipants> getParticipants();
    List<IMeeting> getMeetings();
}
