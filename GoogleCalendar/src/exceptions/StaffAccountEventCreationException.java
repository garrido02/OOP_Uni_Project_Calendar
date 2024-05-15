package exceptions;

public class StaffAccountEventCreationException extends Exception {
    private static final String MESSAGE = "Account %s cannot create high priority events.";

    public StaffAccountEventCreationException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
