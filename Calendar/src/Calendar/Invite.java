/**
 * Invite Interface
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Responses;

/**
 * Invite interface responsible to create the all InviteClass method prototype
 */
public interface Invite {

    /**
     * Updates a response to an invitation
     * @param response - The response to the invitation
     */
    void setResponse(Responses response);

    /**
     * Returns the user who was invited to the event
     * @return the user invited to the event
     */
    Account getInvitee();

    /**
     * Returns the response of the user to the invitation
     * @return the response to the invitation
     */
    Responses getResponse();

    /**
     * Returns the invited user's email
     * @return the user's email
     */
    String getInviteeEmail();
}
