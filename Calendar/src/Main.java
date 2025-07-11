/**
 * Main
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

import Calendar.Calendar;
import enums.*;
import Calendar.*;
import exceptions.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class Main
 */
public class Main {
	
	//MAIN OUTPUT MSGS
    private static final String BYE = "Bye!";
    private static final String ALL = "ALL";
    private static final String ACCEPT = "ACCEPT";
    private static final String REJECT = "REJECT";
    private static final String UNANSWERED = "NO_ANSWER";
    private static final String GUEST_INVITED = "%s was invited.\n";
    private static final String STAFF_ACCEPTED = "%s accepted the invitation.\n";
    private static final String ALL_ACCOUNTS = "All accounts:";
    private static final String NO_EVENTS_ON_TOPICS = "No events on those topics.";
    private static final String EVENTS_ON_TOPICS = "Events on topics %s:\n";
    private static final String EVENT_LISTING_BY_TOPICS = "%s promoted by %s on %s\n";
    private static final int NULL = 0;
    private static final String ACCOUNT_EVENTS = "Account %s events:\n";
    private static final String EVENT_CANCEL_TIME_CONFLICT = "%s promoted by %s was rejected.\n";
    private static final String EVENT_STATUS = "%s status [invited %d] [accepted %d] [rejected %d] [unanswered %d]\n";
    private static final String UNKNOWN_COMMAND = "Unknown command %s. Type help to see available commands.\n";
    private static final String ACCOUNT_CREATED = "%s was registered.\n";
    private static final String ACCOUNT_OR_INVITE_LISTING = "%s [%s]\n";
    private static final String EVENT_CREATED = "%s is scheduled.\n";
    private static final String ACCOUNT_NO_EVENTS = "Account %s has no events.\n";
    private static final String EVENT_CREATOR_CANCEL_TIME_CONFLICT = "%s promoted by %s was removed.\n";
    private static final String ACCOUNT_ATTENDING_OTHER_EVENT = "Account %s already attending another event.\n";
    private static final String EVENT_OCCURS_AT = "%s occurs on %sh:\n";
    private static final String ACCOUNT_REPLIED_INVITATION = "Account %s has replied %s to the invitation.\n";
    private static final String ACCOUNT_ALREADY_REPLIED = "Account %s has already responded.\n";
    private static final String NO_ACCOUNT_REGISTERED = "No account registered.\n";
    private static final String AVAILABLE_COMMANDS = "Available commands:";
    private static final String ACCOUNT_ALREADY_EXISTS = "Account %s already exists.\n";
    private static final String UNKNOWN_ACCOUNT_TYPE = "Unknown account type.\n";
    private static final String ACCOUNT_NOT_FOUND = "Account %s does not exist.\n";
    private static final String UNKNOWN_PRIORITY = "Unknown priority type.\n";
    private static final String GUEST_ACCOUNT_CREATION = "Guest account %s cannot create events.\n";
    private static final String STAFF_ACCOUNT_CREATION = "Account %s cannot create high priority events.\n";
    private static final String EVENT_ALREADY_EXISTS = "%s already exists in account %s.\n";
    private static final String ACCOUNT_IS_BUSY = "Account %s is busy.\n";
    private static final String EVENT_NOT_FOUND_IN_CREATOR = "%s does not exist in account %s.\n";
    private static final String ACCOUNT_ALREADY_INVITED = "Account %s was already invited.\n";
    private static final String UNKNOWN_RESPONSE = "Unknown event response.\n";
    private static final String ACCOUNT_NOT_INVITED = "Account %s is not on the invitation list.\n";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        startApp(in);
        in.close();
    }

    /**
     * Starts the application.
     * @param in - The input scanner
     */
    private static void startApp(Scanner in) {
        String command;
        Calendar calendar = new CalendarClass();
        boolean isExit = false;
        do {
            StringTokenizer tokens = new StringTokenizer(in.nextLine());
            command = tokens.nextToken().toUpperCase();
            try {
                Commands c = Commands.valueOf(command.toUpperCase());
                switch (c) {
                    case REGISTER -> processRegister(calendar, tokens);
                    case ACCOUNTS -> processAccounts(calendar);
                    case CREATE -> processCreate(calendar, tokens, in);
                    case EVENTS -> processListEvents(calendar, tokens);
                    case INVITE -> processInvite(calendar, tokens, in);
                    case RESPONSE -> processResponse(calendar, tokens, in);
                    case EVENT -> processSingleEvent(calendar, tokens);
                    case TOPICS -> processTopics(calendar, tokens);
                    case HELP -> processHelp();
                    case EXIT -> {
                        isExit = true;
                        processExit();
                    }
                }
            } catch (IllegalArgumentException e) {
                processUnknownCommand(command, tokens);
            }
        } while (!isExit);
    }


    /**
     * Registers an account
     * @param calendar - The application
     * @param tokens - Multiple input commands
     */
    private static void processRegister(Calendar calendar, StringTokenizer tokens){
        String email = tokens.nextToken();
        String accountType = tokens.nextToken();

        try {
            calendar.addAccount(email, accountType);
            System.out.printf(ACCOUNT_CREATED, email);
        } catch (DuplicateAccountException e){
            System.out.printf(ACCOUNT_ALREADY_EXISTS, email);
        } catch (UnknownAccountTypeException e){
            System.out.printf(UNKNOWN_ACCOUNT_TYPE, accountType);
        }
    }

    /**
     * Lists all accounts registered on the application
     * @param calendar - The application
     */
    private static void processAccounts(Calendar calendar){
        Iterator<Account> ite = calendar.accountIterator();
        if(ite.hasNext())
        	System.out.println(ALL_ACCOUNTS);
        else
        	System.out.print(NO_ACCOUNT_REGISTERED);
        while (ite.hasNext()){
            Account acc = ite.next();
            System.out.printf(ACCOUNT_OR_INVITE_LISTING, acc.getEmail(), acc.getType().toString());
        }
    }


    /**
     * Creates an event on the application
     * @param calendar - The application
     * @param tokens - Multiple input commands
     * @param in - The input scanner
     */
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
            System.out.printf(EVENT_CREATED, eventName);
        } catch (AccountNotFoundException e){
            System.out.printf(ACCOUNT_NOT_FOUND, email);
        } catch (UnknownEventPriorityException e){
            System.out.print(UNKNOWN_PRIORITY);
        } catch (GuestAccountEventCreationException e){
            System.out.printf(GUEST_ACCOUNT_CREATION, email);
        } catch (StaffAccountEventCreationException e){
            System.out.printf(STAFF_ACCOUNT_CREATION, email);
        } catch (EventAlreadyExitsException e){
            System.out.printf(EVENT_ALREADY_EXISTS, eventName, email);
        } catch (AccountIsBusyException e){
            System.out.printf(ACCOUNT_IS_BUSY, email);
        }
    }

    /**
     * Converts the date given by the user to a specific format to be used by the application
     * @param day - The day of the event
     * @param month - The month of the event
     * @param year - The year of the event
     * @param hour - The hour of the event
     * @return the date of the event on a specified format.
     */
    private static LocalDateTime createDate(String day, String month, String year, String hour){
        int y = Integer.parseUnsignedInt(year);
        int d = Integer.parseUnsignedInt(day);
        int m = Integer.parseUnsignedInt(month);
        int h = Integer.parseUnsignedInt(hour);
        return LocalDateTime.of(y,m,d,h, NULL);
    }

    /**
     * Lists all the events of a given user
     * @param calendar - The application
     * @param tokens - Multiple command inputs
     */
    private static void processListEvents(Calendar calendar, StringTokenizer tokens){
        String email = tokens.nextToken();
        try {
            Iterator<Event> eventIterator = calendar.listEvents(email);
            if (!eventIterator.hasNext()){
                System.out.printf(ACCOUNT_NO_EVENTS,email);
            } else {
                System.out.printf(ACCOUNT_EVENTS, email);
                while (eventIterator.hasNext()){
                    Event e = eventIterator.next();
                    System.out.printf(EVENT_STATUS, e.getName(), e.getInvitationsNr(ALL), e.getInvitationsNr(ACCEPT), e.getInvitationsNr(REJECT), e.getInvitationsNr(UNANSWERED));
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.printf(ACCOUNT_NOT_FOUND, email);
        }
    }

    /**
     * Checks and writes the feedback for any time conflict
     * @param calendar - The application
     * @param invitee - The name of the invitee
     * @param eventCreator - The name of the event creator
     * @param eventName - The name of the event
     */
    private static void handleTimeConflictFeedback(Calendar calendar, String invitee, String eventCreator, String eventName) throws AccountNotFoundException {
        Iterator<Event> rejectedEventIte = calendar.getUserRejectedEvents(invitee, eventCreator, eventName);
        while(rejectedEventIte.hasNext()) {
            Event e = rejectedEventIte.next();
            switch(calendar.checkConflicts(invitee, e)) {
            case REMOVE_EVENT -> System.out.printf(EVENT_CREATOR_CANCEL_TIME_CONFLICT, e.getName(), e.getCreatorEmail());
            case CONFLICT_REJECT -> System.out.printf(EVENT_CANCEL_TIME_CONFLICT, e.getName(), e.getCreatorEmail());
            }
        }
    }
    
    /**
     * Invites a user to an event
     * @param calendar - The application
     * @param tokens - Multiple command inputs
     * @param in - The input scanner
     */
    private static void processInvite(Calendar calendar, StringTokenizer tokens, Scanner in){
        String invitee = tokens.nextToken();
        StringTokenizer string = new StringTokenizer(in.nextLine());
        String eventCreator = string.nextToken();
        String eventName = string.nextToken("\n").trim();

        try {
            if(calendar.invite(invitee, eventCreator, eventName)) {
            	System.out.printf(STAFF_ACCEPTED, invitee);
            	handleTimeConflictFeedback(calendar,invitee,eventCreator,eventName);
            }else {
                System.out.printf(GUEST_INVITED, invitee);
            }
   
        } catch (AccountNotFoundException e){
            System.out.printf(ACCOUNT_NOT_FOUND, e.getName());
        } catch (EventNotFoundInCreatorException e){
            System.out.printf(EVENT_NOT_FOUND_IN_CREATOR, eventName, eventCreator);
        } catch (AccountAlreadyInvitedException e){
            System.out.printf(ACCOUNT_ALREADY_INVITED, invitee, eventName);
        } catch (AccountAlreadyOnAnEventException e){
            System.out.printf(ACCOUNT_ATTENDING_OTHER_EVENT, invitee);
        }
    }


    /**
     * Allows a user to respond to a specified event invitation
     * @param calendar - The application
     * @param tokens - Multiple command inputs
     * @param in - The input scanner
     */
    private static void processResponse(Calendar calendar, StringTokenizer tokens, Scanner in){
        String invitee = tokens.nextToken();
        StringTokenizer stringToken = new StringTokenizer(in.nextLine().trim());
        String eventCreator = stringToken.nextToken();
        String eventName = stringToken.nextToken("\n").trim();
        String response = in.nextLine().trim().toUpperCase();
        try {
            if(calendar.respondInvitation(invitee, eventCreator, eventName, response)) {
            	System.out.printf(ACCOUNT_REPLIED_INVITATION, invitee, response.toLowerCase());
            	handleTimeConflictFeedback(calendar,invitee,eventCreator,eventName);
            }else
            	System.out.printf(ACCOUNT_REPLIED_INVITATION, invitee, response.toLowerCase());

        } catch (AccountNotFoundException e){
            System.out.printf(ACCOUNT_NOT_FOUND, e.getName());
        } catch (EventNotFoundInCreatorException e ){
            System.out.printf(EVENT_NOT_FOUND_IN_CREATOR, eventName, eventCreator);
        } catch (AccountAlreadyRepliedException e ){
            System.out.printf(ACCOUNT_ALREADY_REPLIED, invitee);
        } catch (AccountAlreadyOnAnEventException e){
            System.out.printf(ACCOUNT_ATTENDING_OTHER_EVENT, invitee);
        } catch (UnknownResponseException e ){
            System.out.printf(UNKNOWN_RESPONSE);
        } catch (AccountNotInInvitedListException e){
            System.out.printf(ACCOUNT_NOT_INVITED, invitee);
        }
    }


    /**
     * Describes the status of a specified event
     * @param calendar - The application
     * @param tokens - Multiple command inputs
     */
    private static void processSingleEvent(Calendar calendar, StringTokenizer tokens){
        String eventCreator = tokens.nextToken();
        String eventName = tokens.nextToken("\n").trim();

        try {
            System.out.printf(EVENT_OCCURS_AT, eventName, calendar.processSingleEvent(eventCreator, eventName));
            Iterator<Invite> inviteIterator = calendar.inviteIterator(eventName, eventCreator);
            while (inviteIterator.hasNext()){
                Invite i = inviteIterator.next();
                System.out.printf(ACCOUNT_OR_INVITE_LISTING, i.getInviteeEmail(), i.getResponse().toString());
            }
        } catch (AccountNotFoundException e){
            System.out.printf(ACCOUNT_NOT_FOUND, e.getName());
        } catch (EventNotFoundInCreatorException e){
            System.out.printf(EVENT_NOT_FOUND_IN_CREATOR, eventName, eventCreator);
        }
    }

    /**
     * Lists all events about specified topics. Ordering first by the number of common topics. If the number is the same
     * by event name and lastly creator name
     * @param calendar - The application
     * @param tokens - Multiple command inputs
     */
    private static void processTopics(Calendar calendar, StringTokenizer tokens){
        String[] topics = new String[tokens.countTokens()];
        String topicsList = "";
        int i = 0;
        while (tokens.hasMoreTokens()){
            topics[i] = tokens.nextToken();
            topicsList += topics[i++] + " ";
        }

        Iterator<Event> eventsIterator = calendar.getEventsByTopics(topics);
        if (!eventsIterator.hasNext()){
            System.out.println(NO_EVENTS_ON_TOPICS);
        } else {
        	System.out.printf(EVENTS_ON_TOPICS, topicsList.trim());
            while (eventsIterator.hasNext()){
                Event e = eventsIterator.next();
                System.out.printf(EVENT_LISTING_BY_TOPICS, e.getName(), e.getCreatorEmail(), e.getTopics());
            }
        }
    }

    /**
     * Lists all the application's available commands
     */
    private static void processHelp(){
        System.out.println(AVAILABLE_COMMANDS);
        for (HelpMsg s: HelpMsg.values()){
            System.out.println(s.getString());
        }
    }

    /**
     * Exists the application and therefore the program
     */
    private static void processExit(){
        System.out.println(BYE);
    }

    /**
     * Checks for possible multiple unknown commands.
     * @param command - The command given by the user
     * @param tokens - Multiple command inputs
     */
    private static void processUnknownCommand(String command,StringTokenizer tokens){
    	System.out.printf(UNKNOWN_COMMAND, command);
    	while(tokens.hasMoreTokens()) {
    		System.out.printf(UNKNOWN_COMMAND, tokens.nextToken().toUpperCase());
    	}
    }
}