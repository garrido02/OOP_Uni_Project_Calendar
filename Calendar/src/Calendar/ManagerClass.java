/**
 * ManagerClass
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

/**
 * ManagerClass that extends the AbstractAccountClass
 */
public class ManagerClass extends AbstractAccountClass{
    /**
     * Instance variables
     */
    private static final String TYPE = "manager";

    /**
     * Constructor
     * @param email - The email of the user
     */
    public ManagerClass(String email){ super(email, TYPE); }

}
