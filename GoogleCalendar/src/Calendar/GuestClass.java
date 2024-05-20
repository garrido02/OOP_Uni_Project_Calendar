package Calendar;

import java.util.Objects;

public class GuestClass extends AbstractAccountClass{
    private static final String TYPE = "guest";

    public GuestClass(String email){
        super(email, TYPE);
    }
}
