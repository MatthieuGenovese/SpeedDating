package specs;


/**
 * The Interface ICompatibility.
 *
 * @param <E> the participant type
 * 
 * @author Arnaud Malapert
 */
public interface ICompatibility<E extends IParticipant> {

    /**
     * Gets the compatibility score for two participants.
     *
     * @param part1 the participant 1
     * @param part2 the participant 2
     * @return the compatibility score
     */
    int getScore(E part1, E part2);
    
}
