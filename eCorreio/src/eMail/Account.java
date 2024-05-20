package eMail;

import java.time.LocalDateTime;
import java.util.Iterator;

public interface Account {
    void send(String subject, String dest, String body, LocalDateTime date) throws DuplicaMessageException;
    void receive(String subject, String sender, String body, LocalDateTime date) throws DuplicaMessageException;
    void remove(String subject, String sender);
    Iterator<Email> sent();
    Iterator<Email> received();
    Iterator<Email> messagesBySubject(String subject) throws SubjectDoesNotExistException;
    Iterator<Email> messagesByAdress(String email) throws AdressDoesNotExistException;
    Iterator<String> allSsubjects();
}
