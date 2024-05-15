package exceptions;

public class EventNotFoundInCreatorException extends Exception {
    private static final String MESSAGE = "%s does not exist in account %s.";

    public EventNotFoundInCreatorException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
