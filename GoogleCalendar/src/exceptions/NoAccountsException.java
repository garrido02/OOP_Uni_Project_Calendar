package exceptions;

public class NoAccountsException extends Exception {
    private static final String MESSAGE = "No accounts registered";

    public NoAccountsException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
