package exceptions;

public class AccountAlreadyOnAnEventException extends Exception {
    private static final String MESSAGE = "%s is already attending another event.";

    public AccountAlreadyOnAnEventException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
