package eMail;

import dataStructures.MapList;
import dataStructures.MapListClass;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AccountClass implements Account{
    private SortedSet<Email> sentEmails;
    private SortedSet<Email> receivedEmails;
    private MapList<String, Email> emailsBySubject;
    private MapList<String, Email> emailsByAdress;
    private SortedSet<String> subjects;

    public AccountClass(){
        sentEmails = new TreeSet<>();
        receivedEmails = new TreeSet<>();
        emailsBySubject = new MapListClass<>();
        emailsByAdress = new MapListClass<>();
        subjects = new TreeSet<>();
    }


    @Override
    public void send(String subject, String dest, String body, LocalDateTime date) throws DuplicaMessageException {
        Email email = new EmailClass(subject, dest, body, date);
        if (sentEmails.contains(email)){
            throw new DuplicaMessageException();
        }
        receivedEmails.add(email);
        emailsByAdress.addElem(dest, email);
        emailsBySubject.addElem(subject, email);
        subjects.add(subject);
    }

    @Override
    public void receive(String subject, String sender, String body, LocalDateTime date) throws DuplicaMessageException {
        Email email = new EmailClass(subject, sender, body, date);
        if (receivedEmails.contains(email)){
            throw new DuplicaMessageException();
        }
        receivedEmails.add(email);
        emailsByAdress.addElem(sender, email);
        emailsBySubject.addElem(subject, email);
        subjects.add(subject);
    }

    @Override
    public void remove(String subject, String sender) {
        if (emailsBySubject.containsKey(subject)){
            emailsBySubject.remove(subject, emailsBySubject.getElem(subject));
        }
        if (emailsByAdress.containsKey(sender)){
            emailsByAdress.remove(sender, emailsByAdress.getElem(sender));
        }
        List mail = emailsBySubject.getElem(subject);
        receivedEmails.remove(mail);
        sentEmails.remove(mail);
    }

    @Override
    public Iterator<Email> sent() {
        return sentEmails.iterator();
    }

    @Override
    public Iterator<Email> received() {
        return receivedEmails.iterator();
    }

    @Override
    public Iterator<Email> messagesBySubject(String subject) throws SubjectDoesNotExistException {
        if (!emailsBySubject.containsKey(subject)){
            throw new SubjectDoesNotExistException();
        }
        return emailsBySubject.getElem(subject).iterator();
    }

    @Override
    public Iterator<Email> messagesByAdress(String email) throws AdressDoesNotExistException {
        if (!emailsByAdress.containsKey(email)){
            throw new AdressDoesNotExistException();
        }
        return emailsByAdress.getElem(email).iterator();
    }

    @Override
    public Iterator<String> allSsubjects() {
        return subjects.iterator();
    }
}
