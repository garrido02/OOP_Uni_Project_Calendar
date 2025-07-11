/**
 * Account Interface
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Accounts;
import enums.Responses;

import java.util.Iterator;
import java.util.List;

/**
 * Account Interface responsible to create the all AbstractAccountClass method prototype
 */
public interface Account {
    /**
     * Returns the type of the account
     * @return an enum Object corresponding to the type of the account
     */
    Accounts getType();

    /**
     * Returns the email of the account
     * @return the email of the account
     */
    String getEmail();

    /**
     * Compares alphabetically this account's email to another
     * @param other - The other account
     * @return 1 if this account's email is higher alphabetically than the other. -1 if is lower. Otherwise, 0
     */
    int compareTo(Account other);

    /**
     * Checks if the account has received an invitation to a given event
     * @param e - The event
     * @return true if the account has received the invitation. Otherwise, false
     */
    boolean contains(Event e);

    /**
     * Adds an event to the user's list of invited events
     * @param e - The event
     */
    void addEvent(Event e);

    /**
     * Checks if a user already has an event at the same date of a given event
     * @param e - The event
     * @return true if the user already has an event at the given event's date. Otherwise, false
     */
    boolean isBusy(Event e);

    /**
     * Returns the event with a given name
     * @param eventName - The name of the event
     * @return an Event object corresponding to the event with the given name
     */
    Event getEvent(String eventName);

    /**
     * Returns an event iterator
     * @return an event iterator
     */
    Iterator<Event> eventIterator();

    /**
     * Adds events to a list of events to be rejected
     * @param e - The event to be added
     */
    void addRejectedEvent(Event e);

    /**
     * Returns an iterator of rejected events
     * @return an iterator of rejected events
     */
    Iterator<Event> rejectEventsIterator();

    /**
     * Invites a user to an event
     * @param inviteeAcc - The user to be invited
     * @param event - The event to which we are inviting
     */
    void invite(Account inviteeAcc, Event event);

    /**
     * Removes a given event from the user's event list
     * @param e - The event to be removed
     */
    void removeEvent(Event e);

    /**
     * Responds to a given event invitation
     * @param e - The event to respond
     * @param response - The response to the invitation
     */
    void respond(Event e, Responses response);

    /**
     * Clears the rejected events list
     */
    void clearRejectedEvents();

    /**
     * Checks if the user is already attending a high priority event
     * @return try if the user is already attending a high priority event. Otherwise, false
     */
    boolean isAttendingHighPrioEvent();
}
