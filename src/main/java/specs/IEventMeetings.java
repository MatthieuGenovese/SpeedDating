package specs;

import java.util.List;

/**
 * information about meetings of the event.
 * 
 * @param <E> the participant type
 * 
 * @author Arnaud Malapert
 */
public interface IEventMeetings<E extends IParticipant> {
        
    /**
     * Checks if the participants have met.
     *
     * @param part1 the first participant
     * @param part2 the second participant
     * @return true, if they have already met
     */
    boolean hasMet(E part1, E part2);
 
    /**
     * Checks if the participants have met or will meet at a given slot.
     *
     * @param part1 the first participant
     * @param part2 the second participant
     * @return true, if they have met at this slot
     */
    boolean metAt(E part1, E part2, int slot);
    
    /**
     * Checks if the meeting is scheduled.
     * 
     * @return true, if the meeting is scheduled
     */
    boolean isPresent(IMeeting<E> meeting);
    
    /**
     * Gets the past meetings.
     *
     * @return the past meetings
     */
    List<IMeeting<E>> getPastMeetings();
    
    /**
     * Gets the current meetings.
     *
     * @return the current meetings
     */
    List<IMeeting<E>> getCurrentMeetings();
    
    /**
     * Gets the next meetings.
     *
     * @return the next meetings
     */
    List<IMeeting<E>> getNextMeetings();
    
    /**
     * Gets the scheduled meetings.
     *
     * @return the scheduled meetings
     */
    List<IMeeting<E>> getScheduledMeetings();
    
    /**
     * Gets the all meetings.
     *
     * @return the all meetings
     */
    List<IMeeting<E>> getAllMeetings();

}
