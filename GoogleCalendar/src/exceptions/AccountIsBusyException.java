package exceptions;

public class AccountIsBusyException extends Exception{
    private static final String MESSAGE = "Account %s is busy.";

    public AccountIsBusyException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
