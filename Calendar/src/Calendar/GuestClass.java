/**
 * GuestClass
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

/**
 * GuestClass that extends the AbstractAccountClass
 */
public class GuestClass extends AbstractAccountClass{
    private static final String TYPE = "guest";

    public GuestClass(String email){
        super(email, TYPE);
    }
}
