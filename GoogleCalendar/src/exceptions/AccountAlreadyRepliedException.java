package exceptions;

public class AccountAlreadyRepliedException extends Exception {
    private static final String MESSAGE = "Account %s has already responded.";

    public AccountAlreadyRepliedException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
