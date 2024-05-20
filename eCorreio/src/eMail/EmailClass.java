package eMail;

import java.time.LocalDateTime;

public class EmailClass implements Email{
    private String subject;
    private String sender;
    private String body;
    LocalDateTime date;

    public EmailClass(String subject, String sender, String body, LocalDateTime date) {
        this.subject = subject;
        this.sender = sender;
        this.body = body;
        this.date = date;
    }
}
