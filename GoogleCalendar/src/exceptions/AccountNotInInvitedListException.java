package exceptions;

public class AccountNotInInvitedListException extends Exception {
    private static final String MESSAGE = "Account %s is not on the invitation list.";

    public AccountNotInInvitedListException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
