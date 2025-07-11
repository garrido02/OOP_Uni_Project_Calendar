/**
 * Event Interface
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Priorities;
import enums.Responses;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Event interface responsible to create the all EventClass method prototype
 */
public interface Event {
    /**
     * Returns the priority level of the event
     * @return the priority level
     */
    Priorities getPriorityLevel();

    /**
     * Returns the name of the event
     * @return the name of the event
     */
    String getName();

    /**
     * Returns the topics of the event
     * @return the topics of the event
     */
    String getTopics();

    /**
     * Returns the creator of the event
     * @return the creator of the event
     */
    Account getCreator();

    /**
     * Returns the date of the event
     * @return the date of the event
     */
    LocalDateTime getDate();

    /**
     * Invites a user to the event
     * @param user - The user to be invited
     */
    void invite(Account user);

    /**
     * Updates the response of a given user to the event invitation
     * @param user - The user whose response is to be updated
     * @param response - The response of the user
     */
    void setResponse(Account user, Responses response);

    /**
     * Returns an iterator of event invites
     * @return an iterator of event invites
     */
    Iterator<Invite> inviteIterator();

    /**
     * Returns the number of accepted / rejected / unanswered invitations to the event
     * @param type - A string specifying if we want "accepted", "rejected" or "unanswered" events
     * @return the number of accepted / rejected / unanswered invitations to the event
     */
    int getInvitationsNr(String type);

    /**
     * Checks if a user is invited to the event
     * @param invitee - The user
     * @return true if the user is invited. Otherwise, false
     */
    boolean isUserInvited(Account invitee);

    /**
     * Checks if a user has already replied to his event invitation
     * @param invitee - The user
     * @return true if the user has replied. Otherwise, false
     */
    boolean userAlreadyReplied(Account invitee);

    /**
     * Returns the email of the event creator
     * @return the email of the event creator
     */
    String getCreatorEmail();

    /**
     * Compares the current event with another given event by their number of common topics
     * @param e2
     * @return
     */
    int compareByTopics(Event e2);

    /**
     * Calculates the topics of the current event who match with a given set of topics
     * @param t - The set of topics to compare
     */
    void commonTopics(String[] t);

    /**
     * Returns the number of common topics to a previously given set of topics
     * @return the number of common topics
     */
    int getCommonTopics();

    /**
     * Checks if the user has rejected his event invitation
     * @param invitee - The user
     * @return true if the user has rejected the event invitation. Otherwise, false
     */
	boolean hasUserRejected(Account invitee);
}
