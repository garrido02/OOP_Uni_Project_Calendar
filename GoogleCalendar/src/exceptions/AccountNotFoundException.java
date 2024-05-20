package exceptions;

public class AccountNotFoundException extends Exception {
    private static final String MESSAGE = "Account %s does not exist.\n";

    public AccountNotFoundException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
