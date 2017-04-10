package specs;

/**
 * information about meetings of the event.
 * 
 * @param <E> the participant type
 *
 * @author Arnaud Malapert
 */
public interface IEventSlots {
       
    /**
     * Gets the slot count.
     *
     * @return the slot count
     */
    int getSlotCount();
    
    /**
     * Gets the current slot.
     *
     * @return the current slot
     */
    int getCurrentSlot();
    
    /**
     * Gets the next slot.
     *
     * @return the next slot
     */
    int getNextSlot();
    
    /**
     * Checks if the event is not terminated.
     *
     * @return true, if the event is not terminated
     */
    boolean hasNextSlot();
    
    /**
     * the current slot is over, pass to the next slot.
     */    
    void nextSlot();

}
