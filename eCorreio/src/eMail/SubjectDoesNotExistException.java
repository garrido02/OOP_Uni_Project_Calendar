package eMail;

public class SubjectDoesNotExistException extends Exception {
    private static final String MESSAGE = "Subject does not exist.";

    public SubjectDoesNotExistException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
