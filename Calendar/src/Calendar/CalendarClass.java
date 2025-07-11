/**
 * CalendarClass
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Accounts;
import enums.InviteFeedback;
import enums.Priorities;
import enums.Responses;
import exceptions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * CalendarClass that implements the methods of the Calendar interface
 */
public class CalendarClass implements Calendar {
    /**
     * Instance variables
     */
    private static final String DATE_FORMATTER = "dd-MM-yyyy HH";
    private List<Account> users;

    /**
     * Constructor
     */
    public CalendarClass(){
        users = new ArrayList<>();
    }

    @Override
    public String processSingleEvent(String eventCreator, String eventName) throws AccountNotFoundException, EventNotFoundInCreatorException{
        Account creator = findUser(eventCreator);       
        Event e = findEvent(creator, eventName);
        if (e == null){
            throw new EventNotFoundInCreatorException();
        }
        return e.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
    }

    @Override
    public Iterator<Event> getEventsByTopics(String[] topics) {
        List<Event> eventsByTopics = new ArrayList<>();
        Iterator<Account> accountIterator = users.iterator();
        while (accountIterator.hasNext()){
            Account acc = accountIterator.next();
            Iterator<Event> eventIterator = acc.eventIterator();
            while (eventIterator.hasNext()){
                Event event = eventIterator.next();
                event.commonTopics(topics);
                int commonTopics = event.getCommonTopics();
                if (commonTopics > 0 && !eventsByTopics.contains(event)){
                    eventsByTopics.add(event);
                }
            }
        }
        eventsByTopics.sort((e1, e2) -> e1.compareByTopics(e2));
        return eventsByTopics.iterator();
    }

    @Override
    public Iterator<Invite> inviteIterator(String eventName, String eventCreator) throws AccountNotFoundException {
        Account creator = findUser(eventCreator);
        Event event = findEvent(creator, eventName);
        Iterator<Invite> iterator = event.inviteIterator();
        return iterator;
    }

