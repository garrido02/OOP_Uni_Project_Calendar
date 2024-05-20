import eMail.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {
    private static final String SEND = "send";
    private static final String RECEIVE = "receive";
    private static final String SENT = "sent";
    private static final String RECEIVED = "received";
    private static final String SUBJECT = "subject";
    private static final String SUBJECTS = "subjects";
    private static final String EXIT = "exit";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        startApp(in);
    }

    private static void startApp(Scanner in){
        String command;
        Account acc = new AccountClass();
        do {
            command = in.nextLine();
            switch(command.toLowerCase()){
                case SEND -> processSend(in, acc);
                case RECEIVE -> processReceive(in, acc);
                case SENT -> processSent();
                case RECEIVED -> processReceived();
                case SUBJECT -> processSubject();
                case SUBJECTS -> processSubjects();
                case EXIT -> processExit();
            }
        } while (!command.equals(EXIT));
        in.close();
    }

    private static void processSend(Scanner in, Account acc){
        String topic = in.nextLine().trim();
        String email = in.nextLine().trim();
        String text = in.nextLine().trim();
        LocalDateTime date = createDate(in.nextLine());

        try{
            acc.send(topic, email, text, date);
            System.out.println("Message send with success.");
        } catch(DuplicaMessageException e){
            System.out.println(e.getMessage());
        }
    }

    private static void processReceive(Scanner in, Account acc){
        String topic = in.nextLine().trim();
        String email = in.nextLine().trim();
        String text = in.nextLine().trim();
        LocalDateTime date = createDate(in.nextLine());

        try{
            acc.receive(topic, email, text, date);
            System.out.println("Message send with success.");
        } catch(DuplicaMessageException e){
            System.out.println(e.getMessage());
        }
    }

    private static void processSent(){

    }

    private static void processReceived(){

    }

    private static void processSubject(){

    }

    private static void processSubjects(){

    }

    private static void processExit(){
        System.out.println("Bye!");
    }

    private static LocalDateTime createDate(String date){
        StringTokenizer it = new StringTokenizer(date);
        int year = Integer.parseUnsignedInt(it.nextToken().trim());
        int month = Integer.parseUnsignedInt(it.nextToken().trim());
        int day = Integer.parseUnsignedInt(it.nextToken().trim());
        return LocalDateTime.of(year, month, day, 0, 0);
    }
}