/**
 * Calendar Interface
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Accounts;
import enums.InviteFeedback;
import enums.Priorities;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * Calendar interface responsible to create the all CalendarClass method prototype
 */
public interface Calendar {
    /**
     * Adds an event to the application
     * @param email - The email of the event creator
     * @param eventName - The name of the event
     * @param eventPriority - The priority of the event
     * @param date - The date of the event
     * @param topics - The topics of the event
     * @throws AccountNotFoundException - Exception if the creator is not registered on the application
     * @throws UnknownEventPriorityException - Exception if the priority of the event does not exist
     * @throws GuestAccountEventCreationException - Exception if a guest user tries to create an event
     * @throws StaffAccountEventCreationException - Exception if a staff user tries to create an event of High priority
     * @throws EventAlreadyExitsException - Exception if an event already exist
     * @throws AccountIsBusyException - Exception if the creator is already attending another event at that time.
     */
    void addEvent(String email, String eventName, String eventPriority, LocalDateTime date, String topics) throws AccountNotFoundException, UnknownEventPriorityException,
            GuestAccountEventCreationException, StaffAccountEventCreationException,
            EventAlreadyExitsException, AccountIsBusyException;


    /**
     * Checks if there is time conflicts
     * @param invitee - The email of the invitee
     * @param e - The object event
     * @throws AccountNotFoundException - Exception if the creator is not registered on the application
     */
    public InviteFeedback checkConflicts(String invitee, Event e) throws AccountNotFoundException;
    


    /**
     * Registers an account in the application
     * @param email - The email of the user
     * @param accountType - The account type of the user
     * @throws DuplicateAccountException - Exception if the user already exists
     * @throws UnknownAccountTypeException - Exception if the account type does not exist
     */
    void addAccount(String email, String accountType) throws DuplicateAccountException,
            UnknownAccountTypeException;

    /**
     * Responds to an event invitation
     * @param invitee - The user who was invited
     * @param eventCreator - The creator of the event
     * @param eventName - The name of the event
     * @param response - The response to the invitation
     * @throws AccountNotFoundException - Exception if either the invitee or event creator are not registered
     * @throws AccountNotInInvitedListException - Exception if the invitee is not on the event's invited list
     * @throws AccountAlreadyRepliedException - Exception if the invitee already responded to the invitation
     * @throws AccountAlreadyOnAnEventException - Exception if the user is already attending another event
     * @throws EventNotFoundInCreatorException - Exception if the event was not created by the eventCreator
     * @throws UnknownResponseException - Exception if the response is an unknown option
     */
    boolean respondInvitation(String invitee, String eventCreator, String eventName, String response) throws AccountNotFoundException, AccountNotInInvitedListException,
            AccountAlreadyRepliedException, AccountAlreadyOnAnEventException, EventNotFoundInCreatorException, UnknownResponseException;

    /**
     * Invites a user to an event
     * @param invitee - The user to be invited
     * @param eventCreator - The event creator
     * @param eventName - The name of the event
     * @throws AccountNotFoundException - Exception if either the invitee or event creator are not registered
     * @throws EventNotFoundInCreatorException - Exception if the event was not created by the eventCreator
     * @throws AccountAlreadyInvitedException - Exception if the user was already invited to the event
     * @throws AccountAlreadyOnAnEventException - Exception if the user is already attending another event
     */
    boolean invite(String invitee, String eventCreator, String eventName) throws AccountNotFoundException, EventNotFoundInCreatorException,
            AccountAlreadyInvitedException, AccountAlreadyOnAnEventException;

    /**
     * Returns an account iterator
     * @return an account iterator
     */
    Iterator<Account> accountIterator();

    /**
     * Returns an iterator of all events that a given user was invited to or created
     * @param email - The email of the user
     * @return - an iterator of events
     * @throws AccountNotFoundException - Exception if either the invitee or event creator are not registered
     */
    Iterator<Event> listEvents(String email) throws AccountNotFoundException;

    /**
     * Checks if there are date conflicts between events
     * @param invitee - The user to be invited to an event
     * @param eventCreator - The creator of the event
     * @param eventName - The name of the event
     * @return true if there are date conflicts. Otherwise, false
     * @throws AccountNotFoundException 
     */
    boolean checkConflict(String invitee, String eventCreator, String eventName) throws AccountNotFoundException;

    /**
     * Checks if the conflicted event creator is the one being invited
     * @param invitee - The user to be invited to an event
     * @param e - The event
     * @return true if the event creator is the invitee. Otherwise, false
     */
    boolean isConflictedEventCreator(String invitee, Event e);


    /**
     * Returns an iterator of rejected events by a given user after accepting another event
     * @param invitee - The user
     * @param eventCreator - 
     * @param eventName - 
     * @return an iterator of rejected events by a given user
     * @throws AccountNotFoundException 
     */
    Iterator<Event> getUserRejectedEvents(String invitee, String eventCreator, String eventName) throws AccountNotFoundException;

    /**
     * Returns the date of an event, on a specified time format
     * @param eventCreator - The creator of the event
     * @param eventName - The name of the event
     * @return the date of an event, on a specified time format
     * @throws AccountNotFoundException - Exception if either the invitee or event creator are not registered
     * @throws EventNotFoundInCreatorException - Exception if the event was not created by the eventCreator
     */
    String processSingleEvent(String eventCreator, String eventName) throws AccountNotFoundException, EventNotFoundInCreatorException;

    /**
     * Returns an invites iterator
     * @param eventName - The name of the event
     * @param eventCreator - The creator of the event
     * @return an invited iterator
     * @throws AccountNotFoundException 
     */
    Iterator<Invite> inviteIterator(String eventName, String eventCreator) throws AccountNotFoundException;

    /**
     * Returns an event iterator in which all events match to at least one event on a given set of topics.
     * The events are ordered by the ones with most matched. Ties are resolved by alphabetical order of the event name
     * and if necessary, creator name
     * @param topics - A set of topics
     * @return an event iteratr
     */
    Iterator<Event> getEventsByTopics(String[] topics);

    /**
     * Checks if the user has accepted the invitation to a given event
     * @param invitee - The user
     * @param eventCreator - The creator of the event
     * @param eventName - The name of the event
     * @return true if the user has accepted the invitation. Otherwise, false
     * @throws AccountNotFoundException 
     */
}
