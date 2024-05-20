package exceptions;

public class AccountAlreadyInvitedException extends Exception {
    private static final String MESSAGE = "%s was already invited.";

    public AccountAlreadyInvitedException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