    @Override
    public void addEvent(String email, String eventName, String eventPriority, LocalDateTime date, String topics) throws AccountNotFoundException,
            UnknownEventPriorityException, GuestAccountEventCreationException, StaffAccountEventCreationException,
            EventAlreadyExitsException, AccountIsBusyException {



        Account user = findUser(email);
        Priorities prio;
        try {
             prio = Priorities.valueOf(eventPriority.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new UnknownEventPriorityException();
        }

        if (user instanceof GuestClass){
            throw new GuestAccountEventCreationException();
        }

        if (user instanceof StaffClass && prio.equals(Priorities.HIGH)){
            throw new StaffAccountEventCreationException();
        }

        Event e = new EventClass(eventName, user, topics, prio, date);
        if (user.contains(e)){
            throw new EventAlreadyExitsException();
        }

        if (user.isBusy(e)){
            throw new AccountIsBusyException();
        }

        if (hasTimeConflict(user, e)) {
            Iterator<Event> rejectedEventsIterator = user.rejectEventsIterator();
            while (rejectedEventsIterator.hasNext()) {
                Event rejectedEvent = rejectedEventsIterator.next();
                rejectedEvent.setResponse(user, Responses.REJECT);
            }
        }

        e.invite(user);
        e.setResponse(user, Responses.ACCEPT);
    }

    /**
     * Removes an event from the application
     * @param e - The event to be removed
     */
    private void removeEvent(Event e) {
    	Iterator<Invite> invitees = e.inviteIterator();
    	while(invitees.hasNext())
    		invitees.next().getInvitee().removeEvent(e);
    }


    @Override
    public void addAccount(String email, String accountType) throws DuplicateAccountException, UnknownAccountTypeException {
        try {
            if (users.contains(createTempUser(email))){
                throw new DuplicateAccountException();
            } 
            Accounts type = Accounts.valueOf(accountType.toUpperCase());
            users.add(createAccountType(email, type));
            
        } catch (IllegalArgumentException e){
            throw new UnknownAccountTypeException();
        }
    }

    /**
     * Created an Account object of a given type
     * @param email - The email of the user
     * @param type - The type of the account
     * @return an Account object of a given account type
     */
    private Account createAccountType(String email, Accounts type){
        Account acc = switch(type) {
            case GUEST -> new GuestClass(email);
            case STAFF -> new StaffClass(email);
            case MANAGER -> new ManagerClass(email);
        };
        return acc;
    }


    @Override
    public boolean respondInvitation(String invitee, String eventCreator, String eventName, String response) throws AccountNotFoundException, AccountNotInInvitedListException,
            AccountAlreadyRepliedException, AccountAlreadyOnAnEventException, EventNotFoundInCreatorException, UnknownResponseException{
   	
    	Account inviteeAcc = findUser(invitee);
    	Account creatorAcc = findUser(eventCreator);

       
        Responses r;
        try {
            r = Responses.valueOf(response);
        } catch (IllegalArgumentException e){
            throw new UnknownResponseException();
        }
        
        Event event = findEvent(creatorAcc, eventName);
        if (event == null) {
            throw new EventNotFoundInCreatorException();
        }

        if (!event.isUserInvited(inviteeAcc)){
            throw new AccountNotInInvitedListException();
        }

        if (event.userAlreadyReplied(inviteeAcc)){
            throw new AccountAlreadyRepliedException();
        }

        if (hasTimeConflict(inviteeAcc, event)){
            Iterator<Event> ite = inviteeAcc.rejectEventsIterator();
            while (ite.hasNext()) {
                Event e = ite.next();
                if (checkDatesCollision(e, event)) {
                    Iterator<Invite> inviteIterator = e.inviteIterator();
                    while (inviteIterator.hasNext()) {
                        Invite i = inviteIterator.next();
                        if (i.getInvitee().equals(inviteeAcc) && i.getResponse().equals(Responses.ACCEPT)) {
                            throw new AccountAlreadyOnAnEventException();
                        }
                    }
                }
            }
        }

        inviteeAcc.respond(event, r);
        return r.equals(Responses.ACCEPT);
    }

    @Override
    public boolean checkConflict(String invitee, String eventCreator, String eventName) throws AccountNotFoundException {
        Account inviteeAcc = findUser(invitee);
        Account creatorAcc = findUser(eventCreator);
        Event event = findEvent(creatorAcc, eventName);
        return hasTimeConflict(inviteeAcc, event);

    }

    /**
     * Checks if the user has accepted the invitation to a given event
     * @param invitee - The user
     * @param eventCreator - The creator of the event
     * @param eventName - The name of the event
     * @return true if the user has accepted the invitation. Otherwise, false
     * @throws AccountNotFoundException 
     */
    private boolean checkAcceptedEvent(Account invitee, Account eventCreator, String eventName){
        Event event = eventCreator.getEvent(eventName);
        if (invitee.isBusy(event)){
            return true;
        }
        return false;
    }

    /**
     * Checks if there is time conflict between a given event and a user's pending or accepted events
     * @param inviteeAcc - The user
     * @param event - The event to check for conflicts
     * @return true if there is time conflict. Otherwise, false
     */
    private boolean hasTimeConflict(Account inviteeAcc, Event event) {
        boolean result = false;
        Iterator<Event> eventsIterator = inviteeAcc.eventIterator();
        inviteeAcc.clearRejectedEvents();
        while (eventsIterator.hasNext()){
            Event e = eventsIterator.next();
            if (checkDatesCollision(e, event) && !e.equals(event) && !e.hasUserRejected(inviteeAcc)){
                inviteeAcc.addRejectedEvent(e);
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks if two event's dates collide
     * @param e1 - Event 1
     * @param e2 - Event 2
     * @return true if the dates collide. Otherwise, false
     */
    private boolean checkDatesCollision(Event e1, Event e2){
        return e1.getDate().equals(e2.getDate());
    }

    @Override
    public boolean isConflictedEventCreator(String invitee, Event e) {
        return e.getCreator().getEmail().equals(invitee);
    }
    
    /**
     * Cancels a user participation on a given event
     * @param invitee - The user whose participation is about to the canceled
     * @param e - The event
     */
    private void cancelEventParticipation(String invitee, Event e) throws AccountNotFoundException {
        Account inviteeAcc = findUser(invitee);
        inviteeAcc.respond(e, Responses.REJECT);
    }

    @Override
    public Iterator<Event> getUserRejectedEvents(String invitee, String eventCreator, String eventName) throws AccountNotFoundException {
        this.checkConflict(invitee, eventCreator, eventName);
        return findUser(invitee).rejectEventsIterator();
    }

    /**
     * Returns an event created by a given user and with a given name
     * @param eventCreator - The event creator
     * @param eventName - The name of the event
     * @return an Event object
     */
    private Event findEvent(Account eventCreator, String eventName){
        return eventCreator.getEvent(eventName);
    }

    /**
     * Returns the user corresponding to the information on a dummy account
     * @param tmpUser - The dummy user accout
     * @return an Account object
     */
    private Account findUser(String user) throws AccountNotFoundException{
        try {
        	return users.get(users.indexOf(createTempUser(user)));
        }catch(IndexOutOfBoundsException e) {
        	throw new AccountNotFoundException(user);
        }
    }

    /**
     * Creates a dummy user account
     * @param email - The name of the user
     * @return an Account object
     */
    private Account createTempUser(String email){
        return new GuestClass(email);
    }

    
    
    @Override
    public boolean invite(String invitee, String eventCreator, String eventName) throws AccountNotFoundException, EventNotFoundInCreatorException, AccountAlreadyInvitedException, AccountAlreadyOnAnEventException {
        boolean forcedStaff = false;
    	
        Account inviteeAcc = findUser(invitee);
        Account creatorAcc = findUser(eventCreator);
        
        
        Event event = findEvent(creatorAcc, eventName);
        if (event == null) {
            throw new EventNotFoundInCreatorException();
        }

        if (event.isUserInvited(inviteeAcc)){
            throw new AccountAlreadyInvitedException();
        }

        if (inviteeAcc.isAttendingHighPrioEvent() && event.getPriorityLevel().equals(Priorities.HIGH)){
            throw new AccountAlreadyOnAnEventException();
        }

        if (!Accounts.STAFF.equals(inviteeAcc.getType()) && checkAcceptedEvent(inviteeAcc,creatorAcc, eventName))
            throw new AccountAlreadyOnAnEventException();

        creatorAcc.invite(inviteeAcc, event);
        if (inviteeAcc.getType().equals(Accounts.STAFF) && event.getPriorityLevel().equals(Priorities.HIGH)){
            event.setResponse(inviteeAcc, Responses.ACCEPT);
            forcedStaff = true;
        }
        return forcedStaff;
    }
    
    @Override
    public InviteFeedback checkConflicts(String invitee, Event e) throws AccountNotFoundException {
    		if (!isConflictedEventCreator(invitee, e)) {
             cancelEventParticipation(invitee, e);
             return InviteFeedback.CONFLICT_REJECT;
         } else {
             removeEvent(e);
             return InviteFeedback.REMOVE_EVENT;
         }
    	}

    @Override
    public Iterator<Event> listEvents(String email) throws AccountNotFoundException {
        Account user = findUser(email);
        Iterator<Event> ite = user.eventIterator();
        return ite;
    }

    @Override
    public Iterator<Account> accountIterator() {
        users.sort((user1, user2) -> user1.compareTo(user2));
        return users.iterator();
    }

}
