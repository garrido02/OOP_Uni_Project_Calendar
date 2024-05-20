package Calendar;

import java.util.Objects;

public class StaffClass extends AbstractAccountClass{
    private static final String TYPE = "staff";

    public StaffClass(String email){
        super(email, TYPE);
    }

}
