package exceptions;

public class GuestAccountEventCreationException extends Exception {
    private static final String MESSAGE = "Guest account %s cannot create new events.";

    public GuestAccountEventCreationException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
