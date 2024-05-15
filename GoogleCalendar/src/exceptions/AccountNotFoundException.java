package exceptions;

public class AccountNotFoundException extends Exception {
    private static final String MESSAGE = "Account %s does not exist.";

    public AccountNotFoundException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
