package exceptions;

public class EventAlreadyExitsException extends Exception {
    private static final String MESSAGE = "%s already exists in account %s.";

    public EventAlreadyExitsException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
