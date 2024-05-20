import enums.*;
import Calendar.*;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static final String EXIT = "exit";
    private static final String BYE = "Bye!";
    private static final int NULL = 0;
    private static final String UNKNOWN_COMMAND = "Unknown command %s. Type help to see available commands\n";
    private static final String ACCOUNT_CREATED = "%s was registered.\n";
    private static final String ACCOUNT_LISTING = "%s [%s].\n";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        startApp(in);
    }


    /**
     * DONE
     * @param in
     */
    private static void startApp(Scanner in) {
        String command;
        Calendar calendar = new CalendarClass();
        boolean isExit = false;
        do {
            StringTokenizer tokens = new StringTokenizer(in.nextLine());
            command = tokens.nextToken().toUpperCase();
            try {
                Commands c = Commands.valueOf(command);
                isExit = c.equals(EXIT);
                switch (c) {
                    case REGISTER -> processRegister(calendar, tokens);
                    case ACCOUNTS -> processAccounts(calendar);
                    case CREATE -> processCreate(calendar, tokens, in);
                    case EVENTS -> processListEvents(calendar, tokens);
                    case INVITE -> processInvite(calendar, tokens);
                    case RESPONSE -> processResponse(calendar, tokens);
                    case EVENT -> processSingleEvent(calendar, tokens);
                    case TOPICS -> processTopics(calendar, tokens);
                    case HELP -> processHelp();
                    case EXIT -> processExit();
                }
            } catch (IllegalArgumentException e) {
                processUnknownCommand(command);
            }
        } while (!isExit);
    }


    private static void processRegister(Calendar calendar, StringTokenizer tokens){
        String email = tokens.nextToken();
        String accountType = tokens.nextToken();

        try {
            calendar.addAccount(email, accountType);
            System.out.printf(ACCOUNT_CREATED, email);
        } catch (DuplicateAccountException e){
            System.out.printf(e.getMessage(), email);
        } catch (UnknownAccountTypeException e){
            System.out.printf(e.getMessage(), accountType);
        }
    }


    private static void processAccounts(Calendar calendar){
        Iterator<Account> ite = calendar.accountIterator();
        while (ite.hasNext()){
            Account acc = ite.next();
            System.out.printf(ACCOUNT_LISTING, acc.getEmail(), acc.getType());
        }
    }



    private static void processCreate(Calendar calendar, StringTokenizer tokens, Scanner in){
        String email = tokens.nextToken();
        String eventName = in.nextLine().trim();

        StringTokenizer t = new StringTokenizer(in.nextLine().trim());
        String eventPriority = t.nextToken();
        String year = t.nextToken();
        String month = t.nextToken();
        String day = t.nextToken();
        String hour = t.nextToken();
        LocalDateTime date = createDate(day, month, year, hour);

        String topics = in.nextLine().trim();

        try {
            calendar.addEvent(email, eventName, eventPriority, date, topics);
        } catch (AccountNotFoundException e){
            System.out.printf(e.getMessage(), email);
        } catch (UnknownEventPriorityException e){
            System.out.println(e.getMessage());
        } catch (GuestAccountEventCreationException e){
            System.out.printf(e.getMessage(), email);
        } catch (StaffAccountEventCreationException e){
            System.out.printf(e.getMessage(), email);
        } catch (EventAlreadyExitsException e){
            System.out.printf(e.getMessage(), eventName, email);
        } catch (AccountIsBusyException e){
            System.out.printf(e.getMessage(), email);
        }
    }

    private static LocalDateTime createDate(String day, String month, String year, String hour){
        int y = Integer.parseUnsignedInt(year);
        int d = Integer.parseUnsignedInt(day);
        int m = Integer.parseUnsignedInt(month);
        int h = Integer.parseUnsignedInt(hour);
        return LocalDateTime.of(y,m,d,h, NULL);
    }

    private static void processListEvents(Calendar calendar, StringTokenizer tokens){

    }



    private static void processInvite(Calendar calendar, StringTokenizer tokens){

    }



    private static void processResponse(Calendar calendar, StringTokenizer tokens){

    }



    private static void processSingleEvent(Calendar calendar, StringTokenizer tokens){

    }



    private static void processTopics(Calendar calendar, StringTokenizer tokens){

    }


    /**
     * DONE
     */
    private static void processHelp(){
        System.out.println("Available commands:");
        for (HelpMsg s: HelpMsg.values()){
            System.out.println(s.getString());
        }
    }


    /**
     * DONE
     */
    private static void processExit(){
        System.out.println(BYE);
    }

    private static void processUnknownCommand(String command){
        System.out.printf(UNKNOWN_COMMAND, command);
    }
}