package exceptions;

public class UnknownResponseException extends Exception {
    private static final String MESSAGE = "Unknown event response.";

    public UnknownResponseException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
