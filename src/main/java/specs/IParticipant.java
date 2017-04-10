package specs;

/**
 * Information related to a participant of a speed dating.
 * 
 * @author Arnaud Malapert
 *
 */
public interface IParticipant {

    /**
     * Gets the id.
     *
     * @return the id
     */
    int getID();
    
    /**
     * Gets the login.
     *
     * @return the login
     */
    int getLogin();
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();
    
    /**
     * Gets the surname.
     *
     * @return the surname
     */
    String getSurname();
    
    /**
     * Gets the gender.
     *
     * @return the gender
     */
    boolean getGender();
    
    /**
     * Gets the age.
     *
     * @return the age
     */
    int getAge();
    
}
