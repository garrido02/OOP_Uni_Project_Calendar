package exceptions;

public class DuplicateAccountException extends Exception {
    private static final String MESSAGE = "%s already exists.\n";

    public DuplicateAccountException (){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
