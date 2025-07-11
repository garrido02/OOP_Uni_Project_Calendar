/**
 * InviteClass
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Responses;

import java.util.Objects;


/**
 * InviteClass that implements the methods of the Invite and Comparable interfaces
 */
public class InviteClass implements Invite, Comparable<Invite>{
    /**
     * Instance variables
     */
    private Responses status;
    private Account invitee;

    /**
     * Constructor
     * @param invitee - The user who was invited to the program
     */
    public InviteClass(Account invitee){
        this.status = Responses.NO_ANSWER;
        this.invitee = invitee;
    }

    @Override
    public void setResponse(Responses response) {
        this.status = response;
    }

    @Override
    public Account getInvitee() {
        return invitee;
    }

    @Override
    public Responses getResponse() {
        return status;
    }

    @Override
    public String getInviteeEmail() {
        return invitee.getEmail();
    }

    @Override
    public int compareTo(Invite o) {
        return this.invitee.compareTo(o.getInvitee());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        InviteClass that = (InviteClass) o;
        return Objects.equals(invitee.getEmail(), that.invitee.getEmail());
    }
}
