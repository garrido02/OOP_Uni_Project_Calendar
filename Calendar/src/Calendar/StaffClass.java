/**
 * StaffClass
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

/**
 * StaffClass that extends the AbstractAccountClass
 */
public class StaffClass extends AbstractAccountClass{
    private static final String TYPE = "staff";

    public StaffClass(String email){
        super(email, TYPE);
    }

}
