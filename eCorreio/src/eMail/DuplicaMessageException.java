package eMail;

public class DuplicaMessageException extends Exception {
    private static final String MESSAGE = "Duplicate message.";

    public DuplicaMessageException(){
        super();
    }

    public String getMessage(){
        return MESSAGE;
    }
}
