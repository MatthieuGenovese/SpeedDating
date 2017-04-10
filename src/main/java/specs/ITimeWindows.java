package specs;


// TODO: Auto-generated Javadoc
/**
 * The Interface ITimeWindows.
 *
 * @param <E> the participant type
 * 
 * @author Arnaud Malapert
 */
public interface ITimeWindows<E extends IParticipant> {

    /**
     * Gets the arrival slot: the participant arrives at the event.
     *
     * @param part the participant
     * @return the arrival slot
     */
    int getArrivalSlot(E part);
    
    /**
     * Gets the departure slot: the participant leaves the event.
     *
     * @param part the participant
     * @return the departure slot
     */
    int getDepartureSlot(E part);
    
}
