package specs;

/**
 * The Interface IMeeting.
 *
 * @param <E> the participant type
 * 
 * @author Arnaud Malapert
 */
public interface IMeeting<E extends IParticipant> {
    
    /**
     * Gets the first participant.
     *
     * @return the first participant
     */
    E getFirst();
    
    /**
     * Gets the second participant.
     *
     * @return the second participant
     */
    E getSecond();
    
    /**
     * Gets the meeting slot.
     *
     * @return the slot
     */
    int getSlot();

}
