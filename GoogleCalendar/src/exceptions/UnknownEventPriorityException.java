package exceptions;

public class UnknownEventPriorityException extends Exception {
    private static final String MESSAGE = "Unknown priority type.";

    public UnknownEventPriorityException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
