package exceptions;

public class UnknownAccountTypeException extends Exception{
    private static final String MESSAGE = "Unknown account type.";

    public UnknownAccountTypeException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }

}
