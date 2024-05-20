package eMail;

public class AdressDoesNotExistException extends Exception {
    private static final String MESSAGE = "Adress does not exist.";

    public AdressDoesNotExistException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
