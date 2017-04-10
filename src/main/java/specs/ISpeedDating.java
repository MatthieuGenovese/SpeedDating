package specs;

import java.util.List;

/**
 * All information about a speed dating event.
 * 
 * @param <E> the participant type
 * 
 * @author Arnaud Malapert
 */
public interface ISpeedDating<E extends IParticipant> {
    
    IEventSlots getSlots();
    
    List<E> getParticipants();
    
    ITimeWindows<E> getTimeWindows();
    
    ICompatibility<E> getCompatibility();
    
    IEventMeetings<E> getMeetings();
            
}
